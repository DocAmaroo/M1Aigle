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

package evoagentapp.tasks;

import evoagentmindelements.EvoAgentMind;

public class EATask {
	protected float throttleDelay = 4;
	protected boolean endTask = false;
	protected String environmentName = "";

	public EATask()
	{
	}
	
	public void runTask() {
		
		
	}

	public void setTrottleRender(float delay)
	{
		throttleDelay = delay;
	}

	public float getTrottleRender() {
		return throttleDelay;
	}
	
	public void manualReset()
	{
		
	}
	
	public void stopTask()
	{
		endTask = true;
	}
	
	public String getEnvironmentName()
	{
		return environmentName;
	}
	
	public EvoAgentMind getMind()
	{
		return null;
	}
}
