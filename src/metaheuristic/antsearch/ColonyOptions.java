package metaheuristic.antsearch;

import metaheuristic.MetaheuristicOptions;

public class ColonyOptions extends MetaheuristicOptions {
	private double dissipation;

	public double getDissipation() {
		return dissipation;
	}

	public void setDissipation(double dissipation) {
		this.dissipation = dissipation;
	}
}
