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
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import evoagentsimulation.evoagent2dsimulator.CollisionDefines;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.worldElements.ObstacleStaticCyninder;

public class S_ProximitySensor extends Sensor {

	private fastWorldQueryCallback cb = new fastWorldQueryCallback();
	RayCastClosestCallback ccallback = new RayCastClosestCallback();
	Vec2 targetPoint = new Vec2();
	Vec2 hitPoint = new Vec2();
	double value = 0;
	float MaxDistance = 0;
	float MaxDistanceSquare = 0;
	float halfDistance = 0;
	float halfDistanceReduced = 0;
	double arc = 0;
	int density = 5;
	double angleIncrement = 0.0;
	float deviation = 0.0f;
	double deadZone = 0.005;
	Vec2 leftBound = new Vec2();
	Vec2 rightBound = new Vec2();
	Vec2 selBoxCenter = new Vec2(0,0);
	Vec2 ForwardVec = new Vec2(0,0);
	Vec2 right= new Vec2(0,0);
	Vec2 left= new Vec2(0,0);;
	Vec2 mid= new Vec2(0,0);;
	
	public S_ProximitySensor(Vec2 lp , float la,BotBody2D b, double MaxDist)
	{
		super(lp,la,b);
		MaxDistance = (float)MaxDist;
		halfDistance = MaxDistance/2f;
		halfDistanceReduced = halfDistance - (halfDistance*0.5f);
		MaxDistanceSquare = MaxDistance * MaxDistance;
	}
	
	public S_ProximitySensor(Vec2 lp , float la,BotBody2D b, double MaxDist, double Arc)
	{
		this(lp,la,b,MaxDist);	
		arc = Arc;
		angleIncrement = arc/((double)(density - 1));
		deviation = (float) Math.cos(Math.toRadians(arc));
	}
	
	public S_ProximitySensor(Vec2 lp , float la,BotBody2D b, double MaxDist, double Arc, double DeadZone, int Density)
	{
		this(lp,la,b,MaxDist,Arc);		
		density = Density;
		angleIncrement = arc/((double)(density - 1));
		deadZone = DeadZone;
	}

	public double getValue() {
		computeWorldPosAndAngle();
		value = MaxDistance;
		if(bot.getEnvironment().isCircleWorld())
		{
			value = MaxDistance;
			selBoxCenter.set(worldPosition.x+((float)(Math.cos(worldAngle)*halfDistance)),worldPosition.y+((float)(Math.sin(worldAngle)*halfDistance)));
			ForwardVec.set(((float)(Math.cos(worldAngle)*MaxDistance)),((float)(Math.sin(worldAngle)*MaxDistance)));		
			AABB sel = makeSel();
			bot.getWorld().queryAABB(cb, sel);	
		}
		else
		{
			targetPoint.set(worldPosition);
			targetPoint.addLocal(new Vec2(MaxDistance * (float)Math.cos(worldAngle),MaxDistance * (float)Math.sin(worldAngle)));
			hitPoint.set(targetPoint);
			ccallback.init();
			bot.getWorld().raycast(ccallback, worldPosition, targetPoint);		
			if(ccallback.m_hit)
			{
				hitPoint.set(ccallback.m_point);
				value =  Math.min(value, MathUtils.distance(worldPosition,ccallback.m_point));
				if(value < deadZone)
					return 0;
			}
			if(arc > 0)
			{		
				leftBound.set(worldPosition);
				rightBound.set(worldPosition);
				leftBound.addLocal(new Vec2(MaxDistance * (float)Math.cos(worldAngle+Math.toRadians(arc)),MaxDistance * (float)Math.sin(worldAngle+Math.toRadians(arc))));
				rightBound.addLocal(new Vec2(MaxDistance * (float)Math.cos(worldAngle-Math.toRadians(arc)),MaxDistance * (float)Math.sin(worldAngle-Math.toRadians(arc))));		
				Vec2 targetPointRight = new Vec2();
				Vec2 targetPointLeft = new Vec2();
				for(int i = 1 ; i < density ; i++)
				{
					targetPointLeft.set(worldPosition);
					targetPointRight.set(worldPosition);
					targetPointLeft.addLocal(new Vec2(MaxDistance * (float)Math.cos(worldAngle+Math.toRadians(angleIncrement*i)),MaxDistance * (float)Math.sin(worldAngle+Math.toRadians(angleIncrement*i))));
					targetPointRight.addLocal(new Vec2(MaxDistance * (float)Math.cos(worldAngle-Math.toRadians(angleIncrement*i)),MaxDistance * (float)Math.sin(worldAngle-Math.toRadians(angleIncrement*i))));
					ccallback.init();
					bot.getWorld().raycast(ccallback, worldPosition, targetPointLeft);		
					if(ccallback.m_hit&& MathUtils.distance(worldPosition,ccallback.m_point)< value)
					{
						hitPoint.set(ccallback.m_point);
						value =  Math.min(value, MathUtils.distance(worldPosition,ccallback.m_point));
						if(value < deadZone)
							return 0;
					}
					ccallback.init();
					bot.getWorld().raycast(ccallback, worldPosition, targetPointRight);		
					if(ccallback.m_hit&& MathUtils.distance(worldPosition,ccallback.m_point)< value)
					{
						hitPoint.set(ccallback.m_point);
						value =  Math.min(value, MathUtils.distance(worldPosition,ccallback.m_point));
						if(value < deadZone)
							return 0;
					}			
				}
			}			
		}
		return value;
	}
	
	public Vec2 getWorldPosition()
	{
		return worldPosition;
	}	
	
	public Vec2 getHitPoint()
	{
		return hitPoint;
	}
	
	public double getArc()
	{
		return arc;
	}
	
	public float getMaxDistance()
	{
		return MaxDistance;
	}
	
	public Vec2 getRightBound()
	{
		return rightBound;
	}

	public Vec2 getLeftBound()
	{
		return leftBound;
	}

	public float boundingBoxPointy(int pt)
	{
		switch(pt)
		{
		case 1:
			return selBoxCenter.y-halfDistance;
		case 2:
			return selBoxCenter.y+halfDistance;
		case 3:
			return selBoxCenter.y+halfDistance;
		case 4:
			return selBoxCenter.y-halfDistance;
		}
		return 0;
	}
	
	public float boundingBoxPointx(int pt)
	{
		switch(pt)
		{
		case 1:
			return selBoxCenter.x-halfDistance;
		case 2:
			return selBoxCenter.x-halfDistance;
		case 3:
			return selBoxCenter.x+halfDistance;
		case 4:
			return selBoxCenter.x+halfDistance;
		}
		return 0;
	}

	@Override
	public double getNormalizedValue() {
		value = getValue();
		if(value >= MaxDistance)
			return (normalizedValue = 1.0);
		if(value <= 0)
			return (normalizedValue =0);
		return (normalizedValue =(value/MaxDistance));
	}
	
	public class RayCastClosestCallback implements RayCastCallback {
		
		public boolean m_hit;
		public Vec2 m_point;
		Vec2 m_normal;
		
		public void init() {
			m_hit = false;
		}
		
		public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
			if(fixture.getBody() == bot.body || fixture.m_filter.categoryBits == CollisionDefines.CDTargetObj)
				return -1f;
			m_hit = true;
			m_point = point;
			m_normal = normal;
			return fraction;
		}
	};
	
	private AABB makeSel() {
		AABB ret = new AABB(new Vec2((float)(selBoxCenter.x-halfDistance),(float)(selBoxCenter.y-halfDistance)),
				new Vec2((float)(selBoxCenter.x+halfDistance),(float)(selBoxCenter.y+halfDistance)));
		return ret;
	}

	public class fastWorldQueryCallback implements QueryCallback {
		@Override
		public boolean reportFixture(Fixture fixture) {
			if(fixture.m_filter.categoryBits == CollisionDefines.CDEnv)
			{	
				if(fixture.getBody().getUserData() instanceof ObstacleStaticCyninder)
				{
					float dist = fixture.getBody().getPosition().sub(worldPosition).length();
					right.set(((float)(worldPosition.x+Math.cos(worldAngle+arc)*dist)),((float)(worldPosition.y+Math.sin(worldAngle+arc)*dist)));
					left.set(((float)(worldPosition.x+Math.cos(worldAngle-arc)*dist)),((float)(worldPosition.y+Math.sin(worldAngle-arc)*dist)));
					if(fixture.testPoint(right) ||fixture.testPoint(left))
						value = Math.min(value,dist-fixture.getShape().m_radius);						
				}
			}
			else if (fixture.m_filter.categoryBits == CollisionDefines.CDBot&& fixture.getBody().getUserData() != bot)
			{
				if(fixture.getBody().getUserData() instanceof BotBody2D)
				{
					float dist = fixture.getBody().getPosition().sub(worldPosition).length();
					right.set(((float)(worldPosition.x+Math.cos(worldAngle+arc)*dist)),((float)(worldPosition.y+Math.sin(worldAngle+arc)*dist)));
					left.set(((float)(worldPosition.x+Math.cos(worldAngle-arc)*dist)),((float)(worldPosition.y+Math.sin(worldAngle-arc)*dist)));
					mid.set(((float)(worldPosition.x+Math.cos(worldAngle)*dist)),((float)(worldPosition.y+Math.sin(worldAngle)*dist)));
					if(fixture.testPoint(right) ||fixture.testPoint(left) || fixture.testPoint(mid))
						value = Math.min(value,dist-fixture.getShape().m_radius);						
				}
			}
			return true;
		}
	}
}
