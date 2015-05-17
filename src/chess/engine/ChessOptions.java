package chess.engine;

import application.ChessBoardPanel;
import chess.player.Player;
import uci.AdapterPool;

public class ChessOptions {
	private ChessBoardPanel board;
	private Class<? extends Player> blackPlayer;
	private Class<? extends Player> whitePlayer;
	private AdapterPool adapterPool;
	private int maxLength = 0;

	public ChessOptions() {
	}

	public ChessBoardPanel getBoard() {
		return board;
	}

	public void setBoard(ChessBoardPanel board) {
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

	public AdapterPool getAdapterPool() {
		return adapterPool;
	}

	public void setAdapterPool(AdapterPool adapterPool) {
		this.adapterPool = adapterPool;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

}
