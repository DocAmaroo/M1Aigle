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

package evoagentsimulation.simulationlearning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {
    private int populationSize = 200;
    private int maxGeneration = 100;
    private int repetitions = 1;
    int genomeSize = 0;
    ArrayList<Individual> population;

    public class Individual implements Comparable<Individual> {
        double score = 0;
        double genome[];

        public Individual(){
            genome = new double[genomeSize];
            for(int i=0; i<genomeSize; i++)
                genome[i]= Math.random();

        }

        public Individual(double[] poid){
            genome = poid.clone();
        }

        public int compareTo(Individual compare) {
            double cout1 = this.getScore();
            double cout2 = compare.getScore();
            if( cout1 > cout2)
                return 1;
            if( cout1 < cout2)
                return -1;
            else return 0;
        }

        public void random(){
            for(int i = 0 ; i < genomeSize; i++)
                genome[i] = (Math.random() * 2.0) - 1.0;
        }

        public Individual crossbreed(Individual i2){

            int pivot = (int)(Math.random() * (genomeSize-1));
            double poid[]= new double[genomeSize];
            for(int i=0; i<pivot; i++)
                poid[i]= genome[i];
            for(int j=pivot; j<genomeSize; j++)
                poid[j]= genome[j];
            return new Individual(poid);
        }

        public void mutation(double probabilite) {
            int var1 =  (int)(Math.random() * genomeSize-1);
            int plusoumoins =  (int)(Math.random() * 1);
            if (plusoumoins ==0)
                genome[var1]+=0.2*probabilite;
            else
                genome[var1]-=0.2*probabilite;
        }

        public Individual mutation(Individual v1 ,double probabilite) {
            Individual newgen= new Individual(v1.getGenome());
            int var1 =  (int)(Math.random() * genomeSize-1);
            double plusoumoins =  (Math.random());
            if (plusoumoins >0.5)
                newgen.genome[var1]+=0.8*probabilite;
            else
                newgen.genome[var1]-=0.8*probabilite;
            return newgen;
        }




        public double[] getGenome() {
            return genome;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double val) {
            score = val;
        }
    }

    public GeneticAlgorithm(int genomeS){
        genomeSize=genomeS;
    }

    public void initialise(){
        population = new ArrayList<Individual>();
        for(int i=0; i<populationSize; i++) {
            population.add(new Individual());
        }

        // ...
    }

    public void breedNew(){

        Collections.sort(population); //tri décroissant
        Collections.reverse(population);
        int seuil = (int)populationSize/3;
        int reste = populationSize - seuil-1;

        while(reste >0) {
            Random rd = new Random();
            if(rd.nextBoolean()) {
                //croisement
                int val1 = (int)(Math.random() * seuil+1);
                int val2 = (int)(Math.random() * seuil+1);
                if(val1 == val2)
                    continue;

                Individual i1 =  population.get(val1);
                Individual i2 =  population.get(val2);


                Individual i= i1.crossbreed(i2);
                population.set(seuil+reste, i);
                reste--;
            }
            else {
                //mutation
                Individual i1 =  population.get((int)(Math.random() * seuil+1));
                Individual i= i1.mutation(i1, Math.random());
                population.set(seuil+reste, i);
                reste--;
            }
        }




        // ...
    }



    public Individual getBest(){
        int best = 0;
        Collections.sort(population); //tri décroissant
        Collections.reverse(population);
        for(int i=1; i<populationSize;i++)

            if (population.get(best).getScore() < population.get(i).getScore())
                best = i;

        // ...
        return population.get(best);
    }

    public ArrayList<Individual> getPopulation()
    {
        return population;
    }

    public int getMaxGeneration() {
        return maxGeneration;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setMaxGeneration(int maxGeneration) {
        this.maxGeneration = maxGeneration;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
}
