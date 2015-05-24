package metaheuristic;

import chess.engine.ChessOptions;

public class MetaheuristicOptions {
	private ChessOptions chessOptions;
	private int maxLength = 0;

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
}
