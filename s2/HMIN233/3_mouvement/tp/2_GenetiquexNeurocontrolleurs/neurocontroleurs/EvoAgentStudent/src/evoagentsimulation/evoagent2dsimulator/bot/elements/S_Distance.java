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
import evoagentsimulation.evoagent2dsimulator.worldElements.VirtualWorldElement;

// use point listener sensors
@Deprecated
public class S_Distance extends Sensor{
	public VirtualWorldElement target = null;
	private double maxDistance;
	S_Radar rad = null;
	
	public S_Distance(Vec2 lp, float la, BotBody2D b, VirtualWorldElement targetin, double maxDist) {
		super(lp, la, b);
		target = targetin;
		maxDistance = maxDist;
	}
	
	public S_Distance(Vec2 lp, float la, BotBody2D b, S_Radar radSens, double maxDist) {
		super(lp, la, b);
		rad = radSens;
		target = rad.getCurrentTarget();
		maxDistance = maxDist;
	}

	public double getValue() {
		if(rad != null)
			target = rad.getCurrentTarget();
		if(target != null){
			computeWorldPosAndAngle();
			Vec2 vec = new Vec2(target.getWorldPosition().x-worldPosition.x,target.getWorldPosition().y-worldPosition.y);
			return vec.length();
		}
		else
			return maxDistance;
	}
	
	@Override
	public double getNormalizedValue() {
		normalizedValue = Math.min(1.0, getValue() / maxDistance);
		return normalizedValue;
	}

	public void setTarget(VirtualWorldElement dz) {
		target = dz;
	}	
}
