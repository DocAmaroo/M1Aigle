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

public class S_Battery extends Sensor{
	double decay = 0.00005;
	double value = 0.4;
	double initValue = 0.4;
	
	public void setDecay(double in)
	{
		decay = in;
	}
	
	public S_Battery(Vec2 lp, float la, BotBody2D b) {
		super(lp, la, b);		
	}
	
	public S_Battery(Vec2 lp, float la, BotBody2D b, double d) {
		super(lp, la, b);
		decay = d;
	}

	public S_Battery(Vec2 lp, float la, BotBody2D b, double d, double initv) {
		super(lp, la, b);
		decay = d;
		initValue = initv;
	}
	
	public double getValue() {
		value = value - decay;
		return value;
	}
	
	@Override
	public double getNormalizedValue() {

		normalizedValue = Math.ceil(Math.max(getValue(),0.0)*100)/100;
		return normalizedValue;
	}
	
	@Override
	public void reset()
	{
		value = initValue;
	}	
		
	public void recharge(double amount)
	{
		value+=amount;
		value= Math.min(value,1.0);
	}
}
