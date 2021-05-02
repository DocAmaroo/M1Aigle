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

import java.util.LinkedList;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class S_MovementSensor extends Sensor{
	LinkedList<Vec2> posList = new LinkedList<>();
	int listSize = 20;
	double value = 0;
	int tickCount = 0;
	int tickUpdate = 20;
	double sensitivity = 3.0;
	
	public S_MovementSensor(Vec2 lp, float la, BotBody2D b) {
		super(lp, la, b);
		
	}
	
	public S_MovementSensor(Vec2 lp, float la, BotBody2D b, int lsize) {
		super(lp, la, b);
		listSize = lsize;
	}
	
	public void updateList()
	{
		posList.add(this.worldPosition);
		if(posList.size()>listSize)
			posList.removeFirst();		
	}

	public double getValue() {
		if(tickCount == 0)
		{
			computeWorldPosAndAngle();
			updateList();
			double totalSum = 0;
			double elemSum = 0;
			for(Vec2 v : posList)
			{
				elemSum = 0;
				for(Vec2 to : posList)
				{
					elemSum+= MathUtils.distance(v, to);
					//System.out.println("lol" +MathUtils.distance(v, to));
				}			
				elemSum /= (double)posList.size();
				totalSum+=elemSum;
			}
			totalSum/=(double)posList.size();
			tickCount++;
			return totalSum;			
		}
		else
		{
			if(tickCount >= tickUpdate)
				tickCount = 0;
			else
				tickCount++;
			return value;
		}
	}
	
	@Override
	public double getNormalizedValue() {
		value = getValue();
		//System.out.println(Math.min(1.0,value/5));
		return Math.min(1.0,value/sensitivity);
	}
	
}
