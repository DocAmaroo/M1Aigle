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
		}

		public int compareTo(Individual compare) {
			// ...
		   return 0;
		}

		public void random(){
			// ...
		}

		public Individual crossbreed(Individual i2){
			// ...
			return null;
		}

		public void mutation(double probabilite)
		{
			// ...
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
		// ...
	}	

	public void breedNew(){
		// ...
	}
	
	public Individual getBest(){
		// ...
		return null;
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
