package chess;

import chess.pieces.Piece;

public class Square {
	private final int x, y;
	private boolean active;
	private Piece piece;
	
	public Square(int x, int y) {
		setPiece(null);
		this.x = x;
		this.y = y;
		this.setActive(false);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
}
