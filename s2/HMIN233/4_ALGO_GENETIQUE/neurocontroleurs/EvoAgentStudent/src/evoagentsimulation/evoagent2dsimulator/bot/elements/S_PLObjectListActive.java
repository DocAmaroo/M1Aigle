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

package evoagentsimulation.evoagent2dsimulator.bot.elements;

import java.util.ArrayList;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.worldElements.WorldElement;

public class S_PLObjectListActive extends S_PointListener{
	private ArrayList<WorldElement> list = null;
	
	public S_PLObjectListActive(Vec2 lp, float la, BotBody2D b, SimulationEnvironment simEnv, ArrayList<WorldElement> inlist) {
		super(lp, la, b,simEnv);
		list = inlist;
	}
	
	public S_PLObjectListActive(Vec2 lp, float la, BotBody2D b, SimulationEnvironment simEnv, double maxDist, ArrayList<WorldElement> inlist) {
		super(lp, la, b,simEnv);
		list = inlist;
		maxDistance = maxDist;
	}

	public boolean updateTarget(boolean sigin) {
		if (simEnvironment != null && list != null) {
			computeWorldPosAndAngle();
			double ndist = maxDistance;
			double tmpDist;
			if (signalUpdater == sigin) {
				targetPosition = null;
				signalUpdater = !signalUpdater;
				for (WorldElement E : list) {
					if (!E.lock && ((tmpDist = MathUtils.distance(E.getWorldPosition(), bot.body.getPosition())) <= ndist || ndist < 0)){
								ndist = tmpDist;
								targetPosition = E.getWorldPosition();
					}
				}
			}
		}
		return signalUpdater;
	}

	public void setList(ArrayList<WorldElement> inlist)
	{
		list = inlist;
	}
	
	public void setTarget(WorldElement targ) {
		list = new ArrayList<WorldElement>();
		list.add(targ);
	}

}
