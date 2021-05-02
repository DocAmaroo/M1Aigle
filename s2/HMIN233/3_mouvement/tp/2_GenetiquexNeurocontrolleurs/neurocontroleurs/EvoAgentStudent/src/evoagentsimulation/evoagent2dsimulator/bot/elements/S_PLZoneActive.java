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

import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.worldElements.VirtualWorldElement;

public class S_PLZoneActive extends S_PointListener{
	private VirtualWorldElement targetZone;
	
	public S_PLZoneActive(Vec2 lp, float la, BotBody2D b, SimulationEnvironment simEnv, VirtualWorldElement targetin) {
		super(lp, la, b,simEnv);
		targetZone = targetin;
	}

	public S_PLZoneActive(Vec2 lp, float la, BotBody2D b, SimulationEnvironment simEnv, double maxDist, VirtualWorldElement targetin) {
		super(lp, la, b,simEnv, maxDist);
		targetZone = targetin;
		targetPosition = targetZone.worldPosition;
	}

	public boolean updateTarget(boolean sigin) {
		computeWorldPosAndAngle();
		return sigin;
	}

	public void setTarget(VirtualWorldElement dz) {
		targetZone = dz;
		targetPosition = targetZone.worldPosition;
	}
}
