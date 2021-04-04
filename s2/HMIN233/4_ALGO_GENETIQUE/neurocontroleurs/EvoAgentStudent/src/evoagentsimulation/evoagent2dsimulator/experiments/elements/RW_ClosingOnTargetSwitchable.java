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

public class RW_ClosingOnTargetSwitchable extends RewardFunction{
	public VirtualWorldElement target =null;
	double dist = -1;
	boolean state = false;
	
	public RW_ClosingOnTargetSwitchable(BotBody2D b, double rewardSt, VirtualWorldElement targetin) {
		super(b, rewardSt);
		target = targetin;
	}

	@Override
	public double computeRewardValue() {
		double ret = 0.0;
		if(state)
		{
			double curDist =  MathUtils.distance(bot.body.getPosition(),target.getWorldPosition());
			if(target != null && dist != -1)
			{
				ret = rewardStep*(dist-curDist);
			}
			dist = curDist;			
		}
		return ret;
	}

	@Override
	public void reset()
	{
		dist = -1;		
	}
	
	public void switchState(boolean st)
	{
		state = st;
		if(!state)
			dist = -1;
	}	
}
