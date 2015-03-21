package chess;

import java.util.List;

public abstract class Piece {
	public static final int WHITE_PAWN = 0;
	public static final int WHITE_KNIGHT = 1;
	public static final int WHITE_BISHOP = 2;
	public static final int WHITE_ROOK = 3;
	public static final int WHITE_QUEEN = 4;
	public static final int WHITE_KING = 5;
	public static final int BLACK_PAWN = 6;
	public static final int BLACK_KNIGHT = 7;
	public static final int BLACK_BISHOP = 8;
	public static final int BLACK_ROOK = 9;
	public static final int BLACK_QUEEN = 10;
	public static final int BLACK_KING = 11;

	protected ChessEngine engine;
	protected int x, y;
	protected int type;
	protected Color color;
	protected List<Square> possibleMoves = null;
	protected boolean moved = false;

	public Piece(ChessEngine game, Color color, int x, int y) {
		this.engine = game;
		this.color = color;
		this.x = x;
		this.y = y;
	}

	public abstract void createPossibleMoves();

	public List<Square> getPossibleMoves() {
		createPossibleMoves();
		return possibleMoves;
	}

	public boolean canMove(int x, int y) {
		Square ms = engine.getSquare(x, y);

		createPossibleMoves();
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
		if (getColor() == Color.WHITE && p.getColor() == Color.BLACK)
			return true;
		if (getColor() == Color.BLACK && p.getColor() == Color.WHITE)
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

	public int getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}

	public void setMoved(boolean b) {
		moved = true;
	}

	public boolean getMoved() {
		return moved;
	}

}
