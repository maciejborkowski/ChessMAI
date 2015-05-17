package chess.engine;

public class ChessBoard {
	private Square[][] board;

	public ChessBoard() {
		board = new Square[ChessEngine.SQUARE_WIDTH][ChessEngine.SQUARE_HEIGHT];
		for (int i = 0; i < ChessEngine.SQUARE_WIDTH; i++) {
			for (int j = 0; j < ChessEngine.SQUARE_HEIGHT; j++) {
				board[i][j] = new Square(i, j);
			}
		}
	}

	public Square[][] getBoard() {
		return board;
	}

	public void setBoard(Square[][] board) {
		this.board = board;
	}

	public Square getSquare(int x, int y) {
		return board[x][y];
	}

	public void setSquare(int x, int y, Square square) {
		this.board[x][y] = square;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ChessBoard other = (ChessBoard) obj;

		for (int i = 0; i < ChessEngine.SQUARE_WIDTH; i++) {
			for (int j = 0; j < ChessEngine.SQUARE_HEIGHT; j++) {
				if (!board[i][j].equals(other.board[i][j])) {
					return false;
				}
			}
		}
		return true;
	}
}
