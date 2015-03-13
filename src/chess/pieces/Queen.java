package chess.pieces;

import java.util.ArrayList;

import chess.ChessEngine;
import chess.Color;
import chess.Piece;
import chess.Square;

public final class Queen extends Piece {

	public Queen(ChessEngine game, int type, Color color, int x, int y) {
		super(game, type, color, x, y);
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		boolean north = true, south = true, west = true, east = true, nw = true, ne = true, sw = true, se = true;

		for (int i = 1; i < ChessEngine.SQUARE_HEIGHT; i++) {
			if (!nw && !ne && !sw && !se && !north && !south && !west && !east)
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
			if (nw) {
				int dx = x, dy = y;
				dx += i * -1;
				dy += i * -1;
				nw = checkMovableSquare(dx, dy);
			}
			if (ne) {
				int dx = x, dy = y;
				dx += i * 1;
				dy += i * -1;
				ne = checkMovableSquare(dx, dy);
			}
			if (sw) {
				int dx = x, dy = y;
				dx -= i;
				dy += i;
				sw = checkMovableSquare(dx, dy);
			}
			if (se) {
				int dx = x, dy = y;
				dx += i;
				dy += i;
				se = checkMovableSquare(dx, dy);
			}

		}

	}

}
