import java.util.Collections;
import java.util.Random;

public class Fitness {
	//for genetic algorithm

	City[] route;
	int distance;
	double fitness;
	
	public Fitness(City[] route) {
		this.route = route;
		this.distance = 0;
		this.fitness = 0.0;
	}
	
	public int routeDistance() {
		int pathDistance;
		if(distance == 0) {
			pathDistance = 0;
			for(int i = 0; i < route.length; i++) {
				City fromCity = this.route[i];
				City toCity = null;
				if(i+1 < route.length) {
					toCity = route[i+1];
				}else {
					toCity = route[0];
				}
				pathDistance += fromCity.distance(toCity);
			}
			distance = pathDistance;
		}
		return distance;
	}
	
	public double routeFitness() {
		if(fitness == 0) {
			fitness = 1 / (float) routeDistance();
		}
		return fitness;
	}
	
	public City[] createRoute(City[] cities) {
		Random rand = new Random();
		City[] randomized = new City[cities.length];
		int[] indexes = new int[cities.length];
		for(int i = 0; i < cities.length; i++) {
			indexes[i] = i;
		}
		
		//shuffle indexes
		for (int i = 0; i < indexes.length; i++) {
			int randomIndexToSwap = rand.nextInt(indexes.length);
			int temp = indexes[randomIndexToSwap];
			indexes[randomIndexToSwap] = indexes[i];
			indexes[i] = temp;
		}
		
		for(int i = 0; i < cities.length; i++) {
			randomized[i] = cities[indexes[i]];
		}
		
		return randomized;

	}
	
	
}
