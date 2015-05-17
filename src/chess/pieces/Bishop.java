package chess.pieces;

import java.util.ArrayList;

import chess.engine.ChessColor;
import chess.engine.ChessEngine;
import chess.engine.ChessGame;
import chess.engine.Square;

public final class Bishop extends Piece {

	public Bishop(ChessGame game, ChessColor color, int x, int y) {
		super(game, color, x, y);
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		boolean nw = true, ne = true, sw = true, se = true;

		for (int i = 1; i < ChessEngine.SQUARE_HEIGHT; i++) {
			if (!nw && !ne && !sw && !se)
				break;
			if (nw) {
				Square square = game.getSquare(x - i, y - i);
				nw = processSquare(square);
			}
			if (ne) {
				Square square = game.getSquare(x + i, y - i);
				ne = processSquare(square);
			}
			if (sw) {
				Square square = game.getSquare(x - i, y + i);
				sw = processSquare(square);
			}
			if (se) {
				Square square = game.getSquare(x + i, y + i);
				se = processSquare(square);
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
