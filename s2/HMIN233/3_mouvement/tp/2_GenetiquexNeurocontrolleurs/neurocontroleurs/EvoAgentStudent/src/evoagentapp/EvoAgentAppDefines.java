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

package evoagentapp;


public class EvoAgentAppDefines {
	
	public static final String classpath2DSingleExp = "evoagentsimulation.evoagent2dsimulator.experiments.";
	
	//Physics settings
	// calculation per sec (or step length): 60 = fine , 30 = cheaper
	public static float targetFPS2DPhysics = 30.0f;

	//Sensor Values
	public static double doubleBooleanFalse = 0.2;
	public static double doubleBooleanTrue = 0.8;
}
