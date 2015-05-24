package chess.player;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import metaheuristic.antsearch.MoveProbability;
import chess.engine.ChessEngine;

public class AntPlayer extends MetaheuristicPlayer {
	private Map<String, List<MoveProbability>> pheromones;
	private List<String> boardsStrings = new ArrayList<>();
	private List<int[]> moves = new ArrayList<>();
	private Random random = new Random();

	public AntPlayer(File solutionFile) throws IOException {
		if (solutionFile != null) {
			loadSolution(solutionFile);
		}
	}

	@Override
	public int[] think() throws Exception {
		if (mode == Mode.GREEDY) {
			return greedyMove();
		} else {
			return adventurousMove();
		}
	}

	private int[] greedyMove() {
		String boardString = game.getBoard().toString();
		boardsStrings.add(boardString);
		List<MoveProbability> probabilityMoves = pheromones.get(boardString);
		int[] move = null;

		if (probabilityMoves == null) {
			List<int[]> availableMoves = ChessEngine.availableMoves(game);
			if (availableMoves.size() > 0) {
				int idx = random.nextInt(availableMoves.size());
				move = availableMoves.get(idx);
			}
		} else {
			move = chooseBest(probabilityMoves);
		}
		moves.add(move);
		return move;
	}

	private int[] adventurousMove() {
		String boardString = game.getBoard().toString();
		boardsStrings.add(boardString);
		List<MoveProbability> probabilityMoves = pheromones.get(boardString);
		int[] move = null;

		if (probabilityMoves == null) {
			List<int[]> availableMoves = ChessEngine.availableMoves(game);
			probabilityMoves = createProbabilityMoves(availableMoves);
			pheromones.put(boardString, probabilityMoves);
			if (availableMoves.size() > 0) {
				int idx = random.nextInt(availableMoves.size());
				move = availableMoves.get(idx);
			}
		} else {
			move = chooseRandom(probabilityMoves);
		}
		// System.out.println("CHOSEN MOVE: " + MoveParser.parse(move));
		moves.add(move);
		if (move == null) {
			System.out.println();
		}
		return move;
	}

	private List<MoveProbability> createProbabilityMoves(List<int[]> availableMoves) {
		List<MoveProbability> moves = new ArrayList<>();
		double probability = 1.0 / (double) availableMoves.size();
		for (int[] move : availableMoves) {
			moves.add(new MoveProbability(move, probability));
		}
		return moves;
	}

	private int[] chooseRandom(List<MoveProbability> probabilityMoves) {
		double value = random.nextDouble();
		double cumulativeProbability = 0.0;
		for (MoveProbability move : probabilityMoves) {
			cumulativeProbability += move.getProbability();
			if (value <= cumulativeProbability) {
				return move.getMove();
			}
		}
		return null;
	}

	private int[] chooseBest(List<MoveProbability> probabilityMoves) {
		MoveProbability best = probabilityMoves.get(0);
		for (MoveProbability move : probabilityMoves) {
			if (move.getProbability() > best.getProbability()) {
				best = move;
			}
		}
		return best.getMove();
	}

	private void loadSolution(File file) throws IOException {
		pheromones = new HashMap<>();
		List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charset.forName("UTF-8"));

		for (int i = 0; i < lines.size(); i += 2) {
			String hash = lines.get(i);
			List<MoveProbability> list = new ArrayList<>();
			String[] values = lines.get(i + 1).split(" ");
			if (values.length > 1) {
				for (int j = 0; j < values.length; j += 6) {
					int[] move = new int[5];
					move[0] = Integer.parseInt(values[j]);
					move[1] = Integer.parseInt(values[j + 1]);
					move[2] = Integer.parseInt(values[j + 2]);
					move[3] = Integer.parseInt(values[j + 3]);
					move[4] = Integer.parseInt(values[j + 4]);
					double probability = Double.parseDouble(values[j + 5]);
					list.add(new MoveProbability(move, probability));
				}
			}
			pheromones.put(hash, list);
		}
		System.out.println("LOADED SOLUTION FILE");
	}

	public List<int[]> getMoves() {
		return moves;
	}

	public List<String> getBoardHashes() {
		return boardsStrings;
	}

	public void setPheromones(Map<String, List<MoveProbability>> pheromones) {
		this.pheromones = pheromones;
	}

}
