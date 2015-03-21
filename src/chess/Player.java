package chess;

public abstract class Player {
	protected Color color;
	protected ChessBoard board;
	protected ChessEngine engine;
	
	public ChessBoard getBoard() {
		return board;
	}
	
	public void setBoard(ChessBoard board) {
		this.board = board;
	}
	
	public ChessEngine getEngine() {
		return engine;
	}
	
	public void setEngine(ChessEngine engine) {
		this.engine = engine;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public abstract int[] think();
}
