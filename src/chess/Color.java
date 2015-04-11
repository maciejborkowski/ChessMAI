package chess;

public enum Color {
	WHITE, BLACK;

	public Color negate() {
		if (this.equals(BLACK)) {
			return WHITE;
		} else {
			return BLACK;
		}

	}
}
