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

package evoagentsimulation.evoagent2dsimulator.experiments.elements;

import org.jbox2d.common.Vec2;

import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class CF_NextOnMovingBackwardTooMuch extends ControlFunction {
	
	Vec2 backMovementStartPos = null;
	double maxDist;
	
	public CF_NextOnMovingBackwardTooMuch(BotBody2D bot, SimulationEnvironment worldThread, double maxDist) {
		super(bot, worldThread);
		this.maxDist = maxDist;
	}

	@Override
	public boolean performCheck()
	{
		double speed = bot.body.getLocalVector(bot.body.getLinearVelocity()).x;
				
		// On se déplace en arrière quand speed < 0
		
		if (speed < 0) {
			
			if (backMovementStartPos == null) {
				backMovementStartPos = new Vec2(bot.body.getPosition());
			}
			else {
				double backDistance = bot.body.getPosition().sub(backMovementStartPos).length();
				return backDistance > maxDist;
			}
		}
		
		return false;
	}
	
	public void reset()
	{
		backMovementStartPos = null;
	}
}
