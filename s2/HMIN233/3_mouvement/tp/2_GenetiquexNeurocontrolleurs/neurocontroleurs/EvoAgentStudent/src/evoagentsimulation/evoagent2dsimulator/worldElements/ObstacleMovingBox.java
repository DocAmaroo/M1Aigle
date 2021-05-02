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
import org.jbox2d.dynamics.World;

public class ObstacleMovingBox extends ObstacleStaticBox {
	float speed = 0.02f;
	
	public ObstacleMovingBox(Vec2 worldPos, float worldAng,float s, World w)
	{
		super(worldPos, worldAng,s,w);
		speed = 0.01f + ((float)Math.random() * 0.03f);
	}
	
	public void  step()
	{
		if(getWorldPosition().x < -70 || getWorldPosition().x > 70 ||getWorldPosition().y < -70 ||getWorldPosition().y > 70)
		{
			worldAngle += 120 + (Math.random() * 120); 
		}
			setPosition(new Vec2(getWorldPosition().x + (speed * (float)Math.cos(worldAngle)),getWorldPosition().y+(speed * (float)Math.sin(worldAngle))), worldAngle);
	}	
}
