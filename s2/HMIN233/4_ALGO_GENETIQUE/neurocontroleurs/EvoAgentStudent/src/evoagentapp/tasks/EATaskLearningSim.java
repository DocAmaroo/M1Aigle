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
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import evoagentapp.EvoAgentAppDefines;
import evoagentmindelements.EvoAgentMind;
import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.SimulationEnvironment.ObstaclePos;
import evoagentsimulation.simulationlearning.GeneticAlgorithm;
import evoagentsimulation.simulationlearning.GeneticAlgorithm.Individual;
import evoagentsimulation.simulationlearning.ScoreCounter;
import evoagentsimulation.simulationlearning.SimulationInterruptFlag;
import ui.EvoAgentAppFrame;

public class EATaskLearningSim extends EATask{
	protected String envClasspath;
	protected String endMessage ="Demo ended";
	protected ArrayList<ArrayList<ObstaclePos>> od = new ArrayList<>();
	protected GeneticAlgorithm geneticAlgorithm;
	protected int repetitionLimit = 1;
	protected int tickLimit = 500000;
	protected SimulationEnvironment simEnvTemplate;
	protected Individual best = null;
	
	public EATaskLearningSim(String envirName)
	{
		super();
		envClasspath = EvoAgentAppDefines.classpath2DSingleExp;
		environmentName = envirName;
	}
	
	public void runTask() 
	{
		
		Individual temp = null;
		try {Class<?> clazz = Class.forName(envClasspath+environmentName);
			clazz = Class.forName(envClasspath+environmentName);
			Constructor<?> constructor = clazz.getConstructor();
			simEnvTemplate = (SimulationEnvironment) constructor.newInstance();
			geneticAlgorithm = simEnvTemplate.makeGeneticAlgorihm(simEnvTemplate.makeMind().genomeSize());
			geneticAlgorithm.initialise();
			repetitionLimit = geneticAlgorithm.getRepetitions();
			ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
			for (int i = 0; i < geneticAlgorithm.getMaxGeneration()&& !endTask; i++) {
				int j = 0;
				while(j < geneticAlgorithm.getPopulation().size())
				{
					if(executor.getActiveCount() < EvoAgentAppFrame.PROCS.getValue())
					{
						executor.execute(new threadedEnv(geneticAlgorithm.getPopulation().get(j)));
						j++;
					}
					else
					{try {Thread.sleep(1);} 
						catch (InterruptedException e) {e.printStackTrace();}}
				}
				while(executor.getActiveCount()>0){
					try {Thread.sleep(5);} 
					catch (InterruptedException e) {e.printStackTrace();}}
				temp = geneticAlgorithm.getBest();
				if(best == null || best.getScore() < temp.getScore())
					best = temp;
				System.out.println("iteration : "+ i +" best score : " + best.getScore());
				geneticAlgorithm.breedNew();
			}

			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {e1.printStackTrace();}
	}
	
	
	public class threadedEnv implements Runnable{
		protected SimulationEnvironment environment;
		protected SimulationInterruptFlag interruptFlag= new SimulationInterruptFlag();;
		protected ScoreCounter scoreCounter= new ScoreCounter();
		protected int repetitionCount = 0;
		protected int tickCount = 0;
		protected EvoAgentMind mind;
		protected Individual individual;
		
		public threadedEnv(Individual in)
		{
			individual = in;
		}
		
		@Override
		public void run() {
			makeEnvironement();
			initEnvironement();	
			environment.preRunOps();
			while(repetitionCount<repetitionLimit && !endTask)
			{
				tickCount = 0;
				interruptFlag.resetState();
				environment.reset();
				environment.preRepetitionOps();
				while(tickCount<tickLimit &&!interruptFlag.interrupted()&& !endTask)
				{
					environment.preStepOps();
					environment.doWorldStep();
					environment.preMindOps();
					environment.doAutoStep(scoreCounter, interruptFlag);
					environment.postStepOps();	
					tickCount++;
				}
				environment.postRepetitionOps(scoreCounter,interruptFlag);
				repetitionCount++;
			}
			environment.postRunOps(scoreCounter,interruptFlag);
			individual.setScore(scoreCounter.getCurrentScore());
		}
		
		protected void initEnvironement()
		{
			environment.init();
			environment.setMind(environment.makeMind());
			environment.getMind().setWeights(individual.getGenome());
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
					clazz = Class.forName(envClasspath+environmentName);
					Constructor<?> constructor = clazz.getConstructor();
					environment = (SimulationEnvironment) constructor.newInstance();
					} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					System.out.println("environement class not found : "+ environmentName);
					System.out.println(e.getMessage());
					environment = null;
					return false;
				}
				return true;
		}
	}
	public EvoAgentMind getMind()
	{
		EvoAgentMind m = null;
		if(best != null)
		{
			m = simEnvTemplate.makeMind();
			m.setWeights(best.getGenome());			
		}
		return m;
	}
}
