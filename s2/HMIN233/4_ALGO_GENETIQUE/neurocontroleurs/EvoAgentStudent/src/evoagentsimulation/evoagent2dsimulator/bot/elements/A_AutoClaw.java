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

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import evoagentsimulation.evoagent2dsimulator.CollisionDefines;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.worldElements.TargetObject;

public class A_AutoClaw extends Actuator {
	Body obj = null;
	double prevValue = 0.0;
	private ClawQueryCallback cb = new ClawQueryCallback();
	double range = 0.01;
	public Vec2 clawPos = new Vec2(0,0);
	public Vec2 clawPosObject;
	public float objSize = 1f;
	
	public A_AutoClaw(Vec2 lp, float la, BotBody2D b, double rangein) {
		super(lp, la, b);
		range = rangein;
	}

	@Override
	public void step() {
		computeWorldPosAndAngle();	
		clawPos.set(worldPosition.x+((float)(Math.cos(worldAngle)*range)),worldPosition.y+((float)(Math.sin(worldAngle)*range)));
		//System.out.println("claw : " +normalizedValue);
		if(normalizedValue >= 0.5 && prevValue < 0.5)
		{
			//close claw
			if(obj==null)
			{
				AABB sel = makeSel();
				bot.getWorld().queryAABB(cb, sel);	
				if(obj!= null)
					lockObj();
				//System.out.println("Autoclaw grab");
			}
		}
		else if(normalizedValue < 0.5 && prevValue >= 0.5)
		{
			//open claw 
			if(obj!= null)
				releaseObj();
			obj = null;
		}
		if(obj!=null)
		{
			((TargetObject)obj.getUserData()).lock = true;
			clawPosObject = new Vec2();
			clawPosObject.set(worldPosition.x+((float)(Math.cos(worldAngle)*(objSize+0.5))),worldPosition.y+((float)(Math.sin(worldAngle)*(objSize+0.5))));
			obj.setTransform(clawPosObject, 0);	
			obj.setAngularVelocity(0);
			obj.setLinearVelocity(new Vec2(0,0));
		}
		prevValue = normalizedValue;	
	}

	private AABB makeSel() {
		AABB ret = new AABB(new Vec2((float)(clawPos.x-range),(float)(clawPos.y-range)),new Vec2((float)(clawPos.x+range),(float)(clawPos.y+range)));
		return ret;
	}

	public class ClawQueryCallback implements QueryCallback {
		@Override
		public boolean reportFixture(Fixture fixture) {
			if(fixture.m_filter.categoryBits == CollisionDefines.CDTargetObj)
			{
				obj = fixture.getBody();
				objSize = fixture.m_shape.m_radius;
				return false;
			}
			return true;
		}
	}
	
	public boolean hasObject()
	{
		if(obj != null)
			return true;
		return false;		
	}
	
	public TargetObject getObject()
	{
		return (TargetObject)(obj.getUserData());		
	}
	
	@Override
	public void reset()
	{
		if(obj != null)
			releaseObj();
		obj = null;
		prevValue = 0.0;
	}
	
	private void lockObj()
	{
		getObject().lock = true; 
		getObject().owner = bot;
	}
	
	private void releaseObj()
	{
		getObject().lock = false; 
		getObject().owner = null;
	}
}
