package metaheuristic;

import java.util.HashMap;

import chess.engine.ChessColor;
import chess.engine.ChessGame;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class CostFunction {
	private static final HashMap<Class<? extends Piece>, Integer> pieceCostMap = new HashMap<>();

	static {
		pieceCostMap.put(Bishop.class, 3);
		pieceCostMap.put(King.class, 1000);
		pieceCostMap.put(Knight.class, 3);
		pieceCostMap.put(Pawn.class, 1);
		pieceCostMap.put(Queen.class, 10);
		pieceCostMap.put(Rook.class, 5);
	}

	public static double weightedPieces(ChessGame game, ChessColor color) {
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
