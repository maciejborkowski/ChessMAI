package chess.pieces;

import java.util.ArrayList;

import chess.ChessEngine;
import chess.Color;
import chess.Square;

public final class King extends Piece {
	int[][] offsets = { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, -1 }, { -1, 0 }, { -1, 1 } };

	public King(ChessEngine game, Color color, int x, int y) {
		super(game, color, x, y);
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();

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

	// King blocks king
	@Override
	protected boolean checkMovableSquare(Square square) {
		if (square != null) {
			if (square.getPiece() == null) {
				if (!nearOpponentKing(square)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean nearOpponentKing(Square square) {
		for (int[] dir : offsets) {
			Square tempSquare = engine.getSquare(square.getX() + dir[0], square.getY() + dir[1]);
			if (tempSquare != null && tempSquare.getPiece() instanceof King
					&& tempSquare.getPiece().getColor() != color) {
				return true;
			}

		}
		return false;
	}
}
