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
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import evoagentsimulation.evoagent2dsimulator.CollisionDefines;

public class DynamicWorldElement extends WorldElement{

	public DynamicWorldElement(Vec2 worldPos, float worldAng,float s)
	{
		super(worldPos, worldAng,s);
		sd = new FixtureDef();
		sd.filter.categoryBits = CollisionDefines.CDEnv;
		sd.filter.maskBits = CollisionDefines.CDAllMask;
		
		bd = new BodyDef();
		bd.position.set(worldPosition.x,worldPosition.y);
		bd.angle = worldAngle;
		bd.type = BodyType.DYNAMIC;
	}
}
