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

import java.util.ArrayList;

import evoagentapp.tasks.EATask;
import ui.EvoAgentAppFrame;

public class EvoAgentApp{
	static ArrayList<String> tasks = new ArrayList<>();
	static public EATask currentTask = null;
	static boolean startTask = false;

	private static void createAndShowGUI() {
		new EvoAgentAppFrame();
    }
	public static void stopTask() {
		if(currentTask!=null)
			currentTask.stopTask();
		currentTask = null;
	}

	public static void startTask() {
		startTask = true;
	}

	public static void setTask(EATask t) {
		currentTask = t;
	}
	
	public static EATask getTask() {
		return currentTask;
	}
	
	public static void main(String[] args) {
		createAndShowGUI();
		while(true)
		{
			if(startTask)
			{
				System.out.println("starting task");
				if(currentTask!=null)
					currentTask.runTask();
				startTask = false;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


