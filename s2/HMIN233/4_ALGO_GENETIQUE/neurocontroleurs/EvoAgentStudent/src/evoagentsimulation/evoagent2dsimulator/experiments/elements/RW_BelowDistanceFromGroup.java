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

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class RW_BelowDistanceFromGroup extends RewardFunction{

	
	
	private Vec2 target =null;
	ArrayList<BotBody2D> bots;
	double dist = -1;
	double maxDist=-1;
	
	public RW_BelowDistanceFromGroup(BotBody2D b, double rewardSt,ArrayList<BotBody2D> Bots,double maxDistance) {
		super(b, rewardSt*(Bots.size()-1));
		this.bots = Bots;
		maxDist=maxDistance;
	}

	@Override
	public double computeRewardValue() {
		double ret = 0.0;
		
		target =computeCentre();
		
		double curDist =  MathUtils.distance(bot.body.getPosition(),target);

		if(target != null && dist != -1)
		{
			if(dist<maxDist)
				ret = rewardStep;
		}
		dist = curDist;
		return ret;
	}

	private Vec2 computeCentre()
	{
		target = null;
		boolean trig = false;
		double x = 0;
		double y = 0;
		double count = 0;
		for (int i = 0; i < bots.size(); i++) {
				for (BotBody2D B : bots)
					if (MathUtils.distance(B.body.getPosition(), bot.body.getPosition()) <= maxDist) {
						trig = true;
						x+=B.body.getPosition().x;
						y+=B.body.getPosition().y;
						count++;
					}
			
		}
		if(trig)
		{
			target = new Vec2((float)(x/count),(float)(y/count));
		}
		return target;
	}
	
	@Override
	public void reset()
	{
		dist = -1;		
	}
}

