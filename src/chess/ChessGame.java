package chess;

import java.util.LinkedList;
import java.util.List;

import chess.pieces.Piece;

public class ChessGame implements Runnable {
	static enum State {
		INITIALIZATION, CHECKMATE, NORMAL
	}

	private State state;
	private Player whitePlayer, blackPlayer;
	private Thread gameThread = null;

	private Square[][] squares;
	private List<Piece> whitePieces = new LinkedList<>();
	private List<Piece> blackPieces = new LinkedList<>();
	private Square active;
	private Color currentTurn;
	private ChessBoard board;
	private StringBuilder moveHistory = new StringBuilder();

	public ChessGame(final Player white, final Player black, ChessBoard board) {
		this.whitePlayer = white;
		this.blackPlayer = black;
		this.board = board;
		board.setGame(this);

		state = State.INITIALIZATION;
		this.gameThread = new Thread(this);
	}

	public ChessBoard getBoard() {
		return board;
	}

	public void setBoard(ChessBoard board) {
		this.board = board;
	}

	public Square getActive() {
		return active;
	}

	public void setActive(Square active) {
		this.active = active;
	}

	public void setActive(int x, int y) {
		this.active = squares[x][y];

	}

	public List<Piece> getWhitePieces() {
		return whitePieces;
	}

	public List<Piece> getBlackPieces() {
		return blackPieces;
	}

	public Color getTurn() {
		return currentTurn;
	}

	public State getGameState() {
		return state;
	}

	public StringBuilder getMoveHistory() {
		return moveHistory;
	}

	public void initGame() {
		setActive(null);
		whitePieces = new LinkedList<Piece>();
		blackPieces = new LinkedList<Piece>();
		squares = new Square[ChessEngine.SQUARE_WIDTH][ChessEngine.SQUARE_HEIGHT];
		for (int i = 0; i < ChessEngine.SQUARE_WIDTH; i++) {
			for (int j = 0; j < ChessEngine.SQUARE_HEIGHT; j++) {
				squares[i][j] = new Square(i, j);
			}
		}
		ChessEngine.createPieces(this);
		currentTurn = Color.WHITE;

		whitePlayer.setColor(Color.WHITE);
		whitePlayer.setGame(this);

		blackPlayer.setColor(Color.BLACK);
		blackPlayer.setGame(this);

		this.state = State.NORMAL;
	}

	public Square getSquare(int x, int y) {
		if (x >= 0 && x < ChessEngine.SQUARE_WIDTH && y >= 0 && y < ChessEngine.SQUARE_HEIGHT)
			return squares[x][y];
		return null;
	}

	public void start() {
		gameThread.start();
	}

	@Override
	public void run() {
		try {
			gameLoop();
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
			e.printStackTrace();
		}
	}

	private void gameLoop() throws Exception {
		while (true) {
			if (state == State.CHECKMATE) {
				System.out.println("CHECKMATE");
			} else if (state == State.INITIALIZATION) {
				initGame();
			} else if (state == State.NORMAL && currentTurn.equals(Color.WHITE)) {
				whiteTurn();
			} else if (state == State.NORMAL && currentTurn.equals(Color.BLACK)) {
				blackTurn();
			}
			if (board != null) {
				board.repaint();
			}
		}
	}

	private void whiteTurn() throws Exception {
		int[] move = whitePlayer.think();
		ChessEngine.move(this, move);
		state = State.NORMAL;
		currentTurn = Color.BLACK;
	}

	private void blackTurn() throws Exception {
		int[] move = blackPlayer.think();
		ChessEngine.move(this, move);
		state = State.NORMAL;
		currentTurn = Color.WHITE;
	}

	public void cleanSquare(Square square) {
		Piece piece = square.getPiece();
		if (piece != null) {
			if (piece.getColor() == Color.WHITE) {
				whitePieces.remove(piece);
			} else {
				blackPieces.remove(piece);
			}
			square.setPiece(null);
		}
	}

}
