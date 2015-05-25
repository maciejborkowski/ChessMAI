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
import chess.player.AIPlayer;
import chess.player.AntPlayer;
import chess.player.MetaheuristicPlayer;

public class AntColony implements Runnable {
	private boolean running = true;
	private ColonyOptions colonyOptions;
	private final Map<String, List<MoveProbability>> pheromones;

	public AntColony(ColonyOptions colonyOptions) {
		this.colonyOptions = colonyOptions;

		AdapterPool pool = colonyOptions.getChessOptions().getAdapterPool();
		if (pool.size() < 2) {
			pool.addAdapter(new WindowsUCIAdapter());
			pool.addAdapter(new WindowsUCIAdapter());
		}
		colonyOptions.getChessOptions().setPlayerBlack(AIPlayer.class);
		colonyOptions.getChessOptions().setPlayerWhite(AntPlayer.class);
		colonyOptions.getChessOptions().setBoard(null);

		pheromones = new HashMap<>();
	}

	@Override
	public void run() {
		while (running) {
			try {
				ChessGame game = new ChessGame(colonyOptions.getChessOptions());
				game.setMaxTurns(colonyOptions.getMaxLength());
				((AntPlayer) game.getWhitePlayer()).setPheromones(pheromones);
				((AntPlayer) game.getWhitePlayer()).setMode(MetaheuristicPlayer.Mode.ADVENTUROUS);
				double initialCost = CostFunction.weightedPieces(game, ChessColor.WHITE);
				Thread gameThread = new Thread(game);
				gameThread.start();
				gameThread.join();

				double cost = CostFunction.weightedPieces(game, ChessColor.WHITE);
				cost = cost - initialCost;
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
			double value = cost;
			for (int j = 0; j < pheromone.size(); j++) {
				if (pheromone.get(j).getMove().equals(moves.get(i))) {
					pheromone.get(j).setProbability(pheromone.get(j).getProbability() + value * (j + 1));
				}
			}
			dissipate(pheromone);
		}
	}

	private void dissipate(List<MoveProbability> pheromone) {
		double value = 1.0 - colonyOptions.getDissipation();
		for (MoveProbability moveProbability : pheromone) {
			moveProbability.setProbability(moveProbability.getProbability() * value);
			if (moveProbability.getProbability() <= 0.01) {
				moveProbability.setProbability(0.01);
			}
		}
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

	public void stop() {
		running = false;
	}

}
