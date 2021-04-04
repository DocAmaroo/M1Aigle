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

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.CollisionDefines;
import evoagentsimulation.evoagent2dsimulator.bot.elements.A_AutoClaw;
import evoagentsimulation.evoagent2dsimulator.bot.elements.A_Wheel;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_MovementSensor;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_ObjectDetector;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PLObjectListActive;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PointListenerDistance;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PointListenerOrient;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_ProximitySensor;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_Random;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_PLZoneActive;
import evoagentsimulation.evoagent2dsimulator.bot.elements.S_ZonePresence;

public class BotType8 extends BotBody2D{
	SimulationEnvironment singEnv = null;

	public BotType8(SimulationEnvironment env)
	{
		super(env);
		sd = new FixtureDef();
		sd.shape = new CircleShape(); 
		sd.shape.m_radius = (float)size;
		sd.friction = 0.0f;
		sd.density = 2.0f;
		sd.filter.categoryBits = CollisionDefines.CDBot;
		sd.filter.maskBits = CollisionDefines.CDAllMask;

		bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.angularDamping = 20.0f;
		bd.linearDamping = 5.0f;
		
		bd.allowSleep = false;

		//Sensors
		//obstacles
		double proxSensorArc = 6.0;
		sensors.put("S1",new S_ProximitySensor(new Vec2((float)size,0.0f),0, this, 12.0,proxSensorArc));
		sensors.put("S2",new S_ProximitySensor(new Vec2((float)(size*Math.cos(((double)1.0*(Math.PI/8)))),(float)(size*Math.sin(((double)1.0*(Math.PI/8))))),(float)((double)1.0*(Math.PI/8)), this, 12.0,proxSensorArc));
		for(int i = 1 ; i < 8 ;i++)
			sensors.put("S" + (2+i),new S_ProximitySensor(new Vec2((float)(size*Math.cos((((double)i)*(Math.PI/4)))),(float)(size*Math.sin((((double)i)*(Math.PI/4))))),(float)(((double)i)*(Math.PI/4)), this, 12.0,proxSensorArc));
		sensors.put("S10",new S_ProximitySensor(new Vec2((float)(size*Math.cos(((double)15.0*(Math.PI/8)))),(float)(size*Math.sin(((double)15.0*(Math.PI/8))))),(float)((double)15.0*(Math.PI/8)), this, 12.0,proxSensorArc));

		//movement
		sensors.put("DOF",new S_MovementSensor(new Vec2(0,0),0, this,20));	

		//targets 
		S_PLObjectListActive eAct = new S_PLObjectListActive(new Vec2(0,0),0, this, env, null);
		sensors.put("ACTOBJ",eAct);
		sensors.put("DISTOBJ",new S_PointListenerDistance(new Vec2(0,0),0, this,eAct));
		sensors.put("RADOBJ",new S_PointListenerOrient(new Vec2(0,0),0, this,eAct));
		sensors.put("SENSOBJ",new S_ObjectDetector(new Vec2((float)size,0),0, this,2.0,0.2));
		S_PLZoneActive zAct = new S_PLZoneActive(new Vec2(0,0),0, this, env, null);
		sensors.put("ACTDZ",zAct);
		sensors.put("DISTDZ",new S_PointListenerDistance(new Vec2(0,0),0, this,zAct));
		sensors.put("RADDZ",new S_PointListenerOrient(new Vec2(0,0),0, this,zAct));
		sensors.put("SENSDZ",new S_ZonePresence(new Vec2(0,0),0, this,null));
		
		//Other
		sensors.put("RANDOM",new S_Random(new Vec2(0,0),0, this));
		
		//Actuators
		actuators.put("MotL",new A_Wheel(new Vec2(0.0f,-(float)size),0, this,80.0f));
		actuators.put("MotR",new A_Wheel(new Vec2(0.0f,(float)size),0, this,80.0f));
		actuators.put("EMAG",new A_AutoClaw(new Vec2((float)size,0f),0, this,1.5f));
	}
}

