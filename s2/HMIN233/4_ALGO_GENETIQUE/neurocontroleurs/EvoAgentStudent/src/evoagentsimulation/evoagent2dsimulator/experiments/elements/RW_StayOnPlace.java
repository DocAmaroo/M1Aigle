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

import org.jbox2d.common.Vec2;

import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

// Fixe le bot à ça position de départ, il n'a le droit que de tourner sur lui même.

public class RW_StayOnPlace extends RewardFunction{
	
	Vec2 botStartPosition = null;
	
	public RW_StayOnPlace(BotBody2D bot, double rewardStep) {
		super(bot, rewardStep);
	}
	
	@Override
	public double computeRewardValue() {
		
		double reward = 0.0;
		
		if (botStartPosition != null) {
			double distanceSquared = bot.body.getPosition().sub(botStartPosition).lengthSquared();
			reward = rewardStep * -distanceSquared; // 0 sera donc le meilleur score possible
		}
		else if (bot.body.getPosition() != null){
			botStartPosition = new Vec2(bot.body.getPosition());
		}
				
//		System.out.println("RW_StayOnPlace : " + reward);
		
		return reward;
	}
	
	@Override
	public void reset() {
		botStartPosition = null;
	}
}
