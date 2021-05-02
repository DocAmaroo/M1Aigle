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


import org.jbox2d.common.Vec2;

import evoagentapp.EvoAgentAppDefines;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;
import evoagentsimulation.evoagent2dsimulator.worldElements.VirtualWorldElement;

public class S_ZonePresence extends Sensor{
	public VirtualWorldElement target = null;
	private double value;
	
	public S_ZonePresence(Vec2 lp, float la, BotBody2D b, VirtualWorldElement targetin) {
		super(lp, la, b);
		target = targetin;
	}

	public double getValue() {
		if(target != null)
		{
			computeWorldPosAndAngle();
			Vec2 points[] = new Vec2[4];
			points[0]= new Vec2(-target.size,target.size);
			points[1]= new Vec2(target.size,target.size);
			points[2]= new Vec2(target.size,-target.size);
			points[3]= new Vec2(-target.size,-target.size);
			for(int i = 0 ; i < 4 ; i++)
				points[i].set(target.getWorldPoint(points[i]));
			//double ret = 1.0;
			for(int i = 0 ; i < 4 ;i++)
				if(isVectorRight(new Vec2(points[(i+1)%4].x-points[i].x,points[(i+1)%4].y-points[i].y),new Vec2(worldPosition.x-points[i].x,worldPosition.y-points[i].y)))
					return EvoAgentAppDefines.doubleBooleanFalse;
			return EvoAgentAppDefines.doubleBooleanTrue;
		}
		else
			return EvoAgentAppDefines.doubleBooleanFalse;
	}
	
	private boolean isVectorRight(Vec2 v1, Vec2 v2) {
		//System.out.println(((v1.x) * (v2.y)) - ((v2.x) * (v1.y)));
		if(((v1.x) * (v2.y)) - ((v2.x) * (v1.y))>0.0)
			return true;
		else
			return false;
	}

	@Override
	public double getNormalizedValue() {
		value = getValue();
		return value;
	}

	public void setTarget(VirtualWorldElement t) {
		target = t;
	}
	
}
