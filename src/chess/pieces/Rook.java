package chess.pieces;

import java.util.ArrayList;

import chess.ChessEngine;
import chess.Color;
import chess.Piece;
import chess.Square;

public final class Rook extends Piece {

	public Rook(ChessEngine game, Color color, int x, int y) {
		super(game, color, x, y);
		if (color.equals(Color.BLACK)) {
			type = Piece.BLACK_ROOK;
		} else {
			type = Piece.WHITE_ROOK;
		}
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		boolean north = true, south = true, west = true, east = true;

		for (int i = 1; i < ChessEngine.SQUARE_HEIGHT; i++) {
			if (!north && !south && !west && !east)
				break;
			if (north) {
				Square square = engine.getSquare(x, y - i);
				north = processSquare(square);
			}
			if (south) {
				Square square = engine.getSquare(x, y + i);
				south = processSquare(square);
			}
			if (west) {
				Square square = engine.getSquare(x - i, y);
				west = processSquare(square);
			}
			if (east) {
				Square square = engine.getSquare(x + i, y);
				east = processSquare(square);
			}
		}
	}

	private boolean processSquare(Square square) {
		if (checkMovableSquare(square)) {
			possibleMoves.add(square);
		} else {
			if (checkAttackableSquare(square)) {
				possibleMoves.add(square);
			}
			return false;
		}
		return true;
	}

}
