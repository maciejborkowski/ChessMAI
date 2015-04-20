package chess;

public enum ChessColor {
	WHITE, BLACK;

	public ChessColor negate() {
		if (this.equals(BLACK)) {
			return WHITE;
		} else {
			return BLACK;
		}

	}
}
