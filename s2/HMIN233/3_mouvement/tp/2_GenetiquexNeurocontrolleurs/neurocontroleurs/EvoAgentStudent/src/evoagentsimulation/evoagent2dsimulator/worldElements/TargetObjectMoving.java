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

import org.jbox2d.common.Vec2;

public class TargetObjectMoving extends TargetObject {

	public TargetObjectMoving(Vec2 worldPos, float worldAng, float s) {
		super(worldPos, worldAng, s);
		
	}
	
	public TargetObjectMoving(Vec2 worldPos, float worldAng, float s, String n) {
		super(worldPos, worldAng, s,n);
	}
	
	public void randomPush()
	{
		Vec2 f = new Vec2((float)(Math.random()*2)-1,(float)(Math.random()*2)-1);
		Vec2 p = new Vec2(this.worldPosition);
		this.body.applyForce(f,p);
	}
}
