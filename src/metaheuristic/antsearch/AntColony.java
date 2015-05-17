package metaheuristic.antsearch;

import java.util.HashMap;
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
	private Map<ChessBoard, int[][]> pheromones;

	public AntColony(int turns) {
		options = new ChessOptions();

		AdapterPool pool = new AdapterPool();
		pool.addAdapter(new WindowsUCIAdapter());
		pool.addAdapter(new WindowsUCIAdapter());
		options.setAdapterPool(pool);
		options.setBlackPlayer(AIPlayer.class);
		options.setWhitePlayer(Ant.class);
		options.setMaxLength(turns);
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

	public void updatePheromones() {

	}

	private void saveSolution() {

	}

	public Map<ChessBoard, int[][]> getPheromones() {
		return pheromones;
	}

	public void setPheromones(Map<ChessBoard, int[][]> pheromones) {
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
