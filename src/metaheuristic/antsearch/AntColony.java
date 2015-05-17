package metaheuristic.antsearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import metaheuristic.CostFunction;
import uci.AdapterPool;
import uci.WindowsUCIAdapter;
import chess.engine.ChessBoard;
import chess.engine.ChessColor;
import chess.engine.ChessGame;
import chess.engine.ChessOptions;
import chess.player.AIPlayer;

public class AntColony implements Runnable {
	private boolean running = true;
	private ChessOptions options;
	private Map<ChessBoard, List<MoveProbability>> pheromones;

	public AntColony(ChessOptions options) {
		this.options = options;

		AdapterPool pool = options.getAdapterPool();
		if (pool.size() < 2) {
			pool.addAdapter(new WindowsUCIAdapter());
			pool.addAdapter(new WindowsUCIAdapter());
		}
		options.setBlackPlayer(AIPlayer.class);
		options.setWhitePlayer(Ant.class);
		options.setBoard(null);

		pheromones = new HashMap<>();
	}

	@Override
	public void run() {
		while (running) {
			try {
				ChessGame game = new ChessGame(options);
				((Ant) game.getWhitePlayer()).setColony(this);

				Thread gameThread = new Thread(game);
				gameThread.start();
				gameThread.join();

				double cost = CostFunction.weightedPieces(game, ChessColor.WHITE);
				updatePheromones();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		saveSolution();
	}

	private void updatePheromones() {

	}

	private void saveSolution() {

	}

	public Map<ChessBoard, List<MoveProbability>> getPheromones() {
		return pheromones;
	}

	public void setPheromones(Map<ChessBoard, List<MoveProbability>> pheromones) {
		this.pheromones = pheromones;
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
