package chess.pieces;

import java.util.ArrayList;

import chess.ChessEngine;
import chess.Color;
import chess.Piece;
import chess.Square;

public final class Bishop extends Piece {

	public Bishop(ChessEngine game, int type, Color color, int x, int y) {
		super(game, type, color, x, y);
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		boolean nw = true, ne = true, sw = true, se = true;

		for (int i = 1; i < ChessEngine.SQUARE_HEIGHT; i++) {
			if (!nw && !ne && !sw && !se)
				break;
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
