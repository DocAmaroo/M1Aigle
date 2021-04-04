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

import org.jbox2d.common.MathUtils;

import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.worldElements.TargetObject;

public class CF_NextOnCollisionWithTarget extends ControlFunction{
	
	TargetObject to;
	float targSize;
	
	public CF_NextOnCollisionWithTarget(BotBody2D b,SimulationEnvironment wt, TargetObject t, float size) {
		super(b,wt);
		to=t;
		targSize=size;
	}

	@Override
	public boolean performCheck()
	{
		if(forceNextSignal)
		{
			forceNextSignal = false;
			return true;
		}
		else
		{
			double distance =  MathUtils.distance(bot.body.getPosition(),to.getWorldPosition());
			if(distance < this.targSize +5f)
				return true;
		}
		return false;
	}
	
	public void reset()
	{
		super.reset();
	}
}
