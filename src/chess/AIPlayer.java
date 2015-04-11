package chess;

import uci.MoveParser;
import uci.UCIAdapter;
import uci.WindowsUCIAdapter;

public class AIPlayer extends Player {
	private UCIAdapter adapter = new WindowsUCIAdapter();
	private int[] move = new int[5];

	public AIPlayer() {
		adapter.uci();
		adapter.isready();
	}

	@Override
	public int[] think() {
		System.out.println(color + " THINKS");

		// Tell engine what moves have already happened
		adapter.ucinewgame();
		adapter.position(game.getMoveHistory().toString());
		adapter.isready();

		// Make your move
		String moveString = adapter.go(100);
		System.out.println(color.toString() + " MOVE: " + moveString);
		MoveParser.parse(moveString, move);

		return move;
	}

}
