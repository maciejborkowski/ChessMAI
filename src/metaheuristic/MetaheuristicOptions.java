package metaheuristic;

import java.util.HashMap;

import chess.engine.ChessOptions;
import chess.pieces.Piece;

public class MetaheuristicOptions {
	private ChessOptions chessOptions;
	private int maxLength = 0;
	private HashMap<Class<? extends Piece>, Double> pieceCostMap;

	public ChessOptions getChessOptions() {
		return chessOptions;
	}

	public void setChessOptions(ChessOptions chessOptions) {
		this.chessOptions = chessOptions;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public HashMap<Class<? extends Piece>, Double> getPieceCostMap() {
		return pieceCostMap;
	}

	public void setPieceCostMap(HashMap<Class<? extends Piece>, Double> pieceCostMap) {
		this.pieceCostMap = pieceCostMap;
	}
}
