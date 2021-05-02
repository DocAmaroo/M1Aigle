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

import java.util.LinkedList;

import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class RW_StraightMovement extends RewardFunction{
	LinkedList<Double> posList = new LinkedList<>();
	
	public RW_StraightMovement(BotBody2D b, double rewardSt) {
		super(b, rewardSt);
		// TODO Auto-generated constructor stub
	}
		
	int listSize = 100;
	double value = 0;
	int tickCount = 0;
	int tickUpdate = 10;

	public void updateList()
	{
		if(Math.abs(bot.actuators.get("MotL").normalizedValue-bot.actuators.get("MotL").normalizedValue)>0.2)
			posList.add(Math.pow(Math.abs(bot.actuators.get("MotL").normalizedValue-bot.actuators.get("MotR").normalizedValue),1.0)*20.0);
		else
			posList.add(Math.pow(Math.abs(bot.actuators.get("MotL").normalizedValue-bot.actuators.get("MotR").normalizedValue),1.0));	
		if(posList.size()>listSize)
			posList.removeFirst();		
	}
	
	@Override
	public double computeRewardValue() {
		double ret = 0.0;
		if(tickCount == 0)
		{
			updateList();
			double totalSum = 0;
			for(Double v : posList)
			{
				totalSum+=v;
			}
			ret = -totalSum*rewardStep;
			tickCount++;
		}
		else
		{
			if(tickCount >= tickUpdate)
				tickCount = 0;
			else
				tickCount++;
		}
		
//		System.out.println("RW_straightMovement : " + ret);

		return ret;
	}
	
	public void reset()
	{
		tickCount = 0;
		posList.clear();
	}

}
