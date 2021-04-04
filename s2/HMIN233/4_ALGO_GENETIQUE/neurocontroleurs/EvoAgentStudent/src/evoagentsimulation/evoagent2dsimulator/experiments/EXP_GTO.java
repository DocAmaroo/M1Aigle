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

package evoagentsimulation.evoagent2dsimulator.experiments;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import evoagentmindelements.EvoAgentMind;
import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_ObjectDetector;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PLObjectListActive;
import evoagentsimulation.evoagent2dsimulator.experiments.elements.CF_NextOnTimeout;
import evoagentsimulation.evoagent2dsimulator.experiments.elements.RW_ClosingOnTarget;
import evoagentsimulation.evoagent2dsimulator.experiments.elements.RW_ForwardMotion;
import evoagentsimulation.evoagent2dsimulator.experiments.elements.RW_SensorOverThreshold;
import evoagentsimulation.evoagent2dsimulator.experiments.elements.RW_Speed;
import evoagentsimulation.evoagent2dsimulator.experiments.elements.RewardFunction;
import evoagentsimulation.evoagent2dsimulator.worldElements.DynamicWorldElement;
import evoagentsimulation.evoagent2dsimulator.worldElements.TargetObject;
import evoagentsimulation.evoagent2dsimulator.worldElements.WorldElement;
import evoagentsimulation.simulationlearning.GeneticAlgorithm;

public class EXP_GTO extends SimulationEnvironment {

	private TargetObject to ;
	private ArrayList<WorldElement> targetObjectList;
	private float targSize = 12.0f;
	
	public EXP_GTO()
	{
		super();
		this.name = "GoToObject";
		hasObstacles = false;
	}

	@Override
	public void initialisation() 
	{
		int WORLD_SIZE = 250;
		setSquareBorders(WORLD_SIZE);
		botStartPos = new Vec2(-00.5f,-0.0f);	
		targetObjectList = new ArrayList<WorldElement>();
		to = new TargetObject(new Vec2(-20,-20), (float)(Math.PI/4), targSize);
		targetObjectList.add(to);
		((S_ObjectDetector)getBot().sensors.get("SENSOBJ")).setTarget(to);
		((S_PLObjectListActive)getBot().sensors.get("ACTOBJ")).setList(targetObjectList);
		getWorldElements().add(to);
		makeWorld();
		to.registerToWorld(getWorld());
		getBot().registerBotToWorld();
		posTargetObject(to);
		
		//Control functions
		controlFunctions.add(new CF_NextOnTimeout(getBot(),this, 15000));

		//Reward functions
		rewardFunctions.add(new RW_SensorOverThreshold(getBot(), 15, getBot().sensors.get("SENSOBJ"), 0.5));
		rewardFunctions.add(new RW_ClosingOnTarget(getBot(), 0.08, to));
		rewardFunctions.add(new RW_ForwardMotion(getBot(), 0.002));
		rewardFunctions.add(new RW_Speed(getBot(), 0.00005));
	}

	public GeneticAlgorithm makeGeneticAlgorihm(int genomeS) {
		GeneticAlgorithm gen = new GeneticAlgorithm(genomeS);
		gen.setMaxGeneration(1000);
		gen.setPopulationSize(300);
		return gen;		
	}

    public EvoAgentMind makeMind() {
    	EvoAgentMind mind = new EvoAgentMind();
		mind.addActuator("MotL");
		mind.addActuator("MotR");
		mind.addSensor("RADOBJ");
		mind.addSensor("DISTOBJ");
		mind.setHiddenLayerCount(2);
		mind.setHiddenLayerSize(4);
		return mind;
	}
	
	@Override
	public void postStepOps() {
		super.postStepOps();
		if(((S_ObjectDetector)getBot().sensors.get("SENSOBJ")).getNormalizedValue() > 0.5)
		{
			posTargetObject(to);
			for(RewardFunction r: rewardFunctions)
				r.reset();
		}
	}
	
	private void posTargetObject(DynamicWorldElement obj) {
		Vec2 pos = new Vec2(generatePositionInBoundaries(30f));
		targSize = Math.max(targSize -1.0f, 1.0f);
		
		while(!checkElementPositionConficts(pos,targSize) || !checkObstaclePositionConficts(pos,targSize+3.0f))
			pos.set(generatePositionInBoundaries(10f));
		obj.setWorldPosition(pos.x,pos.y);
		obj.body.setLinearVelocity(new Vec2(0,0));
		obj.body.setAngularVelocity(0);
		
		obj.size = targSize;
		obj.body.getFixtureList().getShape().m_radius = targSize;
	}

	@Override
	public void reset()
	{
		super.reset();
		targSize = 12.0f;
		posTargetObject(to);
		for(RewardFunction r: rewardFunctions)
			r.reset();
	}
}
