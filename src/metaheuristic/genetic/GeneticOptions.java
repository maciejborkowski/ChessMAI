package metaheuristic.genetic;

import metaheuristic.MetaheuristicOptions;

public class GeneticOptions extends MetaheuristicOptions {
	private int population;
	private int goodGensPercent;
	
	public int getGoodGensPercent()
	{
		return goodGensPercent;
	}
	
	public void setGoodGensPercent(int goodGensPercent)
	{
		this.goodGensPercent = goodGensPercent;
	}
	
	public int getPopulation()
	{
		return population;
	}
	
	public void setPopulation(int population)
	{
		this.population = population;
	}
}
