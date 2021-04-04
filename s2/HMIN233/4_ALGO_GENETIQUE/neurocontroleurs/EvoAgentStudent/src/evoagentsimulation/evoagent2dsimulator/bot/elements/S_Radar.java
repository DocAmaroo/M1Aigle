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

import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.worldElements.VirtualWorldElement;
import evoagentsimulation.evoagent2dsimulator.worldElements.WorldElement;

//use Point listener sensors
@Deprecated
public class S_Radar extends Sensor{
	public VirtualWorldElement target = null;
	public VirtualWorldElement targetFromList = null;
	private ArrayList<WorldElement> targetlist= null;
	private double value;
	
	public S_Radar(Vec2 lp, float la, BotBody2D b, VirtualWorldElement targetin) {
		super(lp, la, b);
		target = targetin;
	}

	public double getValue() {
		if(target != null)
		{
			computeWorldPosAndAngle();
			Vec2 vec = new Vec2(target.getWorldPosition().x-worldPosition.x,target.getWorldPosition().y-worldPosition.y);
			vec = bot.body.getLocalVector(vec);			
			return Math.atan2(vec.y, vec.x);
		}
		else if(targetlist != null && targetlist.size() > 0)
		{
			double ndist = -1;
			targetFromList = null;
			for (WorldElement E : targetlist) {
				if (!E.lock && (((MathUtils.distance(E.getWorldPosition(), bot.body.getPosition())) <= ndist) || ndist == -1)) {
							ndist = MathUtils.distance(E.getWorldPosition(), bot.body.getPosition());
							targetFromList = E;
				}
			}
			if(targetFromList != null)
			{
				computeWorldPosAndAngle();
				Vec2 vec = new Vec2(targetFromList.getWorldPosition().x-worldPosition.x,targetFromList.getWorldPosition().y-worldPosition.y);
				vec = bot.body.getLocalVector(vec);			
				return Math.atan2(vec.y, vec.x);
			}
			else
				return 0.0;				
		}
		else
			return 0.0;
	}
	
	@Override
	public double getNormalizedValue() {
		value = getValue();
		//System.out.println((value/(Math.PI*2.0))+0.5);
		return (-value/(Math.PI*2.0))+0.5;
	}

	public void setTarget(VirtualWorldElement dz) {
		target = dz;
	}

	public void setTargetList(ArrayList<WorldElement> targetObjectList) {
		targetlist = targetObjectList;
	}
	
	public VirtualWorldElement getCurrentTarget()
	{
		return targetFromList;
	}
}
