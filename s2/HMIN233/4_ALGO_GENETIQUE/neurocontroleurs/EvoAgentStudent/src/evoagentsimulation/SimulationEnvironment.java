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

package evoagentsimulation;

import java.util.ArrayList;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import evoagentapp.EvoAgentAppDefines;
import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.experiments.elements.ControlFunction;
import evoagentsimulation.evoagent2dsimulator.experiments.elements.RewardFunction;
import evoagentsimulation.evoagent2dsimulator.worldElements.ObstacleStaticBox;
import evoagentsimulation.evoagent2dsimulator.worldElements.ObstacleStaticCyninder;
import evoagentsimulation.evoagent2dsimulator.worldElements.VirtualWorldElement;
import evoagentsimulation.evoagent2dsimulator.worldElements.WorldElement;
import evoagentsimulation.simulationlearning.GeneticAlgorithm;
import evoagentsimulation.simulationlearning.ScoreCounter;
import evoagentsimulation.simulationlearning.SimulationInterruptFlag;
import evoagentmindelements.EvoAgentMind;
import evoagentsimulation.evoagent2dsimulator.CollisionDefines;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.bot.BotType8;

public abstract class SimulationEnvironment {

	protected String name = "DefaultWorld";
	protected String messageString = null;
	private World World = null;
    
	protected float timeStep = (1.0f / EvoAgentAppDefines.targetFPS2DPhysics); 
	protected int viterations = 8;  
	protected int piterations = 3;  
	
	protected ContactListener worldContactlistener;
    
	protected Vec2[] corners = new Vec2[]{new Vec2(-70.0f, 70.0f), new Vec2(70.0f, 70.0f),new Vec2(70.0f, -70.0f),new Vec2(-70.0f, -70.0f)}; 
    //  haut gauche - haut droite - bas droite - bas gauche

    protected double minObstacleSize = 1.0;
    protected double maxObstacleSizeVariability = 2.0;
    protected double maxObstacleSpacingVariability = 2.0;
    protected double ObstacleProbability = 1.0;
    protected double obstacleSpacing = 18.0;
	
	protected ArrayList<VirtualWorldElement> worldElements = new ArrayList<>();
	protected ArrayList<WorldElement> obstacles = new ArrayList<>();
	protected ArrayList<ControlFunction> controlFunctions = new ArrayList<>();
	protected ArrayList<RewardFunction> rewardFunctions = new ArrayList<>();
		
	protected boolean triggeredInterrupt = false;
	
	protected boolean makeCynlinderObstacles = false;
	protected boolean makeCircleWorld = false;	
	protected int botContactCounter = 0;
	protected long tick = 0;
	
	protected boolean hasObstacles = false;
	protected BotBody2D bot = null;
	protected Vec2 botStartPos = new Vec2(10.0f,0.0f);
	protected double botStartAngle = 0.0;
	
	public static GeneticAlgorithm geneticAlgorithm;
	protected EvoAgentMind mind = null;
	
    public SimulationEnvironment()
    {
    	super();
		worldContactlistener = new ContactListener() {	
			@Override
			public void beginContact(Contact contact) {
				if((contact.m_fixtureA.m_body == getBot().body ||contact.m_fixtureB.m_body == getBot().body )&&(contact.m_fixtureA.m_filter.categoryBits == CollisionDefines.CDBot || contact.m_fixtureB.m_filter.categoryBits == CollisionDefines.CDBot))
				{
					if(!(contact.m_fixtureA.m_filter.categoryBits == CollisionDefines.CDBot&&contact.m_fixtureB.m_filter.categoryBits == CollisionDefines.CDTargetObj) 
							&& !(contact.m_fixtureB.m_filter.categoryBits == CollisionDefines.CDBot&&contact.m_fixtureA.m_filter.categoryBits == CollisionDefines.CDTargetObj) )
					{
						getBot().contactCounter++;	
						botContactCounter++;		
					}
				}
			}
			@Override
			public void endContact(Contact contact) {
				if((contact.m_fixtureA.m_body == getBot().body ||contact.m_fixtureB.m_body == getBot().body )&&(contact.m_fixtureA.m_filter.categoryBits == CollisionDefines.CDBot || contact.m_fixtureB.m_filter.categoryBits == CollisionDefines.CDBot))
				{
					if(!(contact.m_fixtureA.m_filter.categoryBits == CollisionDefines.CDBot&&contact.m_fixtureB.m_filter.categoryBits == CollisionDefines.CDTargetObj) 
							&& !(contact.m_fixtureB.m_filter.categoryBits == CollisionDefines.CDBot&&contact.m_fixtureA.m_filter.categoryBits == CollisionDefines.CDTargetObj) )
						{
						getBot().contactCounter--;
						botContactCounter--;
						}
				}
			}
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {}
		};
    }
    
