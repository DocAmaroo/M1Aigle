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

public class RW_TeamScores extends RewardFunction{
	double scoreAgainstValue = 0;
	double scoreAgainst = 0.0;
	double scoreFor = 0.0;
	
	public RW_TeamScores(double rewardScoreFor, double rewardScoreAgainst) {
		super(null, rewardScoreFor);
		scoreAgainstValue = rewardScoreAgainst;
	}

	@Override
	public double computeRewardValue() {
		double ret =(scoreFor * rewardStep) + (scoreAgainst * scoreAgainstValue);
		scoreAgainst = 0.0;
		scoreFor = 0.0;
		return ret;
	}
	
	public void reset()
	{
		scoreAgainst = 0.0;
		scoreFor = 0.0;
	}

	public void scoreFor() {
		scoreFor = 1.0;
	}

	public void scoreAgainst() {
		scoreAgainst = 1.0;
	}
}
