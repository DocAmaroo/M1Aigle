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

import org.jbox2d.common.Vec2;

import evoagentmindelements.EvoAgentMind;
import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_ObjectDetector;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PLObjectListActive;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PLZoneActive;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_ZonePresence;
import evoagentsimulation.evoagent2dsimulator.experiments.elements.RW_ManualReward;
import evoagentsimulation.evoagent2dsimulator.experiments.elements.RewardFunction;
import evoagentsimulation.evoagent2dsimulator.worldElements.DynamicWorldElement;
import evoagentsimulation.evoagent2dsimulator.worldElements.TargetObject;
import evoagentsimulation.evoagent2dsimulator.worldElements.TriggerZone;
import evoagentsimulation.simulationlearning.GeneticAlgorithm;

public class EXP_Collect extends SimulationEnvironment {

	private TriggerZone dz ;
	private TargetObject to ;
	private RW_ManualReward manualReward;
	
	public EXP_Collect()
	{
		super();
		this.name = "Collect";
		hasObstacles = true;
		makeCircleWorld = true;
	}
	
	public void initialisation() 
	{		
		botStartPos = new Vec2(-00.5f,-0.0f);		    
		minObstacleSize = 2.2;
	    maxObstacleSizeVariability = 4.0;
	    maxObstacleSpacingVariability = 12.0;
	    obstacleSpacing = 48.0;	
		int WORLD_SIZE = 250;
		setSquareBorders(WORLD_SIZE);
		dz = new TriggerZone(new Vec2(-50,-50), 0,10);
		((S_ZonePresence)getBot().sensors.get("SENSDZ")).setTarget(dz);
		((S_PLZoneActive)getBot().sensors.get("ACTDZ")).setTarget(dz);
		getWorldElements().add(dz);
		to = new TargetObject(new Vec2(-20,-20), (float)(Math.PI/4), 1.0f);
		((S_ObjectDetector)getBot().sensors.get("SENSOBJ")).setTarget(to);
		((S_PLObjectListActive)getBot().sensors.get("ACTOBJ")).setTarget(to);
		getWorldElements().add(to);
		makeWorld();
		to.registerToWorld(getWorld());
		getBot().registerBotToWorld();
		posTargetObject(to);

		// ...
		//controlFunctions.add(new CF_NextOnCollisionAndTimeout(getBot(),this, ...));
		//rewardFunctions.add(...);
	}

	public GeneticAlgorithm makeGeneticAlgorihm(int genomeS) {
		GeneticAlgorithm gen = new GeneticAlgorithm(genomeS);

		// ...
		
		return gen;		
	}

    public EvoAgentMind makeMind() {
    	EvoAgentMind mind = new EvoAgentMind();
    	
		// ...
    	//mind.addActuator("...");
		//mind.addSensor("...");
		//mind.set ...;
		
    	return mind;
	}
	
	@Override
	public void postStepOps() {
		super.postStepOps();
		if(dz.isPointInZone(to.getWorldPosition())&&getBot().actuators.get("EMAG").normalizedValue<0.5)
		{
			posTargetObject(to);
			for(RewardFunction r: rewardFunctions)
				r.reset();
			manualReward.addToCurrentValue(300);
		}
	}
	
	private void posTargetObject(DynamicWorldElement obj) {
		Vec2 pos = new Vec2(generatePositionInBoundaries());
		while(!checkElementPositionConficts(pos,50.0f) || !checkObstaclePositionConficts(pos,5.0f))
			pos.set(generatePositionInBoundaries());
		obj.setWorldPosition(pos.x,pos.y);
		obj.body.setLinearVelocity(new Vec2(0,0));
		obj.body.setAngularVelocity(0);
	}

	@Override
	public void reset()
	{
		super.reset();
		posTargetObject(to);
		for(RewardFunction r: rewardFunctions)
			r.reset();
	}
}
