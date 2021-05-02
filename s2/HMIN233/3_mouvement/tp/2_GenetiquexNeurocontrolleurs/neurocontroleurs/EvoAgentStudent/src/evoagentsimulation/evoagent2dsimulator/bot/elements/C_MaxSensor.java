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

import java.util.ArrayList;

public class C_MaxSensor extends SensorConditional {

	public C_MaxSensor(ArrayList<Sensor> se) {
		super(se);
	}
	
	@Override
	public double getValue()
	{
		double ret = 0;
		
		for(Sensor s : sensors)
			if(s.getNormalizedValue() > ret)
				ret = s.getNormalizedValue();
		return ret;		
	}	
}
