import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Search {
	
	public static int SAP;		//Simulated Annealing Percentage
	public static int temperature = 25;
	
	
	

	public static ArrayList<City[]> search(City[] cities) {
		ArrayList<City[]> states = new ArrayList<City[]>();
		
		System.out.println("___________________________________");
		SAP = 100;
		for (int i = 0; i < 10; i++) {
			// Create a copy
			//City[] sorted = hillClimb(cities);
			//City[] sorted = simAnneal(cities, SAP);
			City[] sorted = genetic(cities);
			
			
			// shuffle and add it to the array
			
			List<City> result = Arrays.asList(sorted);
			//Collections.shuffle(result);
			states.add(result.toArray(sorted));
		}

		return states;
	}
	
	public static City[] simAnneal(City[] cities, int sap) {
		int currentCost = calculateCost(cities);
		System.out.println("current cost = "+ currentCost);
		
		City temp;
		int tempCost;
			for(int i = 0; i < cities.length-1; i++) {
				temp = new City(cities[i]);
				cities[i] = new City(cities[i+1]);
				cities[i+1] = new City(temp);
				
				if(sap >= 0) {													//temp is too low
					if((tempCost = calculateCost(cities)) > currentCost 		//cost is worse
							&& (Math.random()*100) <= sap ) {					//and temp is met
						currentCost = tempCost;
						SAP -= temperature;							//decrement temp
						System.out.println("-------Jump!-------\t\tTemp = "+SAP);
						return simAnneal(cities, SAP);
					}
				}
				
				if((tempCost = calculateCost(cities)) < currentCost) {
					currentCost = tempCost;
					return hillClimb(cities);
				} else {
					//switch them back
					temp = new City(cities[i]);
					cities[i] = new City(cities[i+1]);
					cities[i+1] = new City(temp);
				}
			}
		temp = new City(cities[0]);
		cities[0] = new City(cities[cities.length-1]);
		cities[cities.length-1] = new City(temp);
			
		int rand = (int) Math.random()*100;
		if(sap <= 0) {													//temp is too low
			if((tempCost = calculateCost(cities)) > currentCost 		//cost is worse
					&& (rand <= sap )) {					//and temp is met
				currentCost = tempCost;
				int newsap = sap - temperature;							//decrement temp
				System.out.println("Temp passed at "+rand);
				return simAnneal(cities, newsap);
			}
		}
		
		if((tempCost = calculateCost(cities)) < currentCost) {
			currentCost = tempCost;
			return hillClimb(cities);
		} else {
			//switch them back
			temp = new City(cities[0]);
			cities[0] = new City(cities[cities.length-1]);
			cities[cities.length-1] = new City(temp);
		}
		
		
		return cities;
	}
	
	public static City[] hillClimb(City[] cities){
		int currentCost = calculateCost(cities);
		System.out.println("current cost = "+ currentCost);
		
		City temp;
		int tempCost;
			for(int i = 0; i < cities.length-1; i++) {
				temp = new City(cities[i]);
				cities[i] = new City(cities[i+1]);
				cities[i+1] = new City(temp);
				
				if((tempCost = calculateCost(cities)) < currentCost) {
					currentCost = tempCost;
					return hillClimb(cities);
				} else {
					//switch them back
					temp = new City(cities[i]);
					cities[i] = new City(cities[i+1]);
					cities[i+1] = new City(temp);
				}
			}
		temp = new City(cities[0]);
		cities[0] = new City(cities[cities.length-1]);
		cities[cities.length-1] = new City(temp);
			
		if((tempCost = calculateCost(cities)) < currentCost) {
			currentCost = tempCost;
			return hillClimb(cities);
		} else {
			//switch them back
			temp = new City(cities[0]);
			cities[0] = new City(cities[cities.length-1]);
			cities[cities.length-1] = new City(temp);
		}
		
	return cities;

	}

	public static City[] genetic(City[] cities) {
		int populationSize 	= 100;
		int eliteSize 		= 20;
		double mutationRate = 0.01;
		int generations 	= 500;
		
		Population pop = new Population( populationSize, cities);
		pop.rankIndividuals();
		
		for(int i = 0; i < generations; i++) {
			pop = nextGeneration(pop, eliteSize, mutationRate);
		}
		
		return null;
	}
	
	
	
	public static int calculateCost(City[] cities) {
		int cost = 0;
		for(int i = 0; i < cities.length-1; i++) {
			cost += dist(cities[i], cities[i+1]);
		}
		cost += dist(cities[cities.length-1], cities[0]);
		
		return cost;
	}

	public static double dist(City a, City b) {
		return Math.sqrt(Math.pow((b.getX()-a.getX()), 2) + Math.pow((b.getY()-a.getY()), 2));
	}

}
