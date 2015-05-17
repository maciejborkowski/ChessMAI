package chess.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import application.ChessBoardPanel;
import uci.AdapterPool;
import chess.engine.ChessEngine.State;
import chess.pieces.Piece;
import chess.player.AIPlayer;
import chess.player.Player;

public class ChessGame implements Runnable {
	private boolean running;
	private ChessEngine.State state;
	private Player whitePlayer, blackPlayer;

	private ChessOptions options;
	private ChessBoardPanel boardPanel;

	private ChessBoard board;
	private List<Piece> whitePieces = new LinkedList<>();
	private List<Piece> blackPieces = new LinkedList<>();
	private Square active;
	private ChessColor currentTurn;
	private StringBuilder moveHistory = new StringBuilder();
	private ChessColor winner;
	private int turnNumber;

	public ChessGame(ChessOptions options) {
		running = true;
		this.options = options;
		whitePlayer = createPlayer(options.getWhitePlayer());
		blackPlayer = createPlayer(options.getBlackPlayer());
		this.boardPanel = options.getBoard();
		if (null != boardPanel) {
			boardPanel.setGame(this);
		}

		state = State.INIT;
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

	public ChessBoardPanel getBoardPanel() {
		return boardPanel;
	}

	public void setBoardPanel(ChessBoardPanel board) {
		this.boardPanel = board;
	}

	public Square getActive() {
		return active;
	}

	public void setActive(Square active) {
		this.active = active;
	}

	public void setActive(int x, int y) {
		this.active = board.getSquare(x, y);

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

		board = new ChessBoard();

		ChessEngine.createPieces(this);
		currentTurn = ChessColor.WHITE;

		whitePlayer.setColor(ChessColor.WHITE);
		whitePlayer.setGame(this);

		blackPlayer.setColor(ChessColor.BLACK);
		blackPlayer.setGame(this);

		turnNumber = 0;
		this.state = State.NORMAL;
	}

	public Square getSquare(int x, int y) {
		if (x >= 0 && x < ChessEngine.SQUARE_WIDTH && y >= 0 && y < ChessEngine.SQUARE_HEIGHT)
			return board.getSquare(x, y);
		return null;
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
				turnNumber++;
				whiteTurn();
			} else if (currentTurn.equals(ChessColor.BLACK)) {
				blackTurn();
			}
			if (boardPanel != null) {
				boardPanel.repaint();
			}

			if (options.getMaxLength() != 0 && turnNumber >= options.getMaxLength()) {
				System.out.println("REACHED MAX NUMBER OF TURNS!");
				stop();
			}
			if (state == State.CHECKMATE) {
				System.out.println("CHECKMATE! " + winner + " WINS!");
				stop();
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

	public boolean isRunning() {
		return running;
	}

	public void stop() {
		running = false;
	}

	public Player getBlackPlayer() {
		return blackPlayer;
	}

	public void setBlackPlayer(Player blackPlayer) {
		this.blackPlayer = blackPlayer;
	}

	public Player getWhitePlayer() {
		return whitePlayer;
	}

	public void setWhitePlayer(Player whitePlayer) {
		this.whitePlayer = whitePlayer;
	}

}
