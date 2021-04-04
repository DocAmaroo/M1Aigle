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
import evoagentsimulation.evoagent2dsimulator.worldElements.VirtualWorldElement;

public class RW_FixedDistanceFromTarget extends RewardFunction{
	
	VirtualWorldElement target;
	double oldTargetDist = -1;
	
	public RW_FixedDistanceFromTarget(BotBody2D bot, VirtualWorldElement target, double rewardStep) {
		super(bot, rewardStep);
		this.target = target;
	}
	
	@Override
	public double computeRewardValue() {
		
		double reward = 0.0;
		double targetDist = MathUtils.distanceSquared(bot.body.getPosition(), target.getWorldPosition());

		if(target != null && oldTargetDist != -1) {
			
			double distDiff = Math.abs(oldTargetDist - targetDist);
			
//			System.out.println("oldTargetDist : " + oldTargetDist + "\ntargetDist : " + targetDist + "\ndistDiff : " + distDiff);

			reward = rewardStep * -(distDiff); // 0 sera donc le meilleur score possible
		}
		
//		System.out.println("RW_FixedDistance : " + reward);

		oldTargetDist = targetDist;

		return reward;
	}
	
	@Override
	public void reset() {
		oldTargetDist = -1;
	}

}
