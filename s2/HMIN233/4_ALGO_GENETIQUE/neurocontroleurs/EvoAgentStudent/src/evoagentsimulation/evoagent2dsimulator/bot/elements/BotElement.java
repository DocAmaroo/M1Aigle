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

import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class BotElement {
	public Vec2 localPosition = null;
	public float localAngle;
	public Vec2 worldPosition = null;
	public float worldAngle;
	public BotBody2D bot;
	
	public BotElement(Vec2 lp , float la, BotBody2D b)
	{
		localPosition = lp;
		localAngle = la;
		bot = b;
	}
	
	public void computeWorldPosAndAngle()
	{
		worldAngle = bot.body.getAngle() + localAngle;
		worldPosition = bot.body.getWorldPoint(localPosition);
	}
	
	public Vec2 getWorldPosition()
	{
		return worldPosition;
	}

}