    public void doAutoStep(ScoreCounter scoreCounter, SimulationInterruptFlag simulationInterruptFlag) {
    	simulationInterruptFlag.registerInterrupt(triggeredInterrupt);
		if(getBot() !=null)
		{
			if(scoreCounter != null)
				for(RewardFunction r : rewardFunctions)
					scoreCounter.addToScore(r.computeRewardValue());		
			for(ControlFunction c : controlFunctions)
				simulationInterruptFlag.registerInterrupt(c.performCheck());
			getBot().step();
			mind.doStep();
		}
	}
    
    public void reset() {
		triggeredInterrupt = false;
		if(getBot()!=null)
		{
			getBot().reset();
			getBot().setPosition(botStartPos,0.0f);			
		}
		getWorld().clearForces();
		for(ControlFunction c : controlFunctions)
			c.reset();
		for(RewardFunction r : rewardFunctions)
			r.reset();
		triggeredInterrupt = false;
		botContactCounter = 0;
	}
    
  	public World makeWorld()
  	{
  		Vec2 gravity = new Vec2(0.0f, 0.0f);
  		setWorld(new World(gravity));
  		makeBorders(getWorld());
  		World.setContactListener(worldContactlistener);
  		return getWorld();
  	}
  	
	protected void makeBorders(World w)
	{
		final float k_restitution = 0.1f;
		Body ground;
		{
			BodyDef bd = new BodyDef();
			bd.position.set(0.0f, 0.0f);
			ground = w.createBody(bd);
			ChainShape shape = new ChainShape();
			FixtureDef sd = new FixtureDef();
			sd.shape = shape;
			sd.density = 0.0f;
			sd.restitution = k_restitution;
			shape.createLoop(getCorners(), getCorners().length);
			ground.createFixture(sd);
		}
		if(makeCircleWorld && !hasObstacles)
		{
			for(int i = (int) getCorners()[0].x;i < getCorners()[1].x;i+=minObstacleSize*4)
			{
				getObstacles().add(new ObstacleStaticCyninder(new Vec2(i,getCorners()[0].y), 0,(float) minObstacleSize*2.5f, getWorld()));	
				getObstacles().add(new ObstacleStaticCyninder(new Vec2(i,getCorners()[2].y), 0,(float) minObstacleSize*2.5f, getWorld()));	
			}

			for(int i = (int) getCorners()[3].y;i < getCorners()[0].y;i+=minObstacleSize*4)
			{
				getObstacles().add(new ObstacleStaticCyninder(new Vec2(getCorners()[0].x,i), 0,(float) minObstacleSize*2.5f, getWorld()));	
				getObstacles().add(new ObstacleStaticCyninder(new Vec2(getCorners()[1].x,i), 0,(float) minObstacleSize*2.5f, getWorld()));	
			}
		}
	}
	
	public synchronized void generateAllObstaclesParameters(ArrayList<ArrayList<ObstaclePos>> obstaclesData, int repeatCount) {
		if(obstaclesData.size() < 1)
			for(int i = 0; i < repeatCount; i++)
				obstaclesData.add(generateObstaclesParameters());
	}
	
