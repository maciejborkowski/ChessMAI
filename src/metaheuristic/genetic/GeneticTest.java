package metaheuristic.genetic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

import metaheuristic.CostFunction;
import uci.AdapterPool;
import uci.WindowsUCIAdapter;
import chess.engine.ChessColor;
import chess.engine.ChessGame;
import chess.engine.ChessOptions;
import chess.player.AIPlayer;
import chess.player.GeneticPlayer;
import chess.player.MetaheuristicPlayer;

import java.util.Random;

public class GeneticTest implements Runnable {
	private int size = 4;
	private int maxGames=0;
	private int totalGames = 0;
	private boolean running = true;
	private ChessOptions options;
	private List<ChessGame> games = new ArrayList<>();
	private final List<Thread> threads = new ArrayList<>();
	File file;
	public GeneticTest(ChessOptions geneticOptions) {

		AdapterPool pool = geneticOptions.getAdapterPool();
		while (pool.size() < size) {
			pool.addAdapter(new WindowsUCIAdapter());
		}

		geneticOptions.setPlayerBlack(AIPlayer.class);
		geneticOptions.setPlayerWhite(GeneticPlayer.class);
		geneticOptions.setBoard(null);
		this.options = geneticOptions;
	}

	@Override
	public void run() {
		System.out.println("Testing");
		while (running)
		{
			runSetOfGames();
			waitForGames();
			resetGames(); // it is really necessary?
		}
	}
	
	private void resetGames() {
		for (ChessGame game : games) {
			game.reset();
		}
	}

	private void waitForGames() {
		for (int i = 0; i < size; i++) {
			try {
				threads.get(threads.size()-i-1).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void runSetOfGames() {
		for (int i = 0; i < size; i++) {
			totalGames++;
			ChessGame game = initGame();
			games.add(game);
			Thread gameThread = new Thread(game);
			threads.add(gameThread);
			gameThread.start();
		}
	}

	private ChessGame initGame() {
		file = new File("gensresult.txt");
		options.setMetaheuristicSolutionWhite(file);
		ChessGame game = new ChessGame(options);
		//((GeneticPlayer) game.getWhitePlayer()).setGens(gens);
		//((GeneticPlayer) game.getWhitePlayer()).setMode(MetaheuristicPlayer.Mode.ADVENTUROUS);
		return game;
	}
	
/*	private void collectGens()
	{
		for (ChessGame game : games)
		{
			Map<String, MoveCost> gameGens = ((GeneticPlayer)game.getWhitePlayer()).getGens();
			//System.out.println(gameGens.values());
			for (String board : gameGens.keySet())
			{
				List <MoveCost> moveCostGlobal = gens.get(board);
				List <MoveCost> moveCostIndividual = gameGens.get(board);
				if (null == moveCostGlobal)
					gens.put(board, moveCostIndividual);
				else
					moveCostGlobal.addAll(moveCostIndividual);
			}
		}
	}*/

	public void stop() {
		running = false;
	}

}
