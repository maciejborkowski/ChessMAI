package metaheuristic.antsearch;

import metaheuristic.MetaheuristicOptions;

public class ColonyOptions extends MetaheuristicOptions {
	private double dissipation;
	private int concurrentAnts;

	public double getDissipation() {
		return dissipation;
	}

	public void setDissipation(double dissipation) {
		this.dissipation = dissipation;
	}

	public int getConcurrentAnts() {
		return concurrentAnts;
	}

	public void setConcurrentAnts(int concurrentAnts) {
		this.concurrentAnts = concurrentAnts;
	}
}
