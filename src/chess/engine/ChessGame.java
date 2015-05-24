package chess.engine;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import application.ChessBoardPanel;
import uci.AdapterPool;
import uci.MoveParser;
import chess.engine.ChessEngine.State;
import chess.pieces.Piece;
import chess.player.AIPlayer;
import chess.player.MetaheuristicPlayer;
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
	private int turnMax = 0;

	public ChessGame(ChessOptions options) {
		running = true;
		this.options = options;
		this.boardPanel = options.getBoard();
		if (null != boardPanel) {
			boardPanel.setGame(this);
		}
		whitePlayer = createPlayer(options, ChessColor.WHITE);
		blackPlayer = createPlayer(options, ChessColor.BLACK);

		state = State.INIT;
	}

	public void initGame() {
		setActive(null);
		whitePieces = new LinkedList<Piece>();
		blackPieces = new LinkedList<Piece>();

		board = new ChessBoard();

		ChessEngine.createPieces(this);
		currentTurn = ChessColor.WHITE;

		if (options.getMoveHistory() != null) {
			goToHistory(options.getMoveHistory());
		}

		turnNumber = 0;
		this.state = State.NORMAL;
	}

	private void goToHistory(String moveHistory) {
		for (String moveString : moveHistory.split(" ")) {
			if (!moveString.equals("")) {
				int[] move = new int[5];
				MoveParser.parse(moveString, move);
				ChessEngine.move(this, move);
				currentTurn = currentTurn.negate();
			}
		}
	}

	private Player createPlayer(ChessOptions options, ChessColor color) {
		try {
			Class<? extends Player> clazz;
			Player player;
			if (color == ChessColor.WHITE) {
				clazz = options.getPlayerWhite();
			} else {
				clazz = options.getPlayerBlack();
			}
			if (clazz == AIPlayer.class) {
				player = clazz.getConstructor(AdapterPool.class).newInstance(options.getAdapterPool());
			} else if (MetaheuristicPlayer.class.isAssignableFrom(clazz)) {
				if (color == ChessColor.WHITE) {
					player = clazz.getConstructor(File.class).newInstance(options.getMetaheuristicSolutionWhite());
				} else {
					player = clazz.getConstructor(File.class).newInstance(options.getMetaheuristicSolutionBlack());
				}
			} else {
				player = clazz.getConstructor().newInstance();
			}
			player.setColor(color);
			player.setGame(this);
			return player;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			System.out.println("GENERIC CLASS CONSTRUCTOR INVOCATION FAILED MISERABLY");
		}
		return null;
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
				currentTurn = currentTurn.negate();
			} else if (currentTurn.equals(ChessColor.BLACK)) {
				blackTurn();
				currentTurn = currentTurn.negate();
			}
			if (boardPanel != null) {
				boardPanel.repaint();
			}

			if (turnMax != 0 && turnNumber >= turnMax) {
				System.out.println("REACHED MAX NUMBER OF TURNS!");
				stop();
			}
			if (state == State.CHECKMATE) {
				System.out.println("CHECKMATE! " + winner + " WINS!");
				stop();
			} else if (state == State.PAT) {
				System.out.println("PAT! NOBODY WINS");
				stop();
			}
		}
	}

	private void whiteTurn() throws Exception {
		int[] move = whitePlayer.think();
		ChessEngine.move(this, move);
	}

	private void blackTurn() throws Exception {
		int[] move = blackPlayer.think();
		ChessEngine.move(this, move);
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

	public ChessBoard getBoard() {
		return board;
	}

	public ChessColor getWinner() {
		return winner;
	}

	public void setMaxTurns(int turnMax) {
		this.turnMax = turnMax;
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

}
