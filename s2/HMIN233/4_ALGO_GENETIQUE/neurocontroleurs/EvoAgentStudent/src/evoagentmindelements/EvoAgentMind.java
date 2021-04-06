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


package evoagentmindelements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import evoagentmindelements.modules.ActuatorModule;
import evoagentmindelements.modules.SensorModule;

public class EvoAgentMind {
    private ArrayList<ActuatorModule> actuatorModules = new ArrayList<>();
    private ArrayList<SensorModule> sensorModules = new ArrayList<>();
    private int hiddenLayerCount = 0;
    private int hiddenLayerSize = 0;
    double weights[];

    public EvoAgentMind()
    {

    }

    public int genomeSize() {
        return (sensorModules.size() * hiddenLayerSize) + ((hiddenLayerCount -1) * (hiddenLayerSize*hiddenLayerSize)) + (hiddenLayerSize * actuatorModules.size());

    }

    public void doStep() {
        //int biais=1;

        int etape =0;

        double[][] valNeurone = new double[hiddenLayerCount][hiddenLayerSize];

        for( int i=0; i <= hiddenLayerCount; i++) {


            for (int j =0; j<hiddenLayerSize; j++) {
                //cas de base
                if(i ==0) {
                    double aggregat=0;
                    for (int k =0; k<sensorModules.size(); k++) {
                        aggregat += weights[k*hiddenLayerSize+j] * sensorModules.get(k).getValue();
                    }
                    valNeurone[i][j]=applyTransfer(aggregat);

                }
                //cas final
                else { if(i==hiddenLayerCount) {
                    if(j<actuatorModules.size()) {
                        double aggregat=0;
                        for (int k =0; k<hiddenLayerSize; k++) {
                            aggregat += weights[etape + k*actuatorModules.size() + j] * valNeurone[i-1][k];
                        }
                        actuatorModules.get(j).setMotorValue(normalize(applyTransfer(aggregat)));
                    }
                }

                //cas generale

                else {
                    double aggregat=0;
                    for (int k =0; k<hiddenLayerSize; k++) {
                        aggregat += weights[etape + k*hiddenLayerSize + j] * valNeurone[i-1][k];
                    }
                    valNeurone[i][j]=applyTransfer(aggregat);

                }
                }

            }

            if(i ==0)
                etape =sensorModules.size()*hiddenLayerSize-1;
            else
                etape = etape + hiddenLayerSize*hiddenLayerSize-1;
        }

        // ...
        //sensorModules.get(0).getValue();
        //actuatorModules.get(0).setMotorValue(0.5);
    }

    public double normalize(double v)
    {
        return (v+1.0)/2.0;

    }

    public double applyTransfer(double v)
    {

        return Math.tanh(v);
    }

    public void setHiddenLayerCount(int n) {
        hiddenLayerCount = n;
    }

    public void setHiddenLayerSize(int n) {
        hiddenLayerSize = n;
    }

    public ArrayList<ActuatorModule> getActuatorModules()
    {
        return actuatorModules;
    }

    public ArrayList<SensorModule> getSensorModules()
    {
        return sensorModules;
    }

    public void addActuator(String actuatorID)
    {
        actuatorModules.add(new ActuatorModule(actuatorID));
    }

    public void addSensor(String sensorID)
    {
        sensorModules.add(new SensorModule(sensorID));
    }

    public void setWeights(double w[])
    {
        weights = w;
    }

    public void toFile(File f)
    {
        if(f.exists())
            f.delete();
        try {
            f.createNewFile();
            PrintWriter pw = new PrintWriter(new FileWriter(f),true);
            pw.println(hiddenLayerCount + " "
                    + hiddenLayerSize + " "
                    + sensorModules.size() + " "
                    + actuatorModules.size() + " "
                    + genomeSize());
            for(int i = 0 ; i < sensorModules.size();i++)
                pw.println(sensorModules.get(i).getID());
            for(int i = 0 ; i < actuatorModules.size();i++)
                pw.println(actuatorModules.get(i).getID());
            for(int i = 0 ; i < weights.length;i++)
                pw.println(weights[i]);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fromFile(File f)
    {
        if(f.exists())
        {
            InputStream ips = null;
            try {
                ips = new FileInputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("file not found : " + f);
                return ;
            }
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            try {
                String line = br.readLine();
                hiddenLayerCount = Integer.parseInt(line.split(" ")[0]);
                hiddenLayerSize = Integer.parseInt(line.split(" ")[1]);
                int sensorCount = Integer.parseInt(line.split(" ")[2]);
                int actuatorCount = Integer.parseInt(line.split(" ")[3]);
                int weightCount = Integer.parseInt(line.split(" ")[4]);
                weights = new double[weightCount];
                for(int i = 0 ; i < sensorCount; i++)
                    addSensor(br.readLine());
                for(int i = 0 ; i < actuatorCount; i++)
                    addActuator(br.readLine());
                for(int i = 0 ; i < weightCount; i++)
                    weights[i] = Double.parseDouble(br.readLine());
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Mind test");
        EvoAgentMind mind = new EvoAgentMind();
        mind.addActuator("A1");
        mind.addActuator("A2");
        mind.addSensor("S1");
        mind.addSensor("S2");
        mind.setHiddenLayerCount(2);
        mind.setHiddenLayerSize(4);

        double[] weights = new double[mind.genomeSize()];

        for(int i = 0 ; i < mind.genomeSize(); i++)
            weights[i] = (Math.random() * 2.0) - 1.0;
        mind.setWeights(weights);

        for(int i = 0 ; i < 10 ; i++)
        {
            System.out.println("test " + i);
            for(SensorModule s : mind.getSensorModules())
                s.setValue(i/10.0);
            mind.doStep();
            for(ActuatorModule a: mind.getActuatorModules())
                System.out.println(a.getMotorValue());
        }
    }
}