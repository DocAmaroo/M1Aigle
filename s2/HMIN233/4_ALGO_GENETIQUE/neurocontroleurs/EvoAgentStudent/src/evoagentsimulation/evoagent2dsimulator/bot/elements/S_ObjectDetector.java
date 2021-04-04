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


import java.util.ArrayList;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import evoagentapp.EvoAgentAppDefines;
import evoagentsimulation.evoagent2dsimulator.CollisionDefines;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.worldElements.TargetObject;
import evoagentsimulation.evoagent2dsimulator.worldElements.WorldElement;

public class S_ObjectDetector extends Sensor{
	
	public double value = EvoAgentAppDefines.doubleBooleanFalse;
	public float offset = 1.0f;	
	public float diameter = 0.05f;
	private ClawQueryCallback cb = new ClawQueryCallback();
	public Vec2 clawPos = new Vec2(0,0);
	


	public S_ObjectDetector(Vec2 lp, float la, BotBody2D b, WorldElement objin, double off) {
		super(lp, la, b);
		offset = (float)off;
	}
	
	public S_ObjectDetector(Vec2 lp, float la, BotBody2D b, double off, double diam) {
		super(lp, la, b);
		offset = (float)off;
		diameter = (float)diam;
	}
	
	public double getValue() {
		value = EvoAgentAppDefines.doubleBooleanFalse;
		computeWorldPosAndAngle();
		clawPos.set(worldPosition.x+((float)(Math.cos(worldAngle)*offset)),worldPosition.y+((float)(Math.sin(worldAngle)*offset)));
		AABB sel = makeSel();
		bot.getWorld().queryAABB(cb, sel);	
		return value;
	}
	
	@Override
	public double getNormalizedValue() {
		value = getValue();
		//System.out.println(value);
		return (normalizedValue = value);
	}
	
	private AABB makeSel() {
		AABB ret = new AABB(new Vec2((float)(clawPos.x-diameter),(float)(clawPos.y-diameter)),new Vec2((float)(clawPos.x+diameter),(float)(clawPos.y+diameter)));
		return ret;
	}

	public class ClawQueryCallback implements QueryCallback {
		@Override
		public boolean reportFixture(Fixture fixture) {
			if(fixture.m_filter.categoryBits == CollisionDefines.CDTargetObj)
			{
				if(((TargetObject)fixture.getBody().getUserData()).isOwner(bot) || !((TargetObject)fixture.getBody().getUserData()).hasOwner())
				{
					value = EvoAgentAppDefines.doubleBooleanTrue;
					return false;
				}
			}
			return true;
		}
	}
	public void setTarget(TargetObject to) {
		//dummy
	}

	public void setTargetList(ArrayList<WorldElement> targetObjectList) {
		//dummy
	};
}
