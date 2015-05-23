package chess.engine;

import java.util.HashMap;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessBoard {
	private static final HashMap<Class<? extends Piece>, Character> values = new HashMap<>();

	static {
		values.put(Bishop.class, 'b');
		values.put(King.class, 'k');
		values.put(Knight.class, 'n');
		values.put(Pawn.class, 'p');
		values.put(Queen.class, 'q');
		values.put(Rook.class, 'r');
	}
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

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < ChessEngine.SQUARE_WIDTH; i++) {
			for (int j = 0; j < ChessEngine.SQUARE_HEIGHT; j++) {
				if (board[j][i].getPiece() == null) {
					builder.append('#');
				} else {
					builder.append(values.get(board[j][i].getPiece().getClass()));
				}
			}
		}
		return builder.toString();
	}
}
