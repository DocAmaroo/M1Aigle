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

package evoagentapp.tasks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JFrame;

import evoagentapp.EvoAgentAppDefines;
import evoagentmindelements.EvoAgentMind;
import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.SimulationEnvironment.ObstaclePos;
import evoagentsimulation.simulationlearning.ScoreCounter;
import evoagentsimulation.simulationlearning.SimulationInterruptFlag;
import ui.SimEnv2DViewer;

public class EATaskDemoSim extends EATask{
	protected long timePrev = 0;
	protected String envClasspath;
	protected SimulationEnvironment environment;
	protected SimulationInterruptFlag interruptFlag;
	protected ScoreCounter scoreCounter;
	protected String endMessage ="Demo ended";

	SimEnv2DViewer simPanel;
	JFrame frame;
	
	protected EvoAgentMind mind;
	
	public EATaskDemoSim(String envirName)
	{
		super();
		environmentName = envirName;
		envClasspath = EvoAgentAppDefines.classpath2DSingleExp;
	}
	
	public EvoAgentMind getMind()
	{
		return mind;
	}
	
	public void setMind(EvoAgentMind m)
	{
		mind = m;
	}
	
	public void runTask()
	{
		interruptFlag = new SimulationInterruptFlag();
		scoreCounter = new ScoreCounter();
		makeEnvironement();
		initEnvironement();	
		initViewer();
		environment.preRunOps();
		while(checkRepetitionContinue() && !endTask)
		{
			interruptFlag.resetState();
			environment.reset();
			environment.preRepetitionOps();
			while(checkContinue() && !interruptFlag.interrupted()&& !endTask)
			{
				if(throttleDelay <= 0 || System.currentTimeMillis()-timePrev>throttleDelay)
				{
					environment.preStepOps();
					environment.doWorldStep();
					environment.preMindOps();
					simStep();
					viewerStep();
					environment.postStepOps();		
					timePrev = System.currentTimeMillis();			
				}
				else
				{
					try {
						Thread.sleep(1);
					} catch (InterruptedException e1) {
						
						e1.printStackTrace();
					}					
				}				
			}
			environment.postRepetitionOps(scoreCounter,interruptFlag);
		}
		environment.postRunOps(scoreCounter,interruptFlag);
		closeViewer();
		if(endMessage!=null)
			System.out.println(endMessage);
	}

	@Override
	public void manualReset()
	{
		interruptFlag.registerInterrupt(true);
	}

	protected void simStep() {
		environment.doAutoStep(scoreCounter, interruptFlag);
	}
	
	protected void viewerStep() {
		simPanel.repaint();
	}

	protected void initViewer() {		
		simPanel = new SimEnv2DViewer(environment, scoreCounter,this); 
		frame = new JFrame(environmentName);
		frame.add(simPanel);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	protected void closeViewer() {
	    frame.setVisible(false);
	    frame.dispose();
	    frame = null;
	}

	protected boolean checkContinue() {
		return frame.isVisible();
	}
	
	protected boolean checkRepetitionContinue() {
		return frame.isVisible();
	}
	
	protected void initEnvironement()
	{
		environment.init();
		environment.setMind(mind);
		ArrayList<ArrayList<ObstaclePos>> obstaclesData = new ArrayList<>();
		if(environment.getHasObstacles())
		{
			environment.generateAllObstaclesParameters(obstaclesData, 1);
			environment.loadObstacles(obstaclesData.get(0));
		}
	}
	
	protected boolean makeEnvironement()
	{
			Class<?> clazz;
			try {
				System.out.println("making env : "+envClasspath+environmentName);
				clazz = Class.forName(envClasspath+environmentName);
				Constructor<?> constructor = clazz.getConstructor();
				environment = (SimulationEnvironment) constructor.newInstance();
				System.out.println("created environment : "+ environmentName);
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				System.out.println("environement class not found : "+ environmentName);
				System.out.println(e.getMessage());
				environment = null;
				return false;
			}
			return true;
	}
}
