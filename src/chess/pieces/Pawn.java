package chess.pieces;

import java.util.ArrayList;

import chess.engine.ChessColor;
import chess.engine.ChessGame;
import chess.engine.Square;

public class Pawn extends Piece {
	private int ny = 0;
	private boolean passantTarget = false;

	public Pawn(ChessGame game, ChessColor color, int x, int y) {
		super(game, color, x, y);
		if (color.equals(ChessColor.BLACK)) {
			ny = 1;
		} else {
			ny = -1;
		}
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();
		Square forward = game.getSquare(x, y + ny);
		if (checkMovableSquare(forward)) {
			possibleMoves.add(forward);
		}
		// Pawn can move two squares forward on his first move
		Square doubleForward = game.getSquare(x, y + 2 * ny);
		if (!moved && (possibleMoves.contains(forward)) && checkMovableSquare(doubleForward)) {
			possibleMoves.add(doubleForward);
		}
		Square forwardLeft = game.getSquare(x - 1, y + ny);
		if (checkAttackableSquare(forwardLeft)) {
			possibleMoves.add(forwardLeft);
		}
		Square forwardRight = game.getSquare(x + 1, y + ny);
		if (checkAttackableSquare(forwardRight)) {
			possibleMoves.add(forwardRight);
		}
		// En Passant
		Square passantLeft = game.getSquare(x - 1, y);
		Square passantLeftForward = game.getSquare(x - 1, y + ny);
		if (checkMovableSquare(passantLeftForward) && (passantLeft.getPiece() instanceof Pawn)
				&& ((Pawn) (passantLeft.getPiece())).isEnPassantTarget()) {
			possibleMoves.add(passantLeftForward);
		}
		Square passantRight = game.getSquare(x + 1, y);
		Square passantRightForward = game.getSquare(x + 1, y + ny);
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
