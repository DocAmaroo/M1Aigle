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

// Quand la cible est immobile encourage le bot à ne pas tourner ni avancer

public class RW_FreezeWhenTargetStop extends RewardFunction{
	
	VirtualWorldElement target;
	double botOldAngle = -1;
	Vec2 botOldPos = null;
	Vec2 targetOldPos = null;
	
	public RW_FreezeWhenTargetStop(BotBody2D bot, VirtualWorldElement target, double rewardStep) {
		super(bot, rewardStep);
		this.target = target;
	}
	
	@Override
	public double computeRewardValue() {
		
		double reward = 0.0;

		if(target != null && targetOldPos != null) {
			
			double targetSpeed = target.getWorldPosition().sub(targetOldPos).length();
			
			if (targetSpeed > 1e-6)
				return 0; // la cible bouge encore, pas de comparaison possible
			
			Vec2 botDir = new Vec2((float)Math.cos(bot.body.getAngle()), (float)Math.sin(bot.body.getAngle())); // direction du bot
			Vec2 botOldDir = new Vec2((float)Math.cos(botOldAngle), (float)Math.sin(botOldAngle)); // ancienne direction
								
			double angleDiff = Vec2.dot(botDir, botOldDir) - 1.0; // renvoie un valeur entre [-2, 0], 0 -> meme angle, -2 -> angle opposé
			double botSpeed = bot.body.getPosition().sub(botOldPos).length();
						
//			System.out.println("\nRW_FreezeWhenTargetStop : angleDiff : " + angleDiff + " botSpeed : " + botSpeed + " targetSpeed : " + targetSpeed);
			
			reward = rewardStep * (angleDiff - botSpeed); // 0 sera donc le meilleur score possible
		}
		
//		System.out.println("RW_FreezeWhenTargetStop : " + reward);
		
		botOldAngle = bot.body.getAngle();
		botOldPos = new Vec2(bot.body.getPosition());
		targetOldPos = new Vec2(target.getWorldPosition());

		return reward;
	}
	
	@Override
	public void reset() {
		botOldAngle = -1;
		botOldPos = null;
		targetOldPos = null;
	}
}
