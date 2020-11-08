import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Individual {
	//group/list of cities to visit
	City[] route;
	int distance;
	double fitness;
	int index;
	
	public Individual(City[] route) {
		this.route = createRoute(route);
		this.distance = createRouteDistance();
		this.fitness = createRouteFitness();
		this.index = -1;
		
	}
	//copy constructor
	public Individual(Individual ind) {
		
		for(int i = 0; i < ind.getRoute().length; i++) {
			this.route[i] = new City(ind.getRoute()[i]);
		}
		
		this.distance = ind.getDistance();
		this.fitness = ind.getFitness();
		this.index = ind.getIndex();
	}
	
	public Individual(Individual ind, int index) {
	
		for(int i = 0; i < ind.getRoute().length; i++) {
			this.route[i] = new City(ind.getRoute()[i]);
		}
		
		this.distance = ind.getDistance();
		this.fitness = ind.getFitness();
		this.index = index;
	}
	


	public int getIndex() {
		return this.index;
	}

	public City[] getRoute() {
		return this.route;
	}

	public int getDistance() {
		return this.distance;
	}

	public double getFitness() {
		return this.fitness;
	}

	public City[] createRoute(City[] cities) {
		Random rand = new Random();
		City[] randomized = new City[cities.length];
		int[] indexes = new int[cities.length];
		for (int i = 0; i < cities.length; i++) {
			indexes[i] = i;
		}

		// shuffle indexes
		for (int i = 0; i < indexes.length; i++) {
			int randomIndexToSwap = rand.nextInt(indexes.length);
			int temp = indexes[randomIndexToSwap];
			indexes[randomIndexToSwap] = indexes[i];
			indexes[i] = temp;
		}

		for (int i = 0; i < cities.length; i++) {
			randomized[i] = cities[indexes[i]];
		}

		return randomized;

	}

	public int createRouteDistance() {
		int pathDistance;

		pathDistance = 0;

		for (int i = 0; i < route.length; i++) {
			City fromCity = this.route[i];
			City toCity = null;
			if (i + 1 < route.length) {
				toCity = route[i + 1];
			} else {
				toCity = route[0];
			}
			pathDistance += fromCity.distance(toCity);
		}
		distance = pathDistance;

		return distance;
	}

	public double createRouteFitness() {
		fitness = 1 / (float) getDistance();
		return fitness;
	}

	// ****************************//

	public static Individual mutate(Individual ind, double mutationRate) {
		int swap = (int) Math.random() * ind.getRoute().length;
		int swapWith = (int) Math.random() * ind.getRoute().length;
		
		if( Math.random()*100 < mutationRate) {
			City temp = ind.getRoute()[swap];
			ind.getRoute()[swap] = ind.getRoute()[swapWith];
			ind.getRoute()[swapWith] = temp;
		}
		
		return ind; 
		
		
	}

	public static Individual breed(Individual parent1, Individual parent2) {
		City[] child = new City[parent1.getRoute().length];
		
		//City[] childP1 = new City[parent1.getRoute().length];
		//City[] childP2 = new City[parent2.getRoute().length];
		
		int geneA = (int) Math.random() * parent1.getRoute().length;
		int geneB = (int) Math.random() * parent2.getRoute().length;
		
		int startGene = Math.min(geneA, geneB);
		int endGene = Math.max(geneA, geneB);
		
		for(int i = startGene; i < endGene; i++) {
			child[i] = parent1.getRoute()[i];		
		}
		
		List<City> list = Arrays.asList(child);
		
		int index = 0;
		for(int i = 0; i < parent1.getRoute().length; i++) {
			if(index >= parent1.getRoute().length) {
				break;
			}
			if(!list.contains(parent2.getRoute()[i])) {
				child[index] = parent2.getRoute()[i];
				index++;
			}
		}
		return new Individual(child);
	}

}
