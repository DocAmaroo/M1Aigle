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

package evoagentsimulation.evoagent2dsimulator.bot;

import java.util.HashMap;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import evoagentmindelements.EvoAgentMind;
import evoagentmindelements.modules.ActuatorModule;
import evoagentmindelements.modules.SensorModule;
import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.CollisionDefines;
import evoagentsimulation.evoagent2dsimulator.bot.elements.Actuator;
import evoagentsimulation.evoagent2dsimulator.bot.elements.Sensor;

public class BotBody2D {
	private boolean active = true;
	public double size = 1.0;
	public String label = "";
	public String name = "";
	public int ID = 0;
	public Body body;
	public SimulationEnvironment environment;
	public HashMap<String,Sensor> sensors = new HashMap<String,Sensor>();
	public HashMap<String,Actuator> actuators = new HashMap<String,Actuator>();
	public int contactCounter = 0;

	FixtureDef sd;
	BodyDef bd;
	
	public BotBody2D(SimulationEnvironment env)
	{
		environment = env;
	}
	
	public void step()
	{
		for(Actuator a : actuators.values())
			a.autoStep();
		for(Sensor s : sensors.values())
			s.autoStep();
	}
	
	 protected void setNormalisedActuatorData(Actuator actuator, double parseDouble) {
		 if(actuator != null)
		 {
				actuator.setNormalizedValue(parseDouble);
				actuator.step();			 
		 }
	}

	public World getWorld() {
		return environment.getWorld();
	}

	public SimulationEnvironment getEnvironment() {
		return environment;
	}

	public void reset() {
		for(Sensor s : sensors.values())
			s.reset();
		for(Actuator a : actuators.values())
			a.reset();
	}
	
	public void registerBotToWorld() {
		body = getWorld().createBody(bd);
		sd.filter.categoryBits = CollisionDefines.CDBot;
		sd.filter.maskBits = CollisionDefines.CDAllMask & ~CollisionDefines.CDBot;
		sd.userData = this;
		body.createFixture(sd);
		body.setUserData(this);
	}
	
	public void removeBotFromWorld() {
		getWorld().destroyBody(this.body);
	}
	
	public void initBotPosition(Vec2 startPos,double startAngle)
	{
		bd.position.set(startPos.x,startPos.y);
		bd.angle = (float)startAngle;
	}
	
	public void setPosition(Vec2 botStartPos, double double1) {
		body.setTransform(botStartPos, (float)double1);
	}
	
	public Vec2 getPosition() {
		return body.getPosition();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAngle(float Angle) {
		bd.angle = Angle;
	}	
	
	public void setName(String name) {
		this.name = name;
	}	

	public String getName() {
		return this.name;
	}	

	public void setLabel(String lab) {
		this.label = lab;
	}

	public void setMind(EvoAgentMind evoAgentMind) {
		for(ActuatorModule a : evoAgentMind.getActuatorModules())
			if(actuators.get(a.getID())!=null)
				actuators.get(a.getID()).setMindModule(a);
		for(SensorModule s : evoAgentMind.getSensorModules())
			if(sensors.get(s.getID())!=null)
				sensors.get(s.getID()).setMindModule(s);
	}	
}
