package metaheuristic.antsearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import metaheuristic.CostFunction;
import uci.AdapterPool;
import uci.WindowsUCIAdapter;
import chess.engine.ChessColor;
import chess.engine.ChessGame;
import chess.engine.ChessOptions;
import chess.player.AIPlayer;
import chess.player.AntPlayer;
import chess.player.MetaheuristicPlayer;

public class AntColony implements Runnable {
	private boolean running = true;
	private ChessOptions options;
	private final Map<String, List<MoveProbability>> pheromones;

	public AntColony(ChessOptions options) {
		this.options = options;

		AdapterPool pool = options.getAdapterPool();
		if (pool.size() < 2) {
			pool.addAdapter(new WindowsUCIAdapter());
			pool.addAdapter(new WindowsUCIAdapter());
		}
		options.setPlayerBlack(AIPlayer.class);
		options.setPlayerWhite(AntPlayer.class);
		options.setBoard(null);

		pheromones = new HashMap<>();
	}

	@Override
	public void run() {
		while (running) {
			try {
				ChessGame game = new ChessGame(options);
				((AntPlayer) game.getWhitePlayer()).setPheromones(pheromones);
				((AntPlayer) game.getWhitePlayer()).setMode(MetaheuristicPlayer.Mode.ADVENTUROUS);

				Thread gameThread = new Thread(game);
				gameThread.start();
				gameThread.join();

				double cost = CostFunction.weightedPieces(game, ChessColor.WHITE);
				List<int[]> moves = ((AntPlayer) game.getWhitePlayer()).getMoves();
				List<String> boardStrings = ((AntPlayer) game.getWhitePlayer()).getBoardHashes();
				updatePheromones(cost, boardStrings, moves);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			saveSolution(new File("antresult.txt"));
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void updatePheromones(double cost, List<String> boardStrings, List<int[]> moves) {
		for (int i = 0; i < boardStrings.size(); i++) {
			List<MoveProbability> pheromone = pheromones.get(boardStrings.get(i));
			dissipate(pheromone);
			double value = cost / pheromone.size();
			for (int j = 0; j < pheromone.size(); j++) {
				if (pheromone.get(j).getMove().equals(moves.get(i))) {
					pheromone.get(j).setProbability(pheromone.get(j).getProbability() + value * (j + 1));
					if (pheromone.get(j).getProbability() <= 0.0) {
						pheromone.get(j).setProbability(0.01);
					}
					break;
				}
			}
			normalize(pheromone);
		}
	}

	private void dissipate(List<MoveProbability> pheromone) {
		double value = 0.00002 / (double) pheromone.size();
		for (MoveProbability moveProbability : pheromone) {
			moveProbability.setProbability(moveProbability.getProbability() + value);
		}
	}

	private void normalize(List<MoveProbability> pheromone) {
		double sum = sumProbability(pheromone);
		for (MoveProbability moveProbability : pheromone) {
			moveProbability.setProbability(moveProbability.getProbability() / sum);
		}
	}

	private double sumProbability(List<MoveProbability> pheromone) {
		double sum = 0.0;
		for (MoveProbability moveProbability : pheromone) {
			sum += moveProbability.getProbability();
		}
		return sum;
	}

	@SuppressWarnings("resource")
	private void saveSolution(File file) throws FileNotFoundException, UnsupportedEncodingException {
		file.delete();
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		for (Entry<String, List<MoveProbability>> pheromoneEntry : pheromones.entrySet()) {
			String string = pheromoneEntry.getKey();
			writer.write(string);
			writer.write("\n");
			for (MoveProbability moveProbability : pheromoneEntry.getValue()) {
				writer.write(moveProbability.toString());
			}
			writer.write("\n");
		}
		writer.flush();
		System.out.println("SAVED TO FILE " + file.getAbsolutePath());
	}

	public Map<String, List<MoveProbability>> getPheromones() {
		return pheromones;
	}

	public ChessOptions getOptions() {
		return options;
	}

	public void setOptions(ChessOptions options) {
		this.options = options;
	}

	public void stop() {
		running = false;
	}

}
