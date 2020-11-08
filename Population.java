import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Population {
	// group of individuals

	public List<Individual> population;
	// public List<Individual> sorted;

	// how many different routes do you want?
	public Population(int popSize, City[] cities) {

		this.population = new ArrayList<Individual>();
		// this.sorted = new ArrayList<Individual>();

		for (int i = 0; i < popSize; i++) {
			population.add(new Individual(cities));
		}

	}

	public Population(List<Individual> p) {
		for(int i = 0; i < p.size(); i++) {
			this.population.add(new Individual(p.get(i)));
		}
	}

	public int getLength() {
		return population.size();
	}

	public List<Individual> getPop() { // not sure if should return population or sorted?
		return population;
	}

	// nextGeneration(pop, eliteSize, mutationRate);
	public static Population nextGeneration(Population currentGen, int eliteSize, double mutationRate) {

		Population popRanked = rankIndividuals(currentGen);
		List<Integer> selectionResults = selection(popRanked, eliteSize); // returns list of indexes for population!!!
		Population matingpool = matingPool(currentGen, selectionResults);
		Population children = breedPopulation(matingpool, eliteSize);
		Population nextGeneration = mutatePopulation(children, mutationRate);

		return nextGeneration;
	}

	// ****************** METHODS USED IN NEXTGENERATION() ***********************//
	 public Population rankIndividuals() { 
		 List<Individual> sorted = new ArrayList<Individual>(); 
		 //copy population into sorted 
		 for(int i = 0; i < population.size(); i++) {
			 sorted.add(population.get(i)); 
			 }
	 
		 Comparator<Individual> compareByFitness = (Individual o1, Individual o2) ->
		 Double.compare(o1.getFitness(), o2.getFitness()); Collections.sort(sorted, compareByFitness.reversed());
	 
		 return new Population(sorted); 
	 }
	
	public static Population rankIndividuals(Population pop) {
		List<Individual> ranked = new ArrayList<Individual>();
		// copy population into sorted
		for (int i = 0; i < pop.population.size(); i++) {
			ranked.add(pop.population.get(i));
		}

		Comparator<Individual> compareByFitness = (Individual o1, Individual o2) -> Double.compare(o1.getFitness(),
				o2.getFitness());
		Collections.sort(ranked, compareByFitness.reversed());

		List<Individual> rankedWithIndex = new ArrayList<Individual>();

		for (int i = 0; i < ranked.size(); i++) {
			rankedWithIndex.add(new Individual(ranked.get(i), i));
		}

		return new Population(ranked);
	}

	public static List<Integer> selection(Population popRanked, int eliteSize) {
		HashMap<Integer, Double> df = new HashMap<Integer, Double>(); // popRanked index, cum_perc

		double sum = 0;
		double cumsum = 0;
		for (int i = 0; i < popRanked.population.size(); i++) { // get sum of Fitnesses
			sum += popRanked.population.get(i).getFitness();
		}

		for (int i = 0; i < popRanked.population.size(); i++) { // add (index, percent) to hashmap df
			cumsum += popRanked.population.get(i).getFitness();
			double cumperc = 100 * cumsum / sum;
			df.put(popRanked.population.get(i).getIndex(), cumperc);
		}

		List<Integer> selectionResults = new ArrayList<Integer>();

		for (int i = 0; i < eliteSize; i++) {
			selectionResults.add(popRanked.population.get(i).getIndex()); // add the best ones c:
		}

		for (int i = eliteSize; i < popRanked.population.size(); i++) { // for the rest, grab random ones based on
																		// percentage...?
			int pick = (int) (100 * Math.random());
			for (int j = 0; j < popRanked.population.size(); j++) {
				if (pick <= df.get(j)) {
					selectionResults.add(popRanked.population.get(j).getIndex());
					break;
				}
			}
		}

		return selectionResults;

	}

	public static Population matingPool(Population generation, List<Integer> selectionResults) {
		List<Individual> matingpool = new ArrayList<Individual>();
		int index;
		for (int i = 0; i < selectionResults.size(); i++) {
			index = selectionResults.get(i);
			matingpool.add(generation.population.get(index));
		}

		return new Population(matingpool);
	}

	public static Population breedPopulation(Population matingpool, int eliteSize) {
		List<Individual> children = new ArrayList<Individual>();
		int length = matingpool.population.size() - eliteSize;
		List<Individual> pool = new ArrayList<Individual>();
		int rand;

		// random sampling without replacement
		for (int i = 0; i < matingpool.population.size(); i++) {
			rand = (int) Math.random() * matingpool.population.size();
			pool.add(matingpool.population.get(rand));
		}

		for (int i = 0; i < eliteSize; i++) {
			children.add(matingpool.population.get(i));
		}

		for (int i = 0; i < length; i++) {
			Individual child = Individual.breed(pool.get(i), pool.get((matingpool.population.size() - i - 1)));
			children.add(child);
		}

		return new Population(children);

	}

	public static Population mutatePopulation(Population population, double mutationRate) {
		List<Individual> mutatedPop = new ArrayList<Individual>();

		for (int i = 0; i < population.population.size(); i++) {
			Individual mutatedInd = Individual.mutate(population.population.get(i), mutationRate);
			mutatedPop.add(mutatedInd);
		}

		return new Population(mutatedPop);
	}
	
	
	

	

}
