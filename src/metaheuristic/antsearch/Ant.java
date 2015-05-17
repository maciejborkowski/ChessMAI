package metaheuristic.antsearch;

import chess.player.Player;

public class Ant extends Player {
	private AntColony colony;
	private String moves;

	@Override
	public int[] think() throws Exception {
		return null;
	}

	public void setColony(AntColony antColony) {
		colony = antColony;
	}

}
