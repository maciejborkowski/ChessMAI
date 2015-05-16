package chess;

public abstract class Player {
	protected ChessColor color;
	protected ChessGame game;

	public ChessGame getGame() {
		return game;
	}

	public void setGame(ChessGame game) {
		this.game = game;
	}

	public ChessColor getColor() {
		return color;
	}

	public void setColor(ChessColor color) {
		this.color = color;
	}

	public abstract int[] think() throws Exception;
}
