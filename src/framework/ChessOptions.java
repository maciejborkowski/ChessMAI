package framework;

import chess.ChessBoard;
import chess.Player;

public class ChessOptions {
	private ChessBoard board;
	private Player white;
	private Player black;

	public ChessOptions() {
	}

	public ChessBoard getBoard() {
		return board;
	}

	public void setBoard(ChessBoard board) {
		this.board = board;
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
