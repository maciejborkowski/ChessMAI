package metaheuristic.genetic;

import java.io.File;
import java.io.FileNotFoundException;
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
import chess.player.AIPlayer;
import chess.player.GeneticPlayer;
import chess.player.MetaheuristicPlayer;

import java.util.Random;

public class Genetic implements Runnable {
	private int size = 4;
	private int maxGames=0;
	private int totalGames = 0;
	private boolean running = true;
	private GeneticOptions geneticOptions;
	
	private List<ChessGame> games = new ArrayList<>();
	private final List<Thread> threads = new ArrayList<>();
	private int goodGensInPercent;
	private Random random = new Random();
	private ChessGame globalIndividual;

	public Genetic(GeneticOptions geneticOptions) {
		this.geneticOptions = geneticOptions;
		maxGames = geneticOptions.getPopulation();
		goodGensInPercent = geneticOptions.getGoodGensPercent();
		AdapterPool pool = geneticOptions.getChessOptions().getAdapterPool();
		while (pool.size() < size) {
			pool.addAdapter(new WindowsUCIAdapter());
		}

		geneticOptions.getChessOptions().setPlayerBlack(AIPlayer.class);
		geneticOptions.getChessOptions().setPlayerWhite(GeneticPlayer.class);
		geneticOptions.getChessOptions().setBoard(null);
	}

	@Override
	public void run() {
		System.out.println("CREATING POPULATION");
		while (running)
		{
			runSetOfGames();
			waitForGames();
			updateAllCosts();
			resetGames(); // it is really necessary?
			
			if (totalGames >= maxGames)
				running = false;
		}
		running = true;
		System.out.println("THE POPULATION HAS BEEN CREATED");
		System.out.println("GENETIC ALGORITHM TURNED ON");
		while (running)
		{
			selectionPhase();
			findGlobalIndividual();
			crossingPhase();
			//updateGamesCosts();
		}

		System.out.println("GENETIC ALGORITHM STOPPED");
		//collectGens();
		saveSolution(new File("gensresult.txt"));
	}
	
	static final Comparator<ChessGame> SENIORITY_ORDER = new Comparator<ChessGame>() {
		public int compare(ChessGame e1, ChessGame e2) {
			return (int)(e1.getCost() - e2.getCost());
		}
	};
	
	private void selectionPhase()
	{
		Collections.sort(games, SENIORITY_ORDER);
		int deleteCount = games.size()*(100-goodGensInPercent)/100;
		for (int i=0; i<deleteCount; i++)
			games.remove(0);

	}
	
	private void findGlobalIndividual()
	{
		ChessGame localIndividual = games.get(games.size()-1);

		if (globalIndividual == null || localIndividual.getCost() > globalIndividual.getCost())
			globalIndividual = localIndividual;
	}
	
	private void crossingPhase()
	{
		Collections.shuffle(games);
		List<ChessGame> newGames = new ArrayList<>();
		for (int i=0; i < maxGames; i++)
		{
			int idx1 = random.nextInt(games.size());
			int idx2 = random.nextInt(games.size());//getWhitePlayer()
			ChessGame firstIndividual = games.get(idx1);
			ChessGame secondIndividual = games.get(idx2);
			Map<String, int[]> gensOfFirstIndividual = ((GeneticPlayer)firstIndividual.getWhitePlayer()).getGens();
			Map<String, int[]> gensOfSecondIndividual = ((GeneticPlayer)secondIndividual.getWhitePlayer()).getGens();
			ChessGame game = initGame();
			double cost=-10000;
			for (String board : gensOfFirstIndividual.keySet())
			{
				Map<String, int[]> newGens = ((GeneticPlayer)game.getWhitePlayer()).getGens();
				int[] moveFirstInd = gensOfFirstIndividual.get(board);
				int[] moveSecondInd = gensOfSecondIndividual.get(board);
				int[] newMove;
				if (null == moveSecondInd)
				{
					newMove = moveFirstInd;
					if (cost < firstIndividual.getCost())
						cost = firstIndividual.getCost();
				}
				else
				{
					ChessGame choosen = chooseBest(firstIndividual, secondIndividual);
					if (choosen == firstIndividual)
					{
						newMove = moveFirstInd;
						if (cost < firstIndividual.getCost())
							cost = firstIndividual.getCost();
					} else
					{
						newMove = moveSecondInd;
						if (cost < secondIndividual.getCost())
							cost = secondIndividual.getCost();
					}
					
				}
				newGens.put(board, newMove);
			}
			game.setCost(cost);
			newGames.add(game);
		}
		games = newGames;
	}
	
	private ChessGame chooseBest(ChessGame mc1, ChessGame mc2)
	{
		ChessGame game1 = mc1;
		ChessGame game2 = mc2;
		double cost1 = mc1.getCost();
		double cost2 = mc2.getCost();
		double sum = Math.abs(cost1) + Math.abs(cost2);
		if (cost1 < cost2)
		{
			game1 = mc2;
			game2 = mc1;
		}
		double x = random.nextDouble()*sum;
		return (Math.abs(cost1)>x) ? game1 : game2;
	}
	
	public void updateAllCosts()
	{
		for (int i = 0; i < size; i++) {
			ChessGame game = games.get(games.size()-i-1);
			double cost = CostFunction.weightedPieces(game, ChessColor.WHITE, geneticOptions.getPieceCostMap());
			game.setCost(cost);
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
		ChessGame game = new ChessGame(geneticOptions.getChessOptions());
		game.setMaxTurns(geneticOptions.getMaxLength());
		//((GeneticPlayer) game.getWhitePlayer()).setGens(gens);
		((GeneticPlayer) game.getWhitePlayer()).setMode(MetaheuristicPlayer.Mode.ADVENTUROUS);
		return game;
	}

	@SuppressWarnings("resource")
	private void saveSolution(File file) {
	try {
			file.delete();
			Map<String, int[]> gens = ((GeneticPlayer)globalIndividual.getWhitePlayer()).getGens();
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			for (Entry<String, int[]> costEntry : gens.entrySet()) {
				String string = costEntry.getKey();
				writer.write(string);
				writer.write("\n");

				StringBuilder builder = new StringBuilder();
				for (int val : costEntry.getValue()) {
					builder.append(val);
					builder.append(" ");
				}
				builder.append(globalIndividual.getCost());
				builder.append(" ");
				writer.write( builder.toString() );
				writer.write("\n");
			}
			writer.flush();
			System.out.println("SAVED TO FILE " + file.getAbsolutePath());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public void stop() {
		running = false;
	}

}
