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

import java.awt.Color;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public abstract class WorldElement extends VirtualWorldElement {
	
	public Body body;
	public World world;
	protected BodyDef bd;
	protected FixtureDef sd;
	protected Color col = Color.orange;
	
	public WorldElement(Vec2 worldPos, float worldAng, float s)
	{
		super(worldPos,  worldAng,  s);

	}

	@Override
	public Vec2 getWorldPoint(Vec2 vec2) {
		return body.getWorldPoint(vec2);
	}
	
	@Override
	public Vec2 getWorldPosition() {
		if(body != null)
			return body.getPosition();
		else
			return null;
	}
	
	public void setWorldPosition(float x, float y) {
		if(body != null)
		{
			body.setTransform(new Vec2(x,y), worldAngle);
		}
	}

	public Color getColor() {
		
		return col;
	}

	public Color setColor(Color c) {
		
		col = c;
		return col;
	}
	
	public void registerToWorld(World w) {
		world = w;
		body = world.createBody(bd);
		body.createFixture(sd);
	}
	
	public void removeFromWorld() {
		if (world != null)
			world.destroyBody(this.body);		
		world = null;
	}
	
	public void initPosition(Vec2 Pos,double Angle)
	{
		bd.position.set(Pos.x,Pos.y);
		bd.angle = (float)Angle;
	}
	
	public void setPosition(Vec2 botStartPos, float ang) {
		body.setTransform(botStartPos, ang);
	}
}
