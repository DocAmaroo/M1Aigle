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


import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.bot.elements.Sensor;

public class RW_AvoidObsOld extends RewardFunction{
	double dist;
	
	public RW_AvoidObsOld(BotBody2D b, double rewardSt, double distance) {
		super(b, rewardSt);
		dist = distance;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double computeRewardValue() {
		//0-9
		double neg = 0.0;
		double pos = 0.0;
		Sensor laz;
		for(int i = 0 ; i < 10 ;i++)
		{
			laz = (Sensor) bot.sensors.get("US_"+(1+i));
			if(laz.getNormalizedValue()<= dist)
			{
				neg += dist - laz.getNormalizedValue();
			}
			else
			{
				pos += (laz.getNormalizedValue()-dist);				
			}
		}
		neg *= -5*rewardStep;
		pos *= rewardStep*0.1;
		return neg+pos;
	}

}
