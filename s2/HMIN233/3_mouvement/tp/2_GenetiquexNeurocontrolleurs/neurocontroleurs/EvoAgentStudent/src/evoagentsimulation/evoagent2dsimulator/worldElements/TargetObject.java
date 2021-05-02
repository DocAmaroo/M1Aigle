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

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

import evoagentsimulation.evoagent2dsimulator.CollisionDefines;
import evoagentsimulation.evoagent2dsimulator.bot.BotBody2D;

public class TargetObject extends DynamicWorldElement {

	public TargetObject(Vec2 worldPos, float worldAng, float s) {
		super(worldPos, worldAng, s);
		sd.shape = new CircleShape(); 
		sd.friction = 0.0f;
		sd.restitution = 1.8f;
		sd.density = 2.0f;
		sd.filter.categoryBits = CollisionDefines.CDTargetObj;
		sd.shape.m_radius = size;
		bd.angularDamping = 1.5f;
		bd.linearDamping = 0.15f;
		bd.userData = this;
		
	}
	
	public TargetObject(Vec2 worldPos, float worldAng, float s, String n) {
		this(worldPos, worldAng, s);
		name = n;
	}

	public boolean isOwner(BotBody2D bot) {
		if(bot == this.owner)
			return true;
		return false;
	}

	public boolean hasOwner() {
		if(this.owner != null)
			return true;
		return false;
	}
	
	public void setFriction(float frict)
	{
		sd.friction = frict;
	}

	public void setDamping(float damp)
	{
		bd.linearDamping  = damp;
	}
}
