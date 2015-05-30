package metaheuristic.antsearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import metaheuristic.CostFunction;
import uci.AdapterPool;
import uci.WindowsUCIAdapter;
import chess.engine.ChessColor;
import chess.engine.ChessGame;
import chess.player.AIPlayer;
import chess.player.AntPlayer;
import chess.player.MetaheuristicPlayer;

public class AntColony implements Runnable {
	private int size = 4;
	private boolean running = true;
	private ColonyOptions colonyOptions;
	private final Map<String, List<MoveProbability>> pheromones;
	private final Set<ChessGame> games = new HashSet<>();
	private final Set<Thread> threads = new HashSet<>();

	public AntColony(ColonyOptions colonyOptions) {
		this.colonyOptions = colonyOptions;

		AdapterPool pool = colonyOptions.getChessOptions().getAdapterPool();
		while (pool.size() < size) {
			pool.addAdapter(new WindowsUCIAdapter());
		}

		colonyOptions.getChessOptions().setPlayerBlack(AIPlayer.class);
		colonyOptions.getChessOptions().setPlayerWhite(AntPlayer.class);
		colonyOptions.getChessOptions().setBoard(null);

		pheromones = new ConcurrentHashMap<>();
	}

	@Override
	public void run() {
		initGames();
		double initCost = 0.0;
		for (ChessGame game : games) {
			initCost = CostFunction.weightedPieces(game, ChessColor.WHITE, colonyOptions.getPieceCostMap());
			break;
		}

		while (running) {
			startGames();
			waitForGames();
			updatePheromones(initCost);
			resetGames();
		}
		saveSolution(new File("antresult.txt"));
	}

	private void resetGames() {
		for (ChessGame game : games) {
			game.reset();
		}
	}

	private void startGames() {
		for (ChessGame game : games) {
			Thread gameThread = new Thread(game);
			threads.add(gameThread);
			gameThread.start();
		}
	}

	private void waitForGames() {
		try {
			for (Thread thread : threads) {
				thread.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void initGames() {
		for (int i = 0; i < size; i++) {
			ChessGame game = initGame();
			games.add(game);
		}
	}

	private ChessGame initGame() {
		ChessGame game = new ChessGame(colonyOptions.getChessOptions());
		game.setMaxTurns(colonyOptions.getMaxLength());
		((AntPlayer) game.getWhitePlayer()).setPheromones(pheromones);
		((AntPlayer) game.getWhitePlayer()).setMode(MetaheuristicPlayer.Mode.ADVENTUROUS);
		return game;
	}

	private void updatePheromones(double initCost) {
		for (ChessGame game : games) {
			double cost = calculateCost(game, initCost);
			updatePheromone(game, cost);
		}
	}

	private double calculateCost(ChessGame game, double initialCost) {
		double cost = CostFunction.weightedPieces(game, ChessColor.WHITE, colonyOptions.getPieceCostMap());
		return cost - initialCost;
	}

	private void updatePheromone(ChessGame game, double cost) {
		List<int[]> moves = ((AntPlayer) game.getWhitePlayer()).getMoves();
		List<String> boardStrings = ((AntPlayer) game.getWhitePlayer()).getBoardHashes();

		for (int i = 0; i < boardStrings.size(); i++) {
			List<MoveProbability> pheromone = pheromones.get(boardStrings.get(i));
			double value = 0.001 * cost;
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
	private void saveSolution(File file) {
		try {
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
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public Map<String, List<MoveProbability>> getPheromones() {
		return pheromones;
	}

	public void stop() {
		running = false;
	}

}
