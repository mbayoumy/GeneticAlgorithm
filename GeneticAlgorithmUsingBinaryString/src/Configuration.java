import java.time.chrono.MinguoDate;
import java.util.ArrayList;

public class Configuration {

	private int chromosomesCount;
	private StringBuilder chromosomes;
	private double probability;
	private double cost = 0.0;


	public Configuration() {

		chromosomesCount = 10;
		String initialValue = "0000000000";
		chromosomes = new StringBuilder(initialValue);
		chromosomes.setLength(chromosomesCount);
		calculateFitness();

	}

	public String getChromosomes() {
		return chromosomes.toString();

	}
	public void setConfiguration(StringBuilder s){
		chromosomes = s;
	}

	public void SetChromosome(int index, int value) {

		chromosomes.setCharAt(index, Integer.toString(value).charAt(0));
	}

	public String getHeight() {
		return chromosomes.substring(0, 5);

	}

	public String getDiameter() {
		return chromosomes.substring(5, 10);
	}

	public void calculateFitness() {
		
		cost =0;

		int height = convertToInt(getHeight());
		int diameter = convertToInt(getDiameter());
		
		double constraint1 = (Math.PI * Math.pow(diameter, 2) * height) / 4;
	
		if (constraint1 < 300) {
			cost += 100;
		}

		double minimizeThis = 0.0654 *(((Math.PI * Math.pow(diameter, 2)) / 2) + Math.PI * height * diameter);
		cost += minimizeThis;
		
		

	}
	
	public double getFitness(){
		return cost;
	}
	public double fitnessSin(){
		return Math.sin(cost);
	}

	public int convertToInt(String binary) {

		int result = 0;

		for (int i = binary.length() - 1; i >= 0; i--) {

			if (binary.charAt(i) == '1') {
				result += Math.pow(2, binary.length() - i - 1);
			}

		}

		return result;

	}
	
	public int getIntHeight(){
		return convertToInt(getHeight());
	}
	
	public int getIntDiameter(){
		return convertToInt(getDiameter());
	}
	

	public int getChromosomesCount() {
		return chromosomesCount;
	}
	

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public static void main(String[] args) {
		
		
		GeneticAlgorithm test = new GeneticAlgorithm(100, 4, 0.75, 0.5, 1000);
		ArrayList<Configuration> cList = test.initialPopulationCreation();
		
		test.startComputation();
	}

}
