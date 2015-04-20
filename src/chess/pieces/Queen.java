package chess.pieces;

import java.util.ArrayList;

import chess.ChessEngine;
import chess.ChessGame;
import chess.ChessColor;
import chess.Square;

public final class Queen extends Piece {

	public Queen(ChessGame game, ChessColor color, int x, int y) {
		super(game, color, x, y);
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		boolean north = true, south = true, west = true, east = true, nw = true, ne = true, sw = true, se = true;

		for (int i = 1; i < ChessEngine.SQUARE_HEIGHT; i++) {
			if (!nw && !ne && !sw && !se && !north && !south && !west && !east)
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
