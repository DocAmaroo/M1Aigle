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

package evoagentsimulation.evoagent2dsimulator;

public class CollisionDefines {

	public static final int CDEnv = 0x0001;
	public static final int CDBot = 0x0002;
	public static final int CDTargetObj = 0x0004;
	public static final int CDProjectile = 0x0008;
	public static final int CDAllMask = CDBot|CDEnv|CDTargetObj|CDProjectile;
	public static final int CDAllButEnvMask = CDBot|CDTargetObj|CDProjectile;
	
}
