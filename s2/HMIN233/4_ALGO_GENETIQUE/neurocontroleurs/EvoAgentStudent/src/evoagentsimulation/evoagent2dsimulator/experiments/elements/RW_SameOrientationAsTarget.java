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

public class RW_SameOrientationAsTarget extends RewardFunction{
	
	VirtualWorldElement target;
	Vec2 targetOldPos = null;
	
	public RW_SameOrientationAsTarget(BotBody2D bot, VirtualWorldElement target, double rewardStep) {
		super(bot, rewardStep);
		this.target = target;
	}
	
	@Override
	public double computeRewardValue() {
		
		double reward = 0.0;

		if(target != null && targetOldPos != null) {
			
			Vec2 targetDir = target.getWorldPosition().sub(targetOldPos);
			
			if (targetDir.length() < 1e-6)
				return 0; // si la cible ne bouge pas alors pas de comparaison possible 
			
			Vec2 botDir = new Vec2((float)Math.cos(bot.body.getAngle()), (float)Math.sin(bot.body.getAngle())); // direction du bot normalisé
			targetDir.normalize(); // direction cible normalisé

			double angleDiff = Vec2.dot(botDir, targetDir) - 1.0; // renvoie un valeur entre [-2, 0] (en multipliant par pi on obtient l'angle mais ça n'est pas necessaire
		
//			System.out.println("\nRW_SameHeadingAsTarget botDir : " + botDir + ", targetDir : " + targetDir + "\nangleDiff : " + angleDiff);

			reward = rewardStep * angleDiff; // 0 sera donc le meilleur score possible
		}
		
//		System.out.println("RW_SameHeading : " + reward);

		targetOldPos = new Vec2(target.getWorldPosition());

		return reward;
	}
	
	@Override
	public void reset() {
		targetOldPos = null;
	}
}