	public ArrayList<ObstaclePos> generateObstaclesParameters() {
		ArrayList<ObstaclePos> ret = new ArrayList<>();
		
		double width = getCorners()[1].x - getCorners()[0].x;
		double height = getCorners()[0].y - getCorners()[2].y;
		int widthStep = (int)(width / obstacleSpacing) + 1;
		int heightStep = (int)(height / obstacleSpacing) + 1;
		float s;

		Vec2 wp ;
		for (int i = 0 ; i < widthStep; i++)
		{
			for (int j = 0 ; j < heightStep; j++)
			{
				if(Math.random()< ObstacleProbability)
				{
					wp =  new Vec2();
					wp.x  = (float)(getCorners()[3].x + (i * obstacleSpacing)+(Math.random()*maxObstacleSpacingVariability-(maxObstacleSpacingVariability/2.0)));
					wp.y  = (float)(getCorners()[3].y + (j * obstacleSpacing)+(Math.random()*maxObstacleSpacingVariability-(maxObstacleSpacingVariability/2.0)));
					s = (float)((Math.random()*maxObstacleSizeVariability)+minObstacleSize);
					if(checkElementPositionConficts(wp,s))
						ret.add(new ObstaclePos(wp, (float)(Math.PI*Math.random()), s));						
				}
			}			
		}
		return ret;
	}

	protected boolean checkElementPositionConficts(Vec2 wp, float s) {
		for(VirtualWorldElement el: getWorldElements())
			if(MathUtils.distance(el.worldPosition,wp)<s+el.size)
				return false;			
		return true;
	}

	protected boolean checkObstaclePositionConficts(Vec2 wp, float s) {
		for(WorldElement ob: getObstacles())
			if(MathUtils.distance(ob.worldPosition,wp)<s+ob.size)
				return false;			
		return true;
	}
	
	protected boolean checkInBoundaries(Vec2 wp, float s) {
		if((wp.x - s > getCorners()[0].x) &&
				(wp.x + s < getCorners()[1].x) &&
				(wp.y - s > getCorners()[3].y) &&
				(wp.y + s < getCorners()[0].y) )
		{
				return true;			
		}
		return false;
	}
	
	protected Vec2 generatePositionInBoundaries(float margin) {
		return new Vec2(
				(float)(Math.random() * (getCorners()[1].x - getCorners()[0].x - (2*margin)))+getCorners()[3].x+margin,
				(float)(Math.random() * (getCorners()[0].y - getCorners()[2].y - (2*margin)))+getCorners()[3].y+margin
				);
	}
	
	protected Vec2 generatePositionInBoundaries() {
		return new Vec2(
				(float)(Math.random() * (getCorners()[1].x - getCorners()[0].x - (20f)))+getCorners()[3].x+10f,
				(float)(Math.random() * (getCorners()[0].y - getCorners()[2].y - (20f)))+getCorners()[3].y+10f
				);
	}
	
	public void loadObstacles(ArrayList<ObstaclePos> obs) {
		//System.out.println(World);
		for(WorldElement o : getObstacles())
			o.removeFromWorld();
		getObstacles().clear();
			if(makeCynlinderObstacles || makeCircleWorld)
				for(ObstaclePos o : obs)
					getObstacles().add(new ObstacleStaticCyninder(o.wp, o.orientation,o.size, getWorld()));		
			else
				for(ObstaclePos o : obs)
					getObstacles().add(new ObstacleStaticBox(o.wp, o.orientation,o.size, getWorld()));	
			if(makeCynlinderObstacles || makeCircleWorld)
			{
				for(int i = (int) getCorners()[0].x;i < getCorners()[1].x;i+=minObstacleSize*4)
				{
					getObstacles().add(new ObstacleStaticCyninder(new Vec2(i,getCorners()[0].y), 0,(float) minObstacleSize*2.5f, getWorld()));	
					getObstacles().add(new ObstacleStaticCyninder(new Vec2(i,getCorners()[2].y), 0,(float) minObstacleSize*2.5f, getWorld()));	
				}

				for(int i = (int) getCorners()[3].y;i < getCorners()[0].y;i+=minObstacleSize*4)
				{
					getObstacles().add(new ObstacleStaticCyninder(new Vec2(getCorners()[0].x,i), 0,(float) minObstacleSize*2.5f, getWorld()));	
					getObstacles().add(new ObstacleStaticCyninder(new Vec2(getCorners()[1].x,i), 0,(float) minObstacleSize*2.5f, getWorld()));	
				}
			}
	}
	
