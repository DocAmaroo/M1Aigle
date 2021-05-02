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

public class TriggerZone extends VirtualWorldElement {
	public String name = "Zone";
	
	public TriggerZone(Vec2 worldPos, float worldAng,float s){
		super(worldPos, worldAng,s);
	}
	
	public TriggerZone(Vec2 worldPos, float worldAng,float s, String Label) {
		super(worldPos, worldAng,s);
		name = Label;
	}

	public boolean isPointInZone(Vec2 point) {
		//find the world coordinates of the zone
		Vec2 points[] = new Vec2[4];
		points[0]= new Vec2(-size,size);
		points[1]= new Vec2(size,size);
		points[2]= new Vec2(size,-size);
		points[3]= new Vec2(-size,-size);
		for(int i = 0 ; i < 4 ; i++)
			points[i].set(getWorldPoint(points[i]));
		//clockwise check of all edges of a convex polygon.
		//if the point is on the left of any edge, it is outside of the polygon
		for(int i = 0 ; i < 4 ;i++)
			if(!isVectorRight(new Vec2(points[(i+1)%4].x-points[i].x,points[(i+1)%4].y-points[i].y),new Vec2(point.x-points[i].x,point.y-points[i].y)))
				return false;
		return true;
	}
	
	public boolean isPointInZoneMargin(Vec2 point, float margin) {
		//find the world coordinates of the zone
		Vec2 points[] = new Vec2[4];
		points[0]= new Vec2(-size+margin,size-margin);
		points[1]= new Vec2(size-margin,size-margin);
		points[2]= new Vec2(size-margin,-size+margin);
		points[3]= new Vec2(-size+margin,-size+margin);
		for(int i = 0 ; i < 4 ; i++)
			points[i].set(getWorldPoint(points[i]));
		//clockwise check of all edges of a convex polygon.
		//if the point is on the left of any edge, it is outside of the polygon
		for(int i = 0 ; i < 4 ;i++)
			if(!isVectorRight(new Vec2(points[(i+1)%4].x-points[i].x,points[(i+1)%4].y-points[i].y),new Vec2(point.x-points[i].x,point.y-points[i].y)))
				return false;
		return true;
	}
	
	private boolean isVectorRight(Vec2 v1, Vec2 v2) {
		if(((v1.x) * (v2.y)) - ((v2.x) * (v1.y))>0.0)
			return false;
		else
			return true;
	}	
}
