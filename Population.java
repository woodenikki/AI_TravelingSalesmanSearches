import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Population {
	// group of individuals

	public List<Individual> population;
	public List<Individual> sorted;
	
	// how many different routes do you want?
	public Population(int popSize, City[] cities) {

		this.population = new ArrayList<Individual>();
		this.sorted = new ArrayList<Individual>();

		for (int i = 0; i < popSize; i++) {
			population.add(new Individual(cities));
		}

	}
	
	public List<Individual> rankIndividuals() {

		//copy population into sorted
		for(int i = 0; i < population.size(); i++) {
			sorted.add(population.get(i));
		}

		Comparator<Individual> compareByFitness = (Individual o1, Individual o2) -> Double.compare(o1.getFitness(), o2.getFitness());
		Collections.sort(sorted, compareByFitness.reversed());

		return sorted;
	}
	
	public List<Individual> rankIndividuals(List<Individual> pop) {
		List<Individual> ranked = new ArrayList<Individual>();
		//copy population into sorted
		for(int i = 0; i < pop.size(); i++) {
			ranked.add(pop.get(i));
		}

		Comparator<Individual> compareByFitness = (Individual o1, Individual o2) -> Double.compare(o1.getFitness(), o2.getFitness());
		Collections.sort(ranked, compareByFitness.reversed());
		
		List<Individual> rankedWithIndex = new ArrayList<Individual>();
		
		for(int i = 0; i < ranked.size(); i++) {
			rankedWithIndex.add(new Individual(ranked.get(i), i));
		}
		


		return ranked;
	}
	
	
	//nextGeneration(pop, eliteSize, mutationRate);
	public List<Individual> nextGeneration(List<Individual> pop, int eliteSize, double mutationRate){
		
		List<Individual> popRanked = rankIndividuals(pop);
		List<Integer> selectionResults = selection(popRanked, eliteSize);		//returns list of indexes for population!!!
		
		return null;//TODO
	}
	
	public List<Integer> selection(List<Individual> popRanked, int eliteSize){
		HashMap<Integer, Double> df = new HashMap<Integer, Double>(); //popRanked index, cum_perc
		
		double sum = 0;
		double cumsum = 0;
		for(int i = 0; i < popRanked.size(); i++) {					//get sum of Fitnesses
			sum+= popRanked.get(i).getFitness();		
		}
		
		for(int i = 0; i < popRanked.size(); i++) {					//add (index, percent) to hashmap df
			cumsum += popRanked.get(i).getFitness();
			double cumperc = 100*cumsum / sum;
			df.put(popRanked.get(i).getIndex(), cumperc);
		}
		
		List<Integer> selectionResults = new ArrayList<Integer>();	
		
		for(int i = 0; i < eliteSize; i++) {
			selectionResults.add(popRanked.get(i).getIndex());		//add the best ones c:
		}
		
		for(int i = eliteSize; i < popRanked.size(); i++) {			//for the rest, grab random ones based on percentage...?
			int pick = (int)(100 * Math.random());
			for(int j = 0; j < popRanked.size(); j++) {
				if(pick <= df.get(j)) {
					selectionResults.add(popRanked.get(j).getIndex());
					break;
				}
			}
		}
		
		return selectionResults;
		
	}


}
