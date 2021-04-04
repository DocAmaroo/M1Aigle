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

package evoagentsimulation.evoagent2dsimulator.bot.elements;

import org.jbox2d.common.Vec2;

import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class A_WheelVariableSpeed extends Actuator {
	public float maxForce = 30.0f;
	public float value = 0.0f;
	SensorConditional cond = null;

	public A_WheelVariableSpeed(Vec2 lp , float la, BotBody2D b,float mf,SensorConditional c){
		super(lp,la,b);
		maxForce = mf;
		cond = c;
	}
	
	@Override
	public void step() {
		computeWorldPosAndAngle();
		computeNormalizedValue();
		Vec2 f = new Vec2((float)Math.cos(worldAngle)*value,(float)Math.sin(worldAngle)*value);
		Vec2 p = new Vec2(worldPosition);
		bot.body.applyForce(f, p);
	}

	private void computeNormalizedValue() {		
		float force;
		if(cond.getValue() >=0.9)
			force = (float) (maxForce / 2.0);
		else
			force = maxForce;
		value = (((float)normalizedValue*2.0f)-1.0f)*force;
	}
}
