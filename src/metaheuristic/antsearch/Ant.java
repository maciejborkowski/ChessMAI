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
		List<MoveProbability> moves = colony.getPheromones().get(game.getBoard());
		int[] move;
		if (moves == null) {
			List<int[]> availableMoves = ChessEngine.availableMoves(game);
			moves = createProbabilityMoves(availableMoves);
			colony.getPheromones().put(game.getBoard(), moves);
			int idx = random.nextInt(availableMoves.size());
			move = availableMoves.get(idx);
		} else {
			move = chooseRandom(moves);
		}
		return move;
	}

	private List<MoveProbability> createProbabilityMoves(List<int[]> availableMoves) {
		List<MoveProbability> moves = new LinkedList<>();
		double probability = 1.0 / (double) availableMoves.size();
		for (int[] move : availableMoves) {
			moves.add(new MoveProbability(move, probability));
		}
		return moves;
	}

	private int[] chooseRandom(List<MoveProbability> moves) {
		double value = random.nextDouble();
		double cumulativeProbability = 0.0;
		for (MoveProbability move : moves) {
			cumulativeProbability += move.getProbability();
			if (value <= cumulativeProbability) {
				return move.getMove();
			}
		}
		return null;
	}

	public void setColony(AntColony antColony) {
		colony = antColony;
	}

}
