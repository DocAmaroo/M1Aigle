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
import evoagentsimulation.evoagent2dsimulator.worldElements.VirtualWorldElement;

public class RW_SameSpeedAsTarget extends RewardFunction{
	
	VirtualWorldElement target;
	Vec2 targetOldPos = null;
	Vec2 botOldPos = null;
	
	public RW_SameSpeedAsTarget(BotBody2D bot, VirtualWorldElement target, double rewardStep) {
		super(bot, rewardStep);
		this.target = target;
	}
	
	@Override
	public double computeRewardValue() {
		
		double reward = 0.0;

		if(target != null && targetOldPos != null) {
			
			Vec2 botTranslation = bot.body.getPosition().sub(botOldPos);
			Vec2 targetTranslation = target.getWorldPosition().sub(targetOldPos);
			
//			System.out.println("RW_SameSpeedAsTarget botTranslation : " + botTranslation + ", targetTranslation : " + targetTranslation);
			
			double botSpeed = botTranslation.lengthSquared();
			double targetSpeed = targetTranslation.lengthSquared();

			double speedDiff = Math.abs(botSpeed - targetSpeed); // on compare le carré des vitesses pour un gain de performance

			reward = rewardStep * -(speedDiff); // 0 sera donc le meilleur score possible
		}
		
//		System.out.println("RW_SameSpeed : " + reward);

		botOldPos = new Vec2(bot.body.getPosition());
		targetOldPos = new Vec2(target.getWorldPosition());

		return reward;
	}
	
	@Override
	public void reset() {
		targetOldPos = null;
		botOldPos = null;
	}
}
