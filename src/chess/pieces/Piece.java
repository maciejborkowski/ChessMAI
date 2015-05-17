package chess.pieces;

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

	public boolean isCheck() {
		List<Piece> pieces;
		if (game.getTurn() == ChessColor.WHITE)
			pieces = game.getWhitePieces();
		else
			pieces = game.getBlackPieces();
		for (Piece pic : pieces) {
			if (pic instanceof King) {
				Square square = game.getSquare(pic.getX(), pic.getY());
				if (!((King) pic).checkAttackedSquare(square)) {
					System.out.println("Jest atakowany");
					return true;
				}
			}
		}
		return false;
	}

	public List<Square> getPossibleMoves() {
		possibleMoves = null;
		createPossibleMoves();
		return possibleMoves;
	}

	public boolean canMove(int x, int y) {
		possibleMoves = null;
		Square ms = game.getSquare(x, y);
		createPossibleMoves();
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
				if (isOpponent(square.getPiece()))
					return true;
			}
		}
		return false;
	}

	public boolean isOpponent(Piece p) {
		if (getColor() == ChessColor.WHITE && p.getColor() == ChessColor.BLACK)
			return true;
		if (getColor() == ChessColor.BLACK && p.getColor() == ChessColor.WHITE)
			return true;
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
