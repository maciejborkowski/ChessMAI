package chess.player;

import uci.AdapterPool;
import uci.MoveParser;
import uci.UCIAdapter;

public class AIPlayer extends Player {
	private AdapterPool pool;
	private int[] move = new int[5];

	public AIPlayer(AdapterPool pool) {
		this.pool = pool;
	}

	@Override
	public int[] think() throws Exception {
		// System.out.println(color + " THINKS");

		// Tell engine what moves have already happened
		UCIAdapter adapter = pool.bindAdapter();
		adapter.ucinewgame();
		System.out.println("Sending history: " + game.getMoveHistory().toString());
		adapter.position(game.getMoveHistory().toString());
		adapter.isready();
		System.out.println("Accepted history: " + game.getMoveHistory().toString());

		// Make your move
		String moveString = adapter.go(1);
		pool.releaseAdapter(adapter);

		// System.out.println(color.toString() + " MOVE: " + moveString);
		MoveParser.parse(moveString, move);

		return move;
	}

}
