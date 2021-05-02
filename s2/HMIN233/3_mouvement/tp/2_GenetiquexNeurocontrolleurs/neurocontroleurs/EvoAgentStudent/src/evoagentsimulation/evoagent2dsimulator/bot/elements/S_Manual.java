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

public class S_Manual extends Sensor{
	boolean autoReset = false;
	
	public S_Manual(Vec2 lp, float la, BotBody2D b, double val) {
		super(lp, la, b);
		defaultValue = val;
		normalizedValue = val;
	}

	public double getValue() {
		if(autoReset)
		{
			double val = normalizedValue;
			normalizedValue = defaultValue;
			return val;			
		}
		else
		{
			return normalizedValue;
		}
	}

	public void setValue(double val) {
		normalizedValue = val;
	}

	@Override
	public double getNormalizedValue() {
		return getValue();
	}
	
	void setAutoReset(boolean reset)
	{
		this.autoReset = reset;
	}
}
