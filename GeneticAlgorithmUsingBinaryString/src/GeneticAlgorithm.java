import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.xml.crypto.KeySelector.Purpose;

public class GeneticAlgorithm {
	
	private int populationSize;
	private int crossSection;
	private double crossoverRate;
	private double mutationRate;
	private int numberOfIterations;
	

	public GeneticAlgorithm(int populationSize, int crossSection, double crossoverRate, double mutationRate,
			int numberOfIterations) {
		
		this.populationSize = populationSize;
		this.crossSection = crossSection;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.numberOfIterations = numberOfIterations;
	}



	public void startComputation(){
		
		//generate initial population

		ArrayList<Configuration> population = initialPopulationCreation();
		
		int countFitnessChange = 0;
		double current,previous = 0;
		int i = 1;
		while(i < numberOfIterations &&countFitnessChange < 2) {
			
			

			double totalFitness = 0.0;

			for (Configuration c : population) {

				totalFitness += c.getFitness();
			}

			double averageFitness = totalFitness / populationSize;
		

			for (Configuration c : population) {

				c.setProbability(averageFitness / c.getFitness());

			}

			population.sort(new Comparator<Configuration>() {

				@Override

				public int compare(Configuration o1, Configuration o2) {

					return Double.compare(o1.getProbability(), o2.getProbability());

				}
			});

			Collections.reverse(population);

			// Reproduction
			ArrayList<Configuration> newGeneration = new ArrayList<>(populationSize);
			int numOfIndividualsKept = (int) ((1 - crossoverRate) * populationSize);
			newGeneration.addAll(population.subList(0, numOfIndividualsKept));
			
			
			
			// Crossover
			int numOfIndividualsFromCrossover = (int) Math.round(crossoverRate * populationSize);

			int counter = 0;
			for (int c = 0; c <= numOfIndividualsFromCrossover; c++) {
				
				Random rand = new Random();
				 
				crossSection = rand.nextInt(10);

				int next = c + 1;
				Configuration parent1 = population.get(c);
				Configuration parent2 = population.get(next);
				Configuration child1 = new Configuration();

				String child1Binary = parent1.getChromosomes().substring(0, crossSection)
						+ parent2.getChromosomes().substring(crossSection, 10);
				child1.setConfiguration(new StringBuilder(child1Binary));
				child1.calculateFitness();

				Configuration child2 = new Configuration();
				String child2Binary = parent2.getChromosomes().substring(0, crossSection)
						+ parent1.getChromosomes().substring(crossSection, 10);
				child2.setConfiguration(new StringBuilder(child2Binary));
				child2.calculateFitness();
		
				newGeneration.add(child1);
				counter++;
				if (counter == numOfIndividualsFromCrossover) {
					break;
				}
				newGeneration.add(child2);
				counter++;
				if (counter == numOfIndividualsFromCrossover) {
					break;
				}

			}
			
			
	
			// mutation
			
			int numberOfIndividualsMutated = (int) Math.round(populationSize * mutationRate);
			ArrayList<Integer> alreadyMutatedIndividuals = new ArrayList<>();
			
			for(int m = 1; m <= numberOfIndividualsMutated ; m++){
				
				Random rand = new Random();
				
				int indexChosen = 0 ;
				boolean isNewIndividual = false;
				
				while(!isNewIndividual){
					
				indexChosen = rand.nextInt((populationSize -1 - 0) + 1) + 0;
				
				if(!alreadyMutatedIndividuals.contains(indexChosen)){
					isNewIndividual = true;
				}		
				}
				
				
				int chromosomemutated = rand.nextInt(10);
				int binaryValue = rand.nextInt((1 - 0) + 1) + 0;
				
			
				
				Configuration mutatedIndividual =  newGeneration.get(indexChosen);
				mutatedIndividual.SetChromosome(chromosomemutated, binaryValue);
				mutatedIndividual.calculateFitness();
				
				newGeneration.set(indexChosen, mutatedIndividual);
				
				alreadyMutatedIndividuals.add(indexChosen);
				
				
			}
			
			population = newGeneration;
			
			mutationRate = mutationRate / 2;
			
			//if the fitness does not change then exit loop
			if(i== 1){
				current = population.get(0).getFitness();
				previous = population.get(0).getFitness();
			}
			else{
				current = population.get(0).getFitness();
				
				if(current == previous){
					countFitnessChange++;
				}
				else{
					countFitnessChange =0;
				}
				previous = current;
			}
			
			i++;
		}
		
		Configuration bestSolution = population.get(0);
		System.out.println("Best Solution: Chormosomes(" + bestSolution.getChromosomes()+
				")   height and diameter "+ bestSolution.getIntHeight() +"::" + bestSolution.getIntDiameter() +"   Fitness:  " + 
				bestSolution.getFitness());
		
		
	}
	
   public ArrayList<Configuration> initialPopulationCreation(){
	   
	   ArrayList<Configuration> population = new ArrayList<>(populationSize);
	   for (int popCount= 0; popCount < populationSize; popCount++){
		   
		   Configuration configuration = new Configuration();
		   for(int i = 0; i < configuration.getChromosomesCount(); i++){
			   
			   Random rand = new Random();
			   int randomNum = rand.nextInt((1 - 0) + 1) + 0;
			   configuration.SetChromosome(i, randomNum);
		   }
		   population.add(configuration);
		
	   }  
	   
	   
	   return population;
	   
   }

}
