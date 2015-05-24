package chess.engine;

import java.io.File;

import application.ChessBoardPanel;
import chess.player.Player;
import uci.AdapterPool;

public class ChessOptions {
	private ChessBoardPanel board;
	private Class<? extends Player> playerBlack;
	private Class<? extends Player> playerWhite;
	private File metaheuristicSolutionBlack;
	private File metaheuristicSolutionWhite;
	private AdapterPool adapterPool;
	private int maxLength = 0;
	private String moveHistory;

	public ChessOptions() {
	}

	public ChessBoardPanel getBoard() {
		return board;
	}

	public void setBoard(ChessBoardPanel board) {
		this.board = board;
	}

	public Class<? extends Player> getPlayerWhite() {
		return playerWhite;
	}

	public void setPlayerWhite(Class<? extends Player> playerWhite) {
		this.playerWhite = playerWhite;
	}

	public Class<? extends Player> getPlayerBlack() {
		return playerBlack;
	}

	public void setPlayerBlack(Class<? extends Player> playerBlack) {
		this.playerBlack = playerBlack;
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

	public File getMetaheuristicSolutionBlack() {
		return metaheuristicSolutionBlack;
	}

	public void setMetaheuristicSolutionBlack(File metaheuristicSolutionBlack) {
		this.metaheuristicSolutionBlack = metaheuristicSolutionBlack;
	}

	public File getMetaheuristicSolutionWhite() {
		return metaheuristicSolutionWhite;
	}

	public void setMetaheuristicSolutionWhite(File metaheuristicSolutionWhite) {
		this.metaheuristicSolutionWhite = metaheuristicSolutionWhite;
	}

	public String getMoveHistory() {
		return moveHistory;
	}

	public void setMoveHistory(String moveHistory) {
		this.moveHistory = moveHistory;
	}

}
