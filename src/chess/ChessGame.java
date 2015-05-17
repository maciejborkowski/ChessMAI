package chess;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import uci.AdapterPool;
import uci.UCIAdapter;
import chess.ChessEngine.State;
import chess.pieces.King;
import chess.pieces.Piece;

public class ChessGame implements Runnable {
	private boolean running;
	private ChessEngine.State state;
	private Player whitePlayer, blackPlayer;
	private Thread gameThread = null;
	private ChessOptions options;
	private ChessBoard board;

	private Square[][] squares;
	private List<Piece> whitePieces = new LinkedList<>();
	private List<Piece> blackPieces = new LinkedList<>();
	private Square active;
	private ChessColor currentTurn;
	private StringBuilder moveHistory = new StringBuilder();
	private ChessColor winner;

	@Deprecated
	public ChessGame(final Player white, final Player black, ChessBoard board) {
		running = true;
		whitePlayer = white;
		blackPlayer = black;
		this.board = board;
		board.setGame(this);

		state = State.INIT;
		this.gameThread = new Thread(this);
	}

	public ChessGame(ChessOptions options) {
		running = true;
		this.options = options;
		whitePlayer = createPlayer(options.getWhitePlayer());
		blackPlayer = createPlayer(options.getBlackPlayer());
		this.board = options.getBoard();
		board.setGame(this);

		state = State.INIT;
		this.gameThread = new Thread(this);
	}

	private Player createPlayer(Class<? extends Player> clazz) {
		try {
			Player player;
			if (clazz == AIPlayer.class) {
				player = clazz.getConstructor(AdapterPool.class).newInstance(options.getAdapterPool());
			} else {
				player = clazz.getConstructor().newInstance();
			}
			return player;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			System.out.println("GENERIC CLASS CONSTRUCTOR INVOCATION FAILED MISERABLY");
		}
		return null;
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

	public ChessColor getTurn() {
		return currentTurn;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
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
		currentTurn = ChessColor.WHITE;

		whitePlayer.setColor(ChessColor.WHITE);
		whitePlayer.setGame(this);

		blackPlayer.setColor(ChessColor.BLACK);
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
			System.out.println("ERROR: " + e.toString());
			e.printStackTrace();
		}
	}

	private void gameLoop() throws Exception {
		while (running) {
			if (state == State.INIT) {
				initGame();
			} else if (currentTurn.equals(ChessColor.WHITE)) {
				whiteTurn();
			} else if (currentTurn.equals(ChessColor.BLACK)) {
				blackTurn();
			}
			if (board != null) {
				board.repaint();
			}

			if (state == State.CHECKMATE) {
				System.out.println("CHECKMATE! " + winner + " WINS!");
				setRunning(false);
			}
		}
	}

	private void whiteTurn() throws Exception {
		int[] move = whitePlayer.think();
		ChessEngine.move(this, move);
		currentTurn = ChessColor.BLACK;
	}

	private void blackTurn() throws Exception {
		int[] move = blackPlayer.think();
		ChessEngine.move(this, move);
		currentTurn = ChessColor.WHITE;
	}

	public void setWinner(ChessColor color) {
		winner = color;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return running;
	}
}
