package chess.pieces;

import java.util.Iterator;
import java.util.List;

import chess.engine.ChessColor;
import chess.engine.ChessGame;
import chess.engine.Square;

public abstract class Piece {
	protected ChessGame game;
	protected int x, y;
	protected ChessColor color;
	protected List<Square> possibleMoves = null;
	protected boolean moved = false;

	public Piece(ChessGame game, ChessColor color, int x, int y) {
		this.game = game;
		this.color = color;
		this.x = x;
		this.y = y;
	}

	public abstract void createPossibleMoves();

	public List<Square> getPossibleMoves() {
		possibleMoves = null;
		createPossibleMoves();

		for (Iterator<Square> iter = possibleMoves.listIterator(); iter.hasNext();) {
			Square move = iter.next();
			if (checkKingAttackedAfterMove(move)) {
				iter.remove();
			}
		}
		return possibleMoves;
	}

	public boolean canMove(int x, int y) {
		possibleMoves = null;
		Square ms = game.getSquare(x, y);
		getPossibleMoves();
		if (possibleMoves != null)
			if (possibleMoves.contains(ms)) {
				return true;
			}
		return false;
	}

	protected boolean checkMovableSquare(Square square) {
		if (square != null) {
			if (square.getPiece() == null) {
				return true;
			}
		}
		return false;
	}

	protected boolean checkAttackableSquare(Square square) {
		if (square != null) {
			if (square.getPiece() != null) {
				if (isOpponent(square.getPiece())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkKingAttackedAfterMove(Square moveTo) {
		Square moveFrom = game.getSquare(x, y);
		Piece attackedPiece = moveTo.getPiece();

		// Simulate move
		int oldX = x;
		int oldY = y;
		x = moveTo.getX();
		y = moveTo.getY();
		moveFrom.setPiece(null);
		moveTo.setPiece(this);

		boolean isCheck = isCheck();

		// Return to previous state
		x = oldX;
		y = oldY;
		moveTo.setPiece(attackedPiece);
		moveFrom.setPiece(this);

		return isCheck;
	}

	private boolean isCheck() {
		List<Piece> pieces;
		if (game.getTurn() == ChessColor.WHITE)
			pieces = game.getWhitePieces();
		else
			pieces = game.getBlackPieces();
		for (Piece piece : pieces) {
			if (piece instanceof King) {
				Square square = game.getSquare(piece.getX(), piece.getY());
				if (((King) piece).checkAttackedSquare(square)) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

	public boolean isOpponent(Piece p) {
		if (getColor() == ChessColor.WHITE && p.getColor() == ChessColor.BLACK) {
			return true;
		}
		if (getColor() == ChessColor.BLACK && p.getColor() == ChessColor.WHITE) {
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ChessColor getColor() {
		return color;
	}

	public void setMoved(boolean b) {
		moved = true;
	}

	public boolean getMoved() {
		return moved;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Piece other = (Piece) obj;

		if (x == other.x && y == other.y && color == other.color && this.getClass() == other.getClass()) {
			return true;
		}
		return false;
	}

}
