package chess;

public abstract class Player {
	protected Color color;
	protected ChessGame game;

	public ChessGame getGame() {
		return game;
	}

	public void setGame(ChessGame game) {
		this.game = game;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public abstract int[] think() throws Exception;
}
