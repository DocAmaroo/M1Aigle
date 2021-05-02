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

import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class RW_BelowDistanceFromTarget extends RewardFunction{

	
	public BotBody2D target =null;
	double dist = -1;
	double maxDist=-1;
	
	public RW_BelowDistanceFromTarget(BotBody2D b, double rewardSt, BotBody2D targetin,double maxDistance) {
		super(b, rewardSt);
		target = targetin;
		maxDist=maxDistance;
	}

	@Override
	public double computeRewardValue() {
		double ret = 0.0;
		double curDist =  MathUtils.distance(bot.body.getPosition(),target.body.getPosition());
		if(target != null && dist != -1)
		{
			if(dist<maxDist)
				ret = rewardStep;
		}
		dist = curDist;
		return ret;
	}

	@Override
	public void reset()
	{
		dist = -1;		
	}
}

