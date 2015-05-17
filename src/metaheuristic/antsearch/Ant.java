package metaheuristic.antsearch;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import uci.MoveParser;
import chess.engine.ChessEngine;
import chess.player.Player;

public class Ant extends Player {
	private AntColony colony;
	private List<String> moves = new LinkedList<>();
	private Random random = new Random();

	@Override
	public int[] think() throws Exception {
		int[] move = chooseMove();

		moves.add(MoveParser.parse(move));
		return move;
	}

	private int[] chooseMove() {
		List<int[]> availableMoves = ChessEngine.availableMoves(game);
		int idx = random.nextInt(availableMoves.size());
		return availableMoves.get(idx);
	}

	public void setColony(AntColony antColony) {
		colony = antColony;
	}

}
