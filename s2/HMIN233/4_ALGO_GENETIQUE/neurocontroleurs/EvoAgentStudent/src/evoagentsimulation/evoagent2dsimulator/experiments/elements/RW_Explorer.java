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

import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.common.Vec2;

import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class RW_Explorer extends RewardFunction{
	private HashMap<Integer,ArrayList<Integer>> explored = new HashMap<>();
	float offsetX,offsetY;
	double subdivision;
	
	public RW_Explorer(BotBody2D b,double rewardS, Vec2[] corners, double subdiv) {
		super(b,rewardS);
		offsetX = Math.min(corners[0].x, Math.min(corners[1].x, Math.min(corners[2].x, corners[3].x)));
		offsetY = Math.min(corners[0].y, Math.min(corners[1].y, Math.min(corners[2].y, corners[3].y)));
		subdivision = subdiv;
	}
	
	@Override
	public double computeRewardValue() {
		int x,y;
		if(explored.containsKey((x=computeSubdiv(bot.body.getPosition().x-offsetX))))
		{
			if(explored.get(x).contains((y=computeSubdiv(bot.body.getPosition().y-offsetY))))
			{
				//return -rewardStep*0.001;				
				return 0.0;
			}
			else
			{
				explored.get(x).add(y);
				return rewardStep;
			}
		}
		else
		{
			explored.put(x, new ArrayList<>());
			explored.get(x).add(computeSubdiv(bot.body.getPosition().y-offsetY));			
			return rewardStep;
		}
	}

	@Override
	public void reset()
	{
		explored.clear();
	}

	private int computeSubdiv(float in)
	{
		return (int)(in/subdivision);
	}
	
}
