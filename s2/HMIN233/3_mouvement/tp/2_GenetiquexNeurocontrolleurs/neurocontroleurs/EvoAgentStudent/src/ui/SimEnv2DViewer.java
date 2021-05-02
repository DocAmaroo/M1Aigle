/*******************************************************************************
 *  * EvoAgentStudent : A simulation platform for agents using neurocontrollers
 *  * Copyright (c)  2020 Suro François (suro@lirmm.fr)
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jbox2d.common.Vec2;

import evoagentapp.EvoAgentApp;
import evoagentapp.tasks.EATask;
import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_ObjectDetector;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PLObjectListActive;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PointListener;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PointListenerDistance;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PointListenerOrient;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_ProximitySensor;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PLZoneActive;
import evoagentsimulation.evoagent2dsimulator.bot.elements.Sensor;
import evoagentsimulation.evoagent2dsimulator.worldElements.MovingObstacle;
import evoagentsimulation.evoagent2dsimulator.worldElements.ObstacleMovingBox;
import evoagentsimulation.evoagent2dsimulator.worldElements.ObstacleStaticBox;
import evoagentsimulation.evoagent2dsimulator.worldElements.ObstacleStaticCyninder;
import evoagentsimulation.evoagent2dsimulator.worldElements.TargetObject;
import evoagentsimulation.evoagent2dsimulator.worldElements.TriggerZone;
import evoagentsimulation.evoagent2dsimulator.worldElements.VirtualWorldElement;
import evoagentsimulation.evoagent2dsimulator.worldElements.WorldElement;
import evoagentsimulation.simulationlearning.ScoreCounter;

public class SimEnv2DViewer extends JPanel{
	private static final long serialVersionUID = -5562812187406952123L;
	
	SimulationEnvironment env;
	EATask task;
	int offsetX = 40,offsetY = 40;
	int mPrevX = 0,mPrevY = 0;
	float scale = 1.0f;
	boolean drawGizmos = false;
	boolean drawPath = false;
	int pathSampling = 10;
	boolean drawNames = false;
	boolean debugStrings = false;
	boolean rightClick = false;
	boolean leftClick = false;
	public static double displayedReward = 0.0;
	Font font = new Font("Default", Font.BOLD, 20);
	DecimalFormat decFormat = new DecimalFormat("#.######");
	boolean singleBot = true;
	ArrayList<Color> teamColors = new ArrayList<>();
	ScoreCounter rewardScore = null;
	BasicStroke stroke = new BasicStroke(1);
	BasicStroke defaultstroke = new BasicStroke(1);
	int strokeThickness = 1;
	boolean fill = false;
	boolean follow = false;
	Color backgroundColor = Color.black;
	Color defaultBotColor = Color.red;
	Color obstacleColor = Color.green;
	Color infoFontColor = Color.green;
	Color virtualColor = Color.orange;
	Color projectileColor = Color.yellow;
	Color sensorBaseColor = Color.lightGray;
	Color sensorSecondaryColor = Color.darkGray;
	Color sensorAltColor = Color.cyan;
	
	JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
	
	ArrayList<Vec2> path;
	
	
	public SimEnv2DViewer(SimulationEnvironment w, EATask tsk)
	{
		this(w,null, tsk);
	}

	public SimEnv2DViewer(SimulationEnvironment w, ScoreCounter sc, EATask tsk)
	{
		setPreferredSize(new Dimension(800,550));
		task = tsk;
		rewardScore = sc;
		env = w;      
		if(env!=null)
		{
			int width = (int)(w.getCorners()[1].x - w.getCorners()[0].x);
			scale = 800/width;
			offsetX = -(int)(w.getCorners()[0].x*scale);
			offsetY = (int)(w.getCorners()[0].y*scale);			
		}
		
		JButton gizmoButton = new JButton("Gizmos");
		gizmoButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				drawGizmos = !drawGizmos;
				repaint();
			}
		});
		toolbar.add(gizmoButton);

		if(singleBot) {
		JButton pathButton = new JButton("Path");
		pathButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				drawPath = !drawPath;
				clearPath();
				repaint();
			}
		});
		toolbar.add(pathButton);}

		if(!singleBot) {
		JButton namesButton = new JButton("Names");
		namesButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				drawNames = !drawNames;
				repaint();
			}
		});
		toolbar.add(namesButton);}
		JSlider speed = new JSlider(0,100,40);
		speed.addChangeListener(new ChangeListener() {	
			@Override
			public void stateChanged(ChangeEvent e) {
				if(EvoAgentApp.getTask()!=null)
				{
					float val = (float)speed.getValue();
					if(val <= 40)
						task.setTrottleRender(val/10.0f);
					else if(val == speed.getMaximum())
						task.setTrottleRender(1000000f);						
					else
						task.setTrottleRender((float)(40+Math.pow(val-40,1.7))/10.0f);
				}
			}
		});
		toolbar.add(speed);
		JButton thickPlus = new JButton("t+");
		JButton thickMinus = new JButton("t-");
		thickPlus.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {//ugh
				strokeThickness++;
				stroke = new BasicStroke(strokeThickness);
			}
		});
		thickMinus.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(strokeThickness > 1)
				{
					strokeThickness--;
					stroke = new BasicStroke(strokeThickness);
				}
			}
		});
		toolbar.add(thickPlus);
		toolbar.add(thickMinus);
		
		JButton bgplus = new JButton("b+");
		JButton bgminus = new JButton("b-");
		bgplus.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				backgroundColor =backgroundColor.brighter();
			}
		});
		bgminus.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(strokeThickness > 0)
				{
					backgroundColor =backgroundColor.darker();
				}
			}
		});
		toolbar.add(bgplus);
		toolbar.add(bgminus);
		
		JButton fillButton = new JButton("Fill");
		fillButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				fill = !fill;
			}
		});
		toolbar.add(fillButton);
		
		if(singleBot) {
		JButton followButton = new JButton("Follow");
		followButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				follow = !follow;
			}
		});
		toolbar.add(followButton);}
		
		JButton resetButton = new JButton("Reset Sim");
		resetButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				env.manualReset();
			}
		});
		
		toolbar.add(resetButton);
		
		toolbar.setFloatable(false);
		this.add(toolbar);
		
        
        this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == 1)
				{
					rightClick = false;
				}
				else if(e.getButton() == 3)
				{
					leftClick = false;
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				mPrevX = e.getX();
				mPrevY = e.getY();		
				if(e.getButton() == 1)
				{
					rightClick = true;
				}
				else if(e.getButton() == 3)
				{
					leftClick = true;
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
        this.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() > 0)
					scale += 0.1;
				else if (scale > 0.1)
					scale -= 0.1;

				repaint();
			}
		});
        this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				offsetX +=e.getX()-mPrevX;
				offsetY +=e.getY()-mPrevY;
				mPrevX = e.getX();
				mPrevY = e.getY();
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
			}
		});
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    	((Graphics2D)g).setStroke(stroke);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, this.getWidth(),this.getHeight());
        g.setFont(font);
        if(env!=null)
        {   
        	if (drawPath)
    			drawPath(g);
            if(follow)
            	centerCamera(env.getBot());
            drawBot(g,env.getBot(),defaultBotColor);   
            drawBorders(g);
            drawObstacles(g);
            drawWorldElements(g);
			g.setColor(infoFontColor);
			if(rewardScore != null)
				g.drawString("Reward : " + decFormat.format(rewardScore.getCurrentScore()),toPix(env.getCorners()[2].x)+offsetX,toPix(env.getCorners()[2].y)+offsetY+100);	
			g.drawString("tick : " + env.getTick(),toPix(env.getCorners()[2].x)+offsetX,toPix(env.getCorners()[2].y)+offsetY+150);	
			if(env.getMessageString() != null)
				g.drawString(env.getMessageString(),toPix(env.getCorners()[2].x)+offsetX,toPix(env.getCorners()[2].y)+offsetY+200);	
			
        }

    	((Graphics2D)g).setStroke(defaultstroke);
    }
    
    private void centerCamera(BotBody2D bot)
    {
    	offsetX =  -toPix(bot.body.getPosition().x) + (this.getWidth() / 2);
		offsetY = -toPix(bot.body.getPosition().y) + (this.getHeight() / 2);
		mPrevX = offsetX;
		mPrevY = offsetY;
    }

	private void drawBot(Graphics g, BotBody2D bot, Color bodyColor) {
		if (drawPath && singleBot)
			recordPath(bot);
		if (drawGizmos)
		{
			for(Sensor s: bot.sensors.values())
			{
				if(s.isInUse())
				{
					if(s instanceof S_ProximitySensor)
						drawPSensorGizmo(g,(S_ProximitySensor)s);
					else if(s instanceof S_ObjectDetector)
						drawObjDecGizmo(g,(S_ObjectDetector)s);
					else if(s instanceof S_PointListenerDistance || s instanceof S_PointListenerOrient)
					{
						Sensor sub;
						if(s instanceof S_PointListenerDistance)
							sub = ((S_PointListenerDistance) s).getListener();
						else
							sub = ((S_PointListenerOrient) s).getListener();
						if(sub instanceof S_PLZoneActive)
							drawPointListenerGizmo(g,(S_PLZoneActive)sub, sensorSecondaryColor);
						else if(sub instanceof S_PLObjectListActive)
							drawPointListenerGizmo(g,(S_PLObjectListActive)sub, sensorSecondaryColor);
					}
				}
			}
		}
    	g.setColor(bodyColor);
    	int bsize = toPix((float)bot.size);
    	if (fill)
			g.fillOval(toPix(bot.body.getPosition().x-(float)(bot.size))+offsetX, toPix(bot.body.getPosition().y-(float)(bot.size))+offsetY, bsize*2, bsize*2);
		else
			g.drawOval(toPix(bot.body.getPosition().x-(float)(bot.size))+offsetX, toPix(bot.body.getPosition().y-(float)(bot.size))+offsetY, bsize*2, bsize*2);

    	
		int tx =(int)(Math.cos(bot.body.getAngle())*(bsize));
		int ty =(int)(Math.sin(bot.body.getAngle())*(bsize));
    	if (fill)
        	g.setColor(Color.black);

		g.drawLine(toPix(bot.body.getPosition().x)+offsetX, toPix(bot.body.getPosition().y)+offsetY,toPix(bot.body.getPosition().x)+offsetX+tx, toPix(bot.body.getPosition().y)+offsetY+ty);
    	g.setColor(bodyColor);
		if(drawNames)
		{
	    	g.setColor(bodyColor);
	    	g.drawString(bot.getName(),toPix(bot.body.getPosition().x-(float)(bot.size))+offsetX, toPix(bot.body.getPosition().y-(float)(bot.size))+offsetY);	

		}
    
    }
	
	private void drawObjDecGizmo(Graphics g, S_ObjectDetector s) {
		g.setColor(sensorAltColor);
		//g.drawLine(toPix(s.worldPosition.x)+offsetX,toPix(s.worldPosition.y)+offsetY,toPix(s.hitPoint.x)+offsetX,toPix(s.hitPoint.y)+offsetY);
	}

	private void drawPointListenerGizmo(Graphics g, S_PointListener s, Color col) {
		Vec2 target;
		if((target = s.getCurrentTargetPosition())!=null && ((Sensor)s).worldPosition != null)
		{
			g.setColor(col);
			g.drawLine(toPix(s.worldPosition.x)+offsetX,toPix(s.worldPosition.y)+offsetY,toPix(target.x)+offsetX,toPix(target.y)+offsetY);			
		}
	}
	
	private void drawPSensorGizmo(Graphics g,S_ProximitySensor s) {
		if(env.isCircleWorld())
		{
			g.setColor(sensorSecondaryColor);
			g.drawLine(toPix(s.boundingBoxPointx(1))+offsetX,toPix(s.boundingBoxPointy(1))+offsetY,toPix(s.boundingBoxPointx(2))+offsetX,toPix(s.boundingBoxPointy(2))+offsetY);
			g.drawLine(toPix(s.boundingBoxPointx(2))+offsetX,toPix(s.boundingBoxPointy(2))+offsetY,toPix(s.boundingBoxPointx(3))+offsetX,toPix(s.boundingBoxPointy(3))+offsetY);
			g.drawLine(toPix(s.boundingBoxPointx(3))+offsetX,toPix(s.boundingBoxPointy(3))+offsetY,toPix(s.boundingBoxPointx(4))+offsetX,toPix(s.boundingBoxPointy(4))+offsetY);
			g.drawLine(toPix(s.boundingBoxPointx(4))+offsetX,toPix(s.boundingBoxPointy(4))+offsetY,toPix(s.boundingBoxPointx(1))+offsetX,toPix(s.boundingBoxPointy(1))+offsetY);
	
			g.setColor(sensorBaseColor);	
			Vec2 hit =  new Vec2(s.worldPosition.x+((float)(Math.cos(s.worldAngle)*s.normalizedValue*s.getMaxDistance())),
					s.worldPosition.y+((float)(Math.sin(s.worldAngle)*s.normalizedValue*s.getMaxDistance())));
			g.drawLine(toPix(s.worldPosition.x)+offsetX,toPix(s.worldPosition.y)+offsetY,toPix(hit.x)+offsetX,toPix(hit.y)+offsetY);
		}
		else
		{
			g.setColor(sensorBaseColor);
			g.drawLine(toPix(s.getWorldPosition().x)+offsetX,toPix(s.getWorldPosition().y)+offsetY,toPix(s.getHitPoint().x)+offsetX,toPix(s.getHitPoint().y)+offsetY);
			if(s.getArc() > 0)
			{
				g.setColor(sensorSecondaryColor);
				g.drawLine(toPix(s.worldPosition.x)+offsetX,toPix(s.worldPosition.y)+offsetY,toPix(s.getRightBound().x)+offsetX,toPix(s.getRightBound().y)+offsetY);
				g.drawLine(toPix(s.worldPosition.x)+offsetX,toPix(s.worldPosition.y)+offsetY,toPix(s.getLeftBound().x)+offsetX,toPix(s.getLeftBound().y)+offsetY);
			}			
		}
	}

	private void drawObstacles(Graphics g) {
		for(VirtualWorldElement we: env.getObstacles())
		{
			if(we.getClass().toString().equals(ObstacleStaticBox.class.toString())||we.getClass().toString().equals(ObstacleMovingBox.class.toString()))
				drawStaticBox(g,(ObstacleStaticBox)we,obstacleColor);
			else if(we.getClass().toString().equals(ObstacleStaticCyninder.class.toString()))
				drawCircle(g,(WorldElement) we,obstacleColor);
		}
	}
	
	
	
	private void drawWorldElements(Graphics g) {
		try {
		for(VirtualWorldElement we: env.getWorldElements())
			{
				if(we instanceof TriggerZone)
				{
					drawStaticBox(g,(TriggerZone)we,virtualColor);
					g.drawString(((TriggerZone)we).name, toPix(((TriggerZone)we).worldPosition.x-((TriggerZone)we).size)+offsetX, toPix(((TriggerZone)we).worldPosition.y-((TriggerZone)we).size)+offsetY);
				}
				else if(we instanceof TargetObject)
					drawCircle(g,(WorldElement)we,((WorldElement)we).getColor());
				else if(we instanceof MovingObstacle)
					drawCircle(g,(WorldElement)we,obstacleColor);
			}
		}
		catch (Exception e) {
			//i don't care about concurrent modification
		}
	}

	private void drawCircle(Graphics g, WorldElement we, Color orange) {
		g.setColor(orange);
		int bsize = toPix((float)we.size);
		if (fill)
			g.fillOval(toPix(we.body.getPosition().x-(float)(we.size))+offsetX-strokeThickness, toPix(we.body.getPosition().y-(float)(we.size))+offsetY-strokeThickness, (bsize+strokeThickness)*2,  (bsize+strokeThickness)*2);
		else
			g.drawOval(toPix(we.body.getPosition().x-(float)(we.size))+offsetX, toPix(we.body.getPosition().y-(float)(we.size))+offsetY, bsize*2, bsize*2);		
		//g.fillOval(toPix(we.body.getPosition().x-(float)(we.size))+offsetX, toPix(we.body.getPosition().y-(float)(we.size))+offsetY, bsize*2, bsize*2);	
	}

	private void drawStaticBox(Graphics g, VirtualWorldElement we, Color c) {
		
		g.setColor(c);
		Vec2 points[] = new Vec2[4];
		points[0]= new Vec2(-we.size,we.size);
		points[1]= new Vec2(we.size,we.size);
		points[2]= new Vec2(we.size,-we.size);
		points[3]= new Vec2(-we.size,-we.size);
		for(int i = 0 ; i < 4 ; i++)
			points[i].set(we.getWorldPoint(points[i]));

		 g.drawLine(toPix(points[0].x)+offsetX, toPix(points[0].y)+offsetY,toPix(points[1].x)+offsetX, toPix(points[1].y)+offsetY);
		 g.drawLine(toPix(points[1].x)+offsetX, toPix(points[1].y)+offsetY,toPix(points[2].x)+offsetX, toPix(points[2].y)+offsetY);
		 g.drawLine(toPix(points[2].x)+offsetX, toPix(points[2].y)+offsetY,toPix(points[3].x)+offsetX, toPix(points[3].y)+offsetY);
		 g.drawLine(toPix(points[3].x)+offsetX, toPix(points[3].y)+offsetY,toPix(points[0].x)+offsetX, toPix(points[0].y)+offsetY);
		 if(fill)
		 {
			 g.drawLine(toPix(points[0].x)+offsetX, toPix(points[0].y)+offsetY,toPix(points[2].x)+offsetX, toPix(points[2].y)+offsetY);
			 g.drawLine(toPix(points[3].x)+offsetX, toPix(points[3].y)+offsetY,toPix(points[1].x)+offsetX, toPix(points[1].y)+offsetY);
		 }
	}

	private void drawBorders(Graphics g) {
		 g.setColor(obstacleColor);
		 g.drawLine(toPix(env.getCorners()[0].x)+offsetX, toPix(env.getCorners()[0].y)+offsetY,toPix(env.getCorners()[1].x)+offsetX, toPix(env.getCorners()[1].y)+offsetY);
		 g.drawLine(toPix(env.getCorners()[1].x)+offsetX, toPix(env.getCorners()[1].y)+offsetY,toPix(env.getCorners()[2].x)+offsetX, toPix(env.getCorners()[2].y)+offsetY);
		 g.drawLine(toPix(env.getCorners()[2].x)+offsetX, toPix(env.getCorners()[2].y)+offsetY,toPix(env.getCorners()[3].x)+offsetX, toPix(env.getCorners()[3].y)+offsetY);
		 g.drawLine(toPix(env.getCorners()[3].x)+offsetX, toPix(env.getCorners()[3].y)+offsetY,toPix(env.getCorners()[0].x)+offsetX, toPix(env.getCorners()[0].y)+offsetY);
	}

	int toPix(float val)
    {
    	return (int)(val*scale);
    }
	
	float fromPix(int val)
    {
    	return ((float)val/scale);
    }
	
	private void clearPath() {
		path = new ArrayList<>();
	}

	private void drawPath(Graphics g) {
		g.setColor(sensorAltColor);
		for(int i = 1 ; i < path.size() ; i++)
			g.drawLine(toPix(path.get(i-1).x)+offsetX, toPix(path.get(i-1).y)+offsetY,toPix(path.get(i).x)+offsetX, toPix(path.get(i).y)+offsetY);

	}

	private void recordPath(BotBody2D bot) {
		if(env.getTick() % pathSampling== 0)
			path.add(new Vec2(bot.getPosition()));	
	}	
}
