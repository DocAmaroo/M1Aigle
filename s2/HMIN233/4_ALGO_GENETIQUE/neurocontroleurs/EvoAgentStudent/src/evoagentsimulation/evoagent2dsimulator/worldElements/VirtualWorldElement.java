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

package evoagentsimulation.evoagent2dsimulator.worldElements;

import org.jbox2d.common.Mat22;
import org.jbox2d.common.Vec2;

import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public abstract class VirtualWorldElement {
	public Vec2 worldPosition;
	public float worldAngle;
	public float size; 
	private Mat22 R= new Mat22();
	public boolean lock = false;
	public BotBody2D owner = null;
	public String name;

	public VirtualWorldElement(Vec2 worldPos, float worldAng, float s) {
		worldPosition = worldPos;
		worldAngle = worldAng; 
		size = s;
		R.set(worldAng);
	}

	public Vec2 getWorldPoint(Vec2 v) {
		Vec2 out = R.mul(v);
		out.addLocal(worldPosition);
		return out;
	}

	public Vec2 getWorldPosition() {
			return worldPosition;
	}
	
	public void setAngle(float a)
	{
		worldAngle = a; 
		R.set(worldAngle);
	}
	
	public void setWorldPosition(float x, float y) {
		worldPosition.x = x;
		worldPosition.y = y;
	}
	
	public String getName()
	{
		return name;
	}
}
