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
package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Paths;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import Utils.StreamToJText;
import evoagentapp.EvoAgentApp;
import evoagentapp.tasks.EATaskDemoSim;
import evoagentapp.tasks.EATaskLearningSim;
import evoagentmindelements.EvoAgentMind;

public class EvoAgentAppFrame {
	static JButton LEARN = new JButton("Learn");
	static JButton TEST = new JButton("Test");
	static JButton SAVE = new JButton("Save");
	static JButton DEMO = new JButton("Demo");
	static JButton STOP = new JButton("Stop");
	static JLabel PROCSTITLE = new JLabel("Ressources");
	public static JSlider PROCS = new JSlider();
	static JPanel menuPanel;
	static JFrame frame = null;

	public EvoAgentAppFrame()
	{
	       	frame = new JFrame("EvoAgent-students");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setPreferredSize(new Dimension(1024,600));
			frame.setLayout(new BorderLayout());
			menuPanel = new JPanel();
			PROCS.setName("Ressources");
			PROCS.setModel(new DefaultBoundedRangeModel(Runtime.getRuntime().availableProcessors(), 0, 1, Runtime.getRuntime().availableProcessors()));
			menuPanel.add(PROCSTITLE);			
			menuPanel.add(PROCS);
			LEARN.addActionListener(new ActionListener() {@Override
				public void actionPerformed(ActionEvent arg0) {
				if(!checkRunningTask())
				{
					System.out.println("Choose learning environment");
					Object[] possibilities = {"EXP_GTO", "EXP_Avoid", "EXP_GTOA"};
					String environmentClass = (String)JOptionPane.showInputDialog(
					                    frame,
					                    "Choose environment class",
					                    "environment",
					                    JOptionPane.PLAIN_MESSAGE,
					                    null, possibilities,
					                    "EXP_GTO");
					System.out.println(environmentClass + " selected");
				    EvoAgentApp.setTask(new EATaskLearningSim(environmentClass));
					EvoAgentApp.startTask();					
				}
				else
				{System.out.println("task already running, press stop first");}
			}});
			menuPanel.add(LEARN);
			TEST.addActionListener(new ActionListener() {@Override
				public void actionPerformed(ActionEvent arg0) {
				if(checkRunningTask() && EvoAgentApp.getTask() instanceof EATaskLearningSim)
				{
					if(EvoAgentApp.getTask().getMind() != null)
					{
						EATaskDemoSim task = new EATaskDemoSim(EvoAgentApp.getTask().getEnvironmentName());
					    EvoAgentMind m = EvoAgentApp.getTask().getMind();
					    task.setMind(m);
					    Thread thread = new Thread(){
					        public void run(){
							    task.runTask();
					        }
					      };
					      thread.setPriority(Thread.MAX_PRIORITY);
					      thread.start();						
					}else{System.out.println("wait for the first generation at least...");}
				}
				else
				{
					System.out.println("can only test if a learning task is running. use demo instead ...");
				}

				}});
			menuPanel.add(TEST);
			SAVE.addActionListener(new ActionListener() {@Override
				public void actionPerformed(ActionEvent arg0) {
					if(checkRunningTask() && EvoAgentApp.getTask() instanceof EATaskLearningSim)
					{

						if(EvoAgentApp.getTask().getMind() != null)
						{
							EvoAgentMind m = EvoAgentApp.getTask().getMind();
							JFileChooser chooser = new JFileChooser(Paths.get("").toAbsolutePath().toString());
							chooser.setDialogTitle("save controller file");
							int returnVal = chooser.showSaveDialog(frame);
							if(returnVal == JFileChooser.APPROVE_OPTION) {
								System.out.println("Saving best controler to: " +
							            chooser.getSelectedFile().getName());
								File file = chooser.getSelectedFile();
								m.toFile(file);
						}
						}else{System.out.println("wait for the first generation at least...");}
					}
					else
					{
						System.out.println("can only save if a learning task is running");
					}
				}});
			menuPanel.add(SAVE);
			DEMO.addActionListener(new ActionListener() {@Override
				public void actionPerformed(ActionEvent arg0) {
				if(!checkRunningTask())
				{
					System.out.println("Choose demo environment");
					Object[] possibilities = {"EXP_GTO", "EXP_Avoid", "EXP_GTOA"};
					String environmentClass = (String)JOptionPane.showInputDialog(
					                    frame,
					                    "Choose environment class",
					                    "environment",
					                    JOptionPane.PLAIN_MESSAGE,
					                    null, possibilities,
					                    "EXP_GTO");
					System.out.println(environmentClass + " selected");
					System.out.println("Choose controller file");				
					JFileChooser chooser = new JFileChooser(Paths.get("").toAbsolutePath().toString());
					chooser.setDialogTitle("Choose controller file");
					int returnVal = chooser.showOpenDialog(frame);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       System.out.println("You chose this controler: " +
				            chooser.getSelectedFile().getName());
					    EATaskDemoSim task = new EATaskDemoSim(environmentClass);
					    EvoAgentMind m = new EvoAgentMind();
						m.fromFile(chooser.getSelectedFile());
					    task.setMind(m);
					    EvoAgentApp.setTask(task);
						EvoAgentApp.startTask();
				    }
				}
				else
				{System.out.println("task already running, press stop first");}
				}});
			menuPanel.add(DEMO);
			STOP.addActionListener(new ActionListener() {@Override
				public void actionPerformed(ActionEvent arg0) {
					EvoAgentApp.stopTask();
					EvoAgentApp.setTask(null);
					System.out.println("Stop");
				}});
			menuPanel.add(STOP);
			frame.getContentPane().add(menuPanel, BorderLayout.NORTH);	
	        JTextArea textArea = new JTextArea();
	        JScrollPane scrollPane = new JScrollPane(textArea);
	        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	        PrintStream printStream = new PrintStream(new StreamToJText(textArea));
	        System.setOut(printStream);
	        System.setErr(printStream);
	        frame.pack();
	        frame.setVisible(true);
	}

	private boolean checkRunningTask(){		
		return EvoAgentApp.getTask()!=null;
	}
}
