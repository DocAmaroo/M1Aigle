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
import evoagentsimulation.evoagent2dsimulator.worldElements.WorldElement;

public class RW_Collect extends RewardFunction{
	RW_ClosingOnTarget objrw;
	RW_ClosingOnTarget dzrw;
	Sensor sensor;
	double prevvalue;
	
	public RW_Collect(BotBody2D b, double rewardSt,WorldElement obj,WorldElement dz,Sensor sens) {
		super(b, rewardSt);
		objrw = new RW_ClosingOnTarget(b, rewardSt, obj);
		dzrw = new RW_ClosingOnTarget(b, rewardSt, dz);
		sensor = sens;
	}

	public double computeRewardValue() {
		double ret = 0.0;
		if((prevvalue <=0.5 && sensor.getNormalizedValue()> 0.5)||(prevvalue >0.5 && sensor.getNormalizedValue()<= 0.5))
		{
			objrw.reset();
			dzrw.reset();
		}
		if(sensor.getNormalizedValue() < 0.5)
			ret = objrw.computeRewardValue();
		else
			ret = dzrw.computeRewardValue();
		prevvalue = sensor.getNormalizedValue();
		return ret;
	}
	
	@Override
	public void reset()
	{
		objrw.reset();
		dzrw.reset();
	}

}

