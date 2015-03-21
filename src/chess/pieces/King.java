package chess.pieces;

import java.util.ArrayList;

import chess.ChessEngine;
import chess.Color;
import chess.Piece;
import chess.Square;

public final class King extends Piece {

	public King(ChessEngine game, Color color, int x, int y) {
		super(game, color, x, y);
		if (color.equals(Color.BLACK)) {
			type = Piece.BLACK_KING;
		} else {
			type = Piece.WHITE_KING;
		}
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		int[][] offsets = { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, -1 }, { -1, 0 }, { -1, 1 } };

		for (int[] dir : offsets) {
			Square square = engine.getSquare(x + dir[0], y + dir[1]);
			if (checkMovableSquare(square) || checkAttackableSquare(square)) {
				possibleMoves.add(square);
			}
		}

		// Castling
		if (!moved && (engine.getSquare(x + 3, y).getPiece() != null)
				&& !engine.getSquare(x + 3, y).getPiece().getMoved()) {
			Square square1 = engine.getSquare(x + 1, y);
			Square square2 = engine.getSquare(x + 2, y);
			if (checkMovableSquare(square1) && checkMovableSquare(square2)) {
				possibleMoves.add(square2);
			}
		}
		if (!moved && (engine.getSquare(x - 4, y).getPiece() != null)
				&& !engine.getSquare(x - 4, y).getPiece().getMoved()) {
			Square square1 = engine.getSquare(x - 1, y);
			Square square2 = engine.getSquare(x - 2, y);
			Square square3 = engine.getSquare(x - 3, y);
			if (checkMovableSquare(square1) && checkMovableSquare(square2) && checkMovableSquare(square3)) {
				possibleMoves.add(square2);
			}
		}
	}
}