	public class ObstaclePos{
		public Vec2 wp;
		public float orientation, size;
		
		public ObstaclePos(Vec2 inp,float orient, float s)
		{
			wp = inp;
			orientation = orient;
			size = s;
		}
	}
	
	public BotBody2D getBot() {
		return bot;
	}

	public void doWorldStep()
	{
		getWorld().step(timeStep,viterations,piterations);
		tick++;
	}

    public boolean hasTriggeredInterrupt()
    {
    	return triggeredInterrupt;
    }
    
    public void manualReset()
	{
    	triggeredInterrupt = true;
	}

    public abstract GeneticAlgorithm makeGeneticAlgorihm(int size);

    public abstract EvoAgentMind makeMind();
    
	public void preinit() {
		bot = new BotType8(this);
	}
	public void initialisation() {
		
	}
	public void postinit() {

	}

	public void init() {
		preinit();
		initialisation();
		postinit();
	}
	
	public void preStepOps() {
		//Override me
		// called before a simulation step
	}
	
	public void preMindOps() {
		//Override me
		// called after the world step (physics), before the Mind step (agent logic)
	}
	
	public void postStepOps() {
		//Override me
		// called after a simulation step
	}
    
	public void preRepetitionOps() {
		//Override me
		// called before a repetition
	}
	
	public void postRepetitionOps(ScoreCounter scoreCounter, SimulationInterruptFlag simulationInterruptFlag) {
		//Override me		
		// called after a repetition
	}
	
	public void preRunOps() {
		//Override me
		// called after the init, before starting the first repetition
	}
	
	public void postRunOps(ScoreCounter score, SimulationInterruptFlag interruptFlag) {
		//Override me		
		// called after the last repetition
	}

	public boolean getHasObstacles()
	{
		return hasObstacles;
	}  
	
	public boolean isCircleWorld()
	{
    	return makeCircleWorld;
	}

	public ArrayList<VirtualWorldElement> getWorldElements() {
		return worldElements;
	}

	public void setWorldElements(ArrayList<VirtualWorldElement> worldElements) {
		this.worldElements = worldElements;
	}

	public World getWorld() {
		return World;
	}

	public void setWorld(World world) {
		World = world;
	}
	
	public EvoAgentMind getMind() {
		return mind;
	}

	public void setMind(EvoAgentMind m) {
		mind = m;
		bot.setMind(m);
	}

	public Vec2[] getCorners() {
		return corners;
	}

	public ArrayList<WorldElement> getObstacles() {
		return obstacles;
	}
	
	public String getMessageString()
	{
		return messageString;
	}

	public int getBotContactCount()
	{
		return botContactCounter;		
	}

	public long getTick() {
		return tick;
	}
	
	public void setSquareBorders (float halfWidth)
	{
		corners = new Vec2[]{
				new Vec2(-halfWidth , halfWidth),
				new Vec2(halfWidth, halfWidth),
				new Vec2(halfWidth, -halfWidth),
				new Vec2(-halfWidth, -halfWidth)}; 
	}

	public void setrectangularBorders (float halfWidth, float halfHeight)
	{
		corners = new Vec2[]{
				new Vec2(-halfWidth , halfHeight),
				new Vec2(halfWidth, halfHeight),
				new Vec2(halfWidth, -halfHeight),
				new Vec2(-halfWidth, -halfHeight)}; 
	}
}