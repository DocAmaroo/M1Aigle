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

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class S_PointListenerDistance extends Sensor{
	private S_PointListener signalListener;
	private float MaxDistance = 100.0f;
	private boolean signalUpdater = false;
	
	double value = 0.0;
	
	public S_PointListenerDistance(Vec2 lp, float la, BotBody2D b, S_PointListener listner, double distMax) {
		super(lp, la, b);
		signalListener = listner;
		MaxDistance = (float)distMax;
	}
	
	public S_PointListenerDistance(Vec2 lp, float la, BotBody2D b, S_PointListener listner) {
		super(lp, la, b);
		signalListener = listner;
	}
	
	public double getValue() {
		signalUpdater = signalListener.updateTarget(signalUpdater);
		Vec2 target = signalListener.getCurrentTargetPosition();
			if(target != null)
			{
				///Vec2 vec = new Vec2(target.x-getWorldPosition().x,target.y-getWorldPosition().y);
				return MathUtils.distance(target, getWorldPosition());
			}
			else
				return MaxDistance;
		}
		
		@Override
		public double getNormalizedValue() {
			value = getValue();
			return Math.min(1.0, value / MaxDistance);
		}	
		
		public S_PointListener getListener()
		{
			return 	signalListener;
		}
		
		
		public Vec2 getWorldPosition()
		{
			return signalListener.getWorldPosition();
		}
		
		public void setDistance(float dist)
		{
			MaxDistance = dist;
		}
}
