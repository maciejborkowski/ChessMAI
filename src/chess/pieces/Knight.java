package chess.pieces;

import java.util.ArrayList;

import chess.ChessEngine;
import chess.Color;
import chess.Piece;
import chess.Square;

public final class Knight extends Piece {

	public Knight(ChessEngine game, int type, Color color, int x, int y) {
		super(game, type, color, x, y);
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();

		int[][] offsets = { { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 }, { 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 } };
		for (int[] o : offsets) {
			checkMovableSquare(x + o[0], y + o[1]);
		}
	}
}