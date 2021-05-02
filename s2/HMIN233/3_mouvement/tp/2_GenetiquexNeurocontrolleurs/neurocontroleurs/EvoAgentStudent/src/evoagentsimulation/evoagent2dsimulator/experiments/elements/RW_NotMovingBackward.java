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

public class RW_NotMovingBackward extends RewardFunction{

	public RW_NotMovingBackward(BotBody2D b, double rewardStep) {
		super(b, rewardStep);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double computeRewardValue() {
		
		double speed = bot.body.getLocalVector(bot.body.getLinearVelocity()).x;
		
//		System.out.println("RW_NotMovingBackward localLinearVelocity : " + bot.body.getLocalVector(bot.body.getLinearVelocity()));
		
		speed = speed < 0 ? speed : 0; // plus la vitesse est négative (= plus on va en arrière), plus la punition sera importante

//		System.out.println("RW_NotMovingBackward : " + rewardStep * speed);
		
		return rewardStep * speed; // 0 sera donc le meilleur score possible
	}
}
