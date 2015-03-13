package chess.pieces;

import java.util.ArrayList;

import chess.ChessEngine;
import chess.Color;
import chess.Piece;
import chess.Square;

public final class King extends Piece {

	public King(ChessEngine game, int type, Color color, int x, int y) {
		super(game, type, color, x, y);
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		int[][] offset = { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, -1 }, { -1, 0 }, { -1, 1 } };

		for (int[] dir : offset) {
			checkMovableSquare(x + dir[0], y + dir[1]);
		}
	}

}
