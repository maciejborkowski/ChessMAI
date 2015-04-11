package chess.pieces;

import java.util.ArrayList;

import chess.ChessGame;
import chess.Color;
import chess.Square;

public final class Knight extends Piece {

	public Knight(ChessGame game, Color color, int x, int y) {
		super(game, color, x, y);
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		int[][] offsets = { { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 }, { 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 } };

		for (int[] dir : offsets) {
			Square square = game.getSquare(x + dir[0], y + dir[1]);
			if (checkMovableSquare(square) || checkAttackableSquare(square)) {
				possibleMoves.add(square);
			}
		}
	}
}