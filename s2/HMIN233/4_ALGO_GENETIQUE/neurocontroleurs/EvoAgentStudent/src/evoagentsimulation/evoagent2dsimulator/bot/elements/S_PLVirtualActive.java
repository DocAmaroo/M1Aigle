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

import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.worldElements.VirtualWorldElement;

public class S_PLVirtualActive extends S_PointListener{
	public VirtualWorldElement target = null;
	
	public S_PLVirtualActive(Vec2 lp, float la, BotBody2D b, SimulationEnvironment env, double maxDist) {
		super(lp, la, b, env, maxDist);
	}

	public S_PLVirtualActive(Vec2 lp, float la, BotBody2D b, SimulationEnvironment env) {
		super(lp, la, b, env);
	}

	public boolean updateTarget(boolean sigin) {
		if (simEnvironment != null) {
			computeWorldPosAndAngle();
			targetPosition = null;
			if (signalUpdater == sigin) {
				signalUpdater = !signalUpdater;
				if(maxDistance < 0 || MathUtils.distance(target.getWorldPosition(), getWorldPosition()) < maxDistance)
					targetPosition = target.getWorldPosition();
			}
		}
		return signalUpdater;
	}
	
	public void setTarget(VirtualWorldElement t)
	{
		target = t;
	}
}
