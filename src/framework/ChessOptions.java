package framework;

import chess.ChessEngine;
import chess.Player;

public class ChessOptions {
	private ChessEngine engine;
	private Player white;
	private Player black;

	public ChessOptions() {
	}

	public ChessEngine getEngine() {
		return engine;
	}

	public void setEngine(ChessEngine engine) {
		this.engine = engine;
	}

	public Player getWhite() {
		return white;
	}

	public void setWhite(Player white) {
		this.white = white;
	}

	public Player getBlack() {
		return black;
	}

	public void setBlack(Player black) {
		this.black = black;
	}
}
