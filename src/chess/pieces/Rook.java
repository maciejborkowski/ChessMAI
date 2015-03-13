package chess.pieces;

import java.util.ArrayList;

import chess.ChessEngine;
import chess.Color;
import chess.Piece;
import chess.Square;

public final class Rook extends Piece {

	public Rook(ChessEngine game, int type, Color color, int x, int y) {
		super(game, type, color, x, y);
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		boolean north = true, south = true, west = true, east = true;

		for (int i = 1; i < ChessEngine.SQUARE_HEIGHT; i++) {
			if (!north && !south && !west && !east)
				break;

			if (north) {
				int dx = x, dy = y;
				dy -= i;
				north = checkMovableSquare(dx, dy);
			}
			if (south) {
				int dx = x, dy = y;
				dy += i;
				south = checkMovableSquare(dx, dy);
			}
			if (west) {
				int dx = x, dy = y;
				dx -= i;
				west = checkMovableSquare(dx, dy);
			}
			if (east) {
				int dx = x, dy = y;
				dx += i;
				east = checkMovableSquare(dx, dy);
			}
		}
	}

}
