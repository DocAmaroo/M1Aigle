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

import evoagentapp.EvoAgentAppDefines;
import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;


public abstract class S_PointListener extends Sensor{
	protected SimulationEnvironment simEnvironment;
	protected boolean signalUpdater = false;
	double maxDistance = -1;
	protected Vec2 targetPosition = null;
	
	public S_PointListener(Vec2 lp, float la, BotBody2D b, SimulationEnvironment Env) {
		super(lp, la, b);
		simEnvironment = Env;
	}
	
	public S_PointListener(Vec2 lp, float la, BotBody2D b, SimulationEnvironment Env, double maxDist) {
		this(lp, la, b, Env);
		maxDistance = maxDist;
	}
	
	public abstract boolean updateTarget(boolean sigin);
	
	public  double getValue() {
		if (simEnvironment != null) {
			signalUpdater = updateTarget(signalUpdater);
			if (targetPosition != null)
				return EvoAgentAppDefines.doubleBooleanTrue;
		}
		return EvoAgentAppDefines.doubleBooleanFalse;
	}

	public double getNormalizedValue() {
		return getValue();
	}
	
	public Vec2 getCurrentTargetPosition() {
		if (simEnvironment != null) {
			return targetPosition;
		}
		return null;
	}

	public void setDistance(double maxDist) {
		maxDistance = maxDist;
	}
}
