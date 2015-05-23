package chess.pieces;

import java.util.ArrayList;

import chess.engine.ChessColor;
import chess.engine.ChessEngine;
import chess.engine.ChessGame;
import chess.engine.Square;

public final class Rook extends Piece {

	public Rook(ChessGame game, ChessColor color, int x, int y) {
		super(game, color, x, y);
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		boolean north = true, south = true, west = true, east = true;

		for (int i = 1; i < ChessEngine.SQUARE_HEIGHT; i++) {
			if (!north && !south && !west && !east)
				break;
			if (north) {
				Square square = game.getSquare(x, y - i);
				north = processSquare(square);
			}
			if (south) {
				Square square = game.getSquare(x, y + i);
				south = processSquare(square);
			}
			if (west) {
				Square square = game.getSquare(x - i, y);
				west = processSquare(square);
			}
			if (east) {
				Square square = game.getSquare(x + i, y);
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
