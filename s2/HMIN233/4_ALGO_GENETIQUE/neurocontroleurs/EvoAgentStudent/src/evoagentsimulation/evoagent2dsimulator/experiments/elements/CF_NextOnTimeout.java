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


import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class CF_NextOnTimeout extends ControlFunction {
	int tickLimit;
	int tickCounter = 0;
	
	public CF_NextOnTimeout(BotBody2D b,SimulationEnvironment wt, int tl) {
		super(b,wt);
		tickLimit = tl;
	}

	@Override
	public boolean performCheck()
	{
		//System.out.println(tickCounter);
		if(forceNextSignal)
		{
			//System.out.println("force");
			forceNextSignal = false;
			return true;
		}
		else
		{
			tickCounter++;
			if(tickCounter > tickLimit)
				return true;
		}
		return false;
	}
	
	public void reset()
	{
		super.reset();
		//System.out.println("reset " + tickCounter) ;
		tickCounter = 0;
	}
	
	
}
