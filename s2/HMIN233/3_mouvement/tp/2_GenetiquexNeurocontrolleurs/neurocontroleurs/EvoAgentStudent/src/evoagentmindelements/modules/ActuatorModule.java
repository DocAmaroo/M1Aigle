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

package evoagentmindelements.modules;

public class ActuatorModule {
	private String ID;
	private double motorValue = 0.5;
	public boolean inUse = false;

	public ActuatorModule(String idIn)
	{
		setID(idIn);
	}
	
	public double getMotorValue()
	{
		return motorValue;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}	
	
	public void reset()
	{
		motorValue = 0.5;
	}

	public void setMotorValue(double v) {
		motorValue = v;
	}
}
