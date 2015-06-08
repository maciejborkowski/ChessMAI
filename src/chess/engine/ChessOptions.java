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
	private String initialMoveHistory;
	private HistoryContainer moveHistoryContainer;
	
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

	public String getInitialMoveHistory() {
		return initialMoveHistory;
	}

	public void setInitialMoveHistory(String initialMoveHistory) {
		this.initialMoveHistory = initialMoveHistory;
	}

	public HistoryContainer getMoveHistoryContainer() {
		return moveHistoryContainer;
	}

	public void setMoveHistoryContainer(HistoryContainer moveHistoryContainer) {
		this.moveHistoryContainer = moveHistoryContainer;
	}

}
