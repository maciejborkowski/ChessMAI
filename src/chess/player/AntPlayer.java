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
	private Random random;

	public AntPlayer(File solutionFile) throws IOException {
		if (solutionFile != null) {
			loadSolution(solutionFile);
		}
		random = new Random();
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

	private int[] adventurousMove() throws Exception {
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
			// List<MoveProbability> moves =
			// removeLocalMaxima(probabilityMoves);
			if (probabilityMoves.size() > 0) {
				// move = chooseRandom(probabilityMoves);
				// move = chooseRandomLogarithmically(probabilityMoves);
				move = chooseRandomIntolerantly(probabilityMoves, 2.0);
			}
		}
		// System.out.println("CHOSEN MOVE: " + MoveParser.parse(move));
		moves.add(move);
		return move;
	}

	private List<MoveProbability> createProbabilityMoves(List<int[]> availableMoves) {
		List<MoveProbability> moves = new ArrayList<>();
		double probability = 0.0;
		for (int[] move : availableMoves) {
			moves.add(new MoveProbability(move, probability));
		}
		return moves;
	}

	@SuppressWarnings("unused")
	@Deprecated
	private List<MoveProbability> removeLocalMaxima(List<MoveProbability> source) {
		List<MoveProbability> target = new ArrayList<>();
		for (MoveProbability moveProbability : source) {
			if (moveProbability.getProbability() < 100.0) {
				target.add(moveProbability);
			}
		}
		return target;
	}

	private int[] chooseRandom(List<MoveProbability> pheromone) throws Exception {
		double min = Math.abs(minPheromone(pheromone));
		double value = random.nextDouble() * sumPheromone(pheromone, min);
		double cumulativeProbability = 0.0;
		for (MoveProbability moveProbability : pheromone) {
			cumulativeProbability += moveProbability.getProbability() + min;
			if (value <= cumulativeProbability) {
				return moveProbability.getMove();
			}
		}
		throw new Exception("PROBABILITY ERROR");
	}

	private int[] chooseRandomLogarithmically(List<MoveProbability> pheromone) throws Exception {
		double min = Math.abs(minPheromone(pheromone));
		double value = random.nextDouble() * sumLogPheromone(pheromone, min);
		double cumulativeProbability = 0.0;
		for (MoveProbability moveProbability : pheromone) {
			cumulativeProbability += Math.log(moveProbability.getProbability() + min + 1);
			if (value <= cumulativeProbability) {
				return moveProbability.getMove();
			}
		}
		throw new Exception("PROBABILITY ERROR");
	}

	private int[] chooseRandomIntolerantly(List<MoveProbability> pheromone, double tolerance) throws Exception {
		double min = Math.abs(minPheromone(pheromone));
		int[] best = chooseBest(pheromone);
		double value = random.nextDouble() * sumIntolerantPheromone(pheromone, min, best, tolerance);
		double cumulativeProbability = 0.0;
		for (MoveProbability moveProbability : pheromone) {
			if (moveProbability.getMove().equals(best)) {
				cumulativeProbability += (moveProbability.getProbability() + min);
			} else {
				cumulativeProbability += (moveProbability.getProbability() + min) / (pheromone.size() / tolerance);
			}
			if (value <= cumulativeProbability) {
				return moveProbability.getMove();
			}
		}
		throw new Exception("PROBABILITY ERROR");
	}

	private double minPheromone(List<MoveProbability> pheromone) {
		double min = 0.0;
		for (MoveProbability moveProbability : pheromone) {
			if (moveProbability.getProbability() < min) {
				min = moveProbability.getProbability();
			}
		}
		return min;
	}

	public double sumPheromone(List<MoveProbability> pheromone, double min) {
		double sum = 0.0;
		for (MoveProbability moveProbability : pheromone) {
			sum += moveProbability.getProbability();
		}
		sum += min * pheromone.size();
		return sum;
	}

	private double sumLogPheromone(List<MoveProbability> pheromone, double min) {
		double sum = 0.0;
		for (MoveProbability moveProbability : pheromone) {
			sum += Math.log(moveProbability.getProbability() + min + 1);
		}
		return sum;
	}

	private double sumIntolerantPheromone(List<MoveProbability> pheromone, double min, int[] best, double tolerance) {
		double sum = 0.0;
		for (MoveProbability moveProbability : pheromone) {
			if (moveProbability.getMove().equals(best)) {
				sum += (moveProbability.getProbability() + min);
			} else {
				sum += (moveProbability.getProbability() + min) / (pheromone.size() / tolerance);
			}
		}
		return sum;
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

	public void reset() {
		boardsStrings = new ArrayList<>();
		moves = new ArrayList<>();
	}

}
