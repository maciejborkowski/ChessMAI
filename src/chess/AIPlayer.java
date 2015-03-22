package chess;

import uci.MovesParser;
import uci.UCIAdapter;
import uci.WindowsUCIAdapter;

public class AIPlayer extends Player {
	private UCIAdapter adapter = new WindowsUCIAdapter();
	private int[] move = new int[4];

	public AIPlayer() {
		adapter.uci();
		adapter.isready();
		adapter.ucinewgame();
		adapter.isready();
	}

	@Override
	public int[] think() {
		System.out.println(color + " THINKS");

		// Tell engine what move has been made by opponent
		adapter.position(engine.getMoveHistory());
		adapter.isready();

		// Make your move
		String moveString = adapter.go(100);
		System.out.println(color.toString() + " MOVE: " + moveString);
		MovesParser.parse(moveString, move);
		
		adapter.ucinewgame();
		adapter.isready();
		
		return move;
	}

}
