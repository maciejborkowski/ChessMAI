package chess.pieces;

import java.util.ArrayList;

import chess.*;

public class Pawn extends Piece {

	private int ny = 0;

	public Pawn(ChessEngine game, int type, Color color, int x, int y) {
		super(game, type, color, x, y);
		possibleMoves = null;
		if (color == Color.WHITE)
			ny = -1;
		else
			ny = 1;
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		int dy = ny;
		Square ahead = game.getSquare(x, y + dy);
		if (ahead != null && ahead.getPiece() == null) {
			possibleMoves.add(ahead);
		}
		Square aheadLeft = game.getSquare(x - 1, y + dy);
		if ((aheadLeft != null) && (aheadLeft.getPiece() != null) && (isOpponent(aheadLeft.getPiece()))) {
			possibleMoves.add(aheadLeft);
		}
		Square aheadRight = game.getSquare(x + 1, y + dy);
		if ((aheadRight != null) && (aheadRight.getPiece() != null) && isOpponent(aheadRight.getPiece())) {
			possibleMoves.add(aheadRight);
		}

	}

}
