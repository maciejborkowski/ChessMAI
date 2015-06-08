package chess.player;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import metaheuristic.genetic.MoveCost;
import chess.engine.ChessEngine;

import java.util.Arrays;

public class GeneticPlayer extends MetaheuristicPlayer {
	private Map<String, MoveCost> gens = new HashMap<>();
	private List<String> boardsStrings = new ArrayList<>();
	private List<int[]> moves = new ArrayList<>();
	private Random random = new Random();
	
	public GeneticPlayer(File solutionFile) throws IOException {
		if (solutionFile != null) {
			gens.clear();
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
		MoveCost costMove = gens.get(boardString);
		int[] move = null;

		if (costMove == null) {
			List<int[]> availableMoves = ChessEngine.availableMoves(game);
			if (availableMoves.size() > 0) {
				int idx = random.nextInt(availableMoves.size());
				move = availableMoves.get(idx);
			}
		} else {
			move = costMove.getMove();
		}
		moves.add(move);
		return move;
	}
	
	private int[] adventurousMove() {
		String boardString = game.getBoard().toString();
		boardsStrings.add(boardString);
		MoveCost probabilityMove = gens.get(boardString);
		int[] move = null;
		
		List<int[]> availableMoves = ChessEngine.availableMoves(game);
		if (availableMoves.size() > 0) {
			int idx = random.nextInt(availableMoves.size());
			move = availableMoves.get(idx);
			MoveCost moveCost = new MoveCost(move, 0);
			
			if (probabilityMove != null) {
				gens.remove(boardString);
				System.out.println("WARNING: THE SAME BOARD OCCURED !");
			}
			gens.put(boardString, moveCost);
		}
		//System.out.println("CHOSEN MOVE: " + Arrays.toString(move));
		moves.add(move);
		return move;
	}
	
	
	private void loadSolution(File file) throws IOException {
		//gens = new HashMap<>();
		List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charset.forName("UTF-8"));

		for (int i = 0; i < lines.size(); i += 2) {
			String hash = lines.get(i);
			MoveCost mc=null;// = new MoveCost();
			String[] values = lines.get(i + 1).split(" ");
			if (values.length > 1) {
				//for (int j = 0; j < values.length; j += 6) {
					int[] move = new int[5];
					move[0] = Integer.parseInt(values[0]);
					move[1] = Integer.parseInt(values[1]);
					move[2] = Integer.parseInt(values[2]);
					move[3] = Integer.parseInt(values[3]);
					move[4] = Integer.parseInt(values[4]);
					double cost = Double.parseDouble(values[5]);
					mc = new MoveCost(move, cost);
				//}
			}
			gens.put(hash, mc);
		}
		System.out.println("LOADED SOLUTION FILE");
	}
	
	public List<int[]> getMoves() {
		return moves;
	}

	public List<String> getBoardHashes() {
		return boardsStrings;
	}
	
/*	public void setGens(Map<String, List<MoveCost>> gens) {
		this.gens = gens;
	}*/
	
	public Map<String, MoveCost> getGens() {
		return gens;
	}
}
