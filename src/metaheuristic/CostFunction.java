package metaheuristic;

import java.util.HashMap;

import chess.engine.ChessColor;
import chess.engine.ChessEngine;
import chess.engine.ChessGame;
import chess.pieces.Piece;

public class CostFunction {
	public static double weightedPieces(ChessGame game, ChessColor color, HashMap<Class<? extends Piece>, Double> pieceCostMap) {
		if (game.getState() == ChessEngine.State.CHECKMATE) {
			if (game.getWinner() == color) {
				return 1000.0;
			} else {
				return -1000.0;
			}
		}
		double cost = 0.0;
		for (Piece piece : game.getBlackPieces()) {
			if (piece.getColor() == color) {
				cost += pieceCostMap.get(piece.getClass());
			} else {
				cost -= pieceCostMap.get(piece.getClass());
			}
		}

		for (Piece piece : game.getWhitePieces()) {
			if (piece.getColor() == color) {
				cost += pieceCostMap.get(piece.getClass());
			} else {
				cost -= pieceCostMap.get(piece.getClass());
			}
		}
		return cost;
	}
}
