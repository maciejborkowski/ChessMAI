package framework;

import chess.ChessBoard;
import chess.Player;

public class ChessOptions {
	private ChessBoard board;
	private Class<? extends Player> blackPlayer;
	private Class<? extends Player> whitePlayer;
	
	public ChessOptions() {
	}

	public ChessBoard getBoard() {
		return board;
	}

	public void setBoard(ChessBoard board) {
		this.board = board;
	}

	public Class<? extends Player> getWhitePlayer() {
		return whitePlayer;
	}

	public void setWhitePlayer(Class<? extends Player> whitePlayer) {
		this.whitePlayer = whitePlayer;
	}

	public Class<? extends Player> getBlackPlayer() {
		return blackPlayer;
	}

	public void setBlackPlayer(Class<? extends Player> blackPlayer) {
		this.blackPlayer = blackPlayer;
	}

}
