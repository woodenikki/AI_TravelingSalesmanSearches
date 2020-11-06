import java.util.Collections;
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
	
	public Individual(Individual i, int index) {
		this.route = i.getRoute();
		this.distance = i.getDistance();
		this.fitness = i.getFitness();
		
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

	public void mutate() {

		
		
	}

	public void breed(Individual parent1, Individual parent2) {
		City[] childRoute = new City[parent1.getRoute().length];
		
		City[] p1Route = parent1.getRoute();
		City[] p2Route = parent2.getRoute();
		
		int geneA = (int) Math.random() * p1Route.length;
		int geneB = (int) Math.random() * p2Route.length;
		
		int startGene = Math.min(geneA, geneB);
		int endGene = Math.max(geneA, geneB);
		
		int range = Math.abs(endGene - startGene);
		
		for(int i = 0; i < range; i++) {
			
		}
	}

}
