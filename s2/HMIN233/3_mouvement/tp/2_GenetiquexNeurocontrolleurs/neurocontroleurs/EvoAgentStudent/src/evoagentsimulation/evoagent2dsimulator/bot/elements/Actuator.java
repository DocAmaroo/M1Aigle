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

import evoagentmindelements.modules.ActuatorModule;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class Actuator extends BotElement{
	public double normalizedValue = 0.0;
	ActuatorModule actuator=null;
	
	public Actuator(Vec2 lp , float la, BotBody2D b)
	{
		super(lp,la,b);
	}

	public void setNormalizedValue(double parseDouble) {
		normalizedValue = parseDouble;		
	}

	public void step() {
		//Override me
	}

	public void reset() {
	}

	public void autoStep() {
		if(actuator!=null)
		{
			normalizedValue = actuator.getMotorValue();
			step();			
		}
	}

	public void setMindModule(ActuatorModule actuator) {
		this.actuator = actuator;
	}	
}
