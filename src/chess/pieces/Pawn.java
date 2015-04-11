package chess.pieces;

import java.util.ArrayList;

import chess.ChessEngine;
import chess.Color;
import chess.Square;

public class Pawn extends Piece {
	private int ny = 0;
	private boolean passantTarget = false;

	public Pawn(ChessEngine game, Color color, int x, int y) {
		super(game, color, x, y);
		if (color.equals(Color.BLACK)) {
			ny = 1;
		} else {
			ny = -1;
		}
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		Square forward = engine.getSquare(x, y + ny);
		if (checkMovableSquare(forward)) {
			possibleMoves.add(forward);
		}
		// Pawn can move two squares forward on his first move
		Square doubleForward = engine.getSquare(x, y + 2 * ny);
		if (!moved && (possibleMoves.contains(forward)) && checkMovableSquare(doubleForward)) {
			possibleMoves.add(doubleForward);
		}
		Square forwardLeft = engine.getSquare(x - 1, y + ny);
		if (checkAttackableSquare(forwardLeft)) {
			possibleMoves.add(forwardLeft);
		}
		Square forwardRight = engine.getSquare(x + 1, y + ny);
		if (checkAttackableSquare(forwardRight)) {
			possibleMoves.add(forwardRight);
		}
		// En Passant
		Square passantLeft = engine.getSquare(x - 1, y);
		Square passantLeftForward = engine.getSquare(x - 1, y + ny);
		if (checkMovableSquare(passantLeftForward) && (passantLeft.getPiece() instanceof Pawn)
				&& ((Pawn) (passantLeft.getPiece())).isEnPassantTarget()) {
			possibleMoves.add(passantLeftForward);
		}
		Square passantRight = engine.getSquare(x + 1, y);
		Square passantRightForward = engine.getSquare(x + 1, y + ny);
		if (checkMovableSquare(passantRightForward) && (passantRight.getPiece() instanceof Pawn)
				&& ((Pawn) (passantRight.getPiece())).isEnPassantTarget()) {

			possibleMoves.add(passantRightForward);
		}
	}

	public boolean isEnPassantTarget() {
		return passantTarget;
	}

	public void setPassantTarget(boolean target) {
		passantTarget = target;
	}
}
