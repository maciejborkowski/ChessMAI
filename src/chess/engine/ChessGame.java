package chess.engine;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

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

	private ChessBoard board;
	private List<Piece> whitePieces = new LinkedList<>();
	private List<Piece> blackPieces = new LinkedList<>();
	private Square active;
	private ChessColor currentTurn;
	private StringBuilder moveHistory;
	private ChessColor winner;
	private int turnNumber;
	private int turnMax = 0;

	public ChessGame(ChessOptions options) {
		running = true;
		this.options = options;
		if (null != options.getBoard()) {
			options.getBoard().setGame(this);
		}

		whitePlayer = createPlayer(options, ChessColor.WHITE);
		blackPlayer = createPlayer(options, ChessColor.BLACK);

		initGame();
	}

	public void initGame() {
		setActive(null);
		whitePieces = new LinkedList<Piece>();
		blackPieces = new LinkedList<Piece>();

		board = new ChessBoard();

		ChessEngine.createPieces(this);
		currentTurn = ChessColor.WHITE;
		state = State.NORMAL;

		moveHistory = new StringBuilder();
		if (options.getInitialMoveHistory() != null) {
			goToHistory(options.getInitialMoveHistory());
		}

		turnNumber = 0;
		updateGUI();
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

	@Override
	public void run() {
		try {
			gameLoop();
		} catch (Exception e) {
			System.out.println("ERROR: " + e.toString());
			e.printStackTrace();
		}
	}

	private void gameLoop() {
		while (running) {
			makeMove();
			updateGUI();
			checkEndgameConditions();
		}
	}

	private void makeMove() {
		try {
			int[] move = null;
			if (currentTurn.equals(ChessColor.WHITE)) {
				turnNumber++;
				move = whitePlayer.think();
			} else if (currentTurn.equals(ChessColor.BLACK)) {
				move = blackPlayer.think();
			}
			ChessEngine.move(this, move);
			currentTurn = currentTurn.negate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateGUI() {
		if (options.getBoard() != null) {
			options.getBoard().repaint();
		}
		if (options.getMoveHistoryContainer() != null) {
			options.getMoveHistoryContainer().rewrite(moveHistory.toString());
		}
	}

	private void checkEndgameConditions() {
		if (turnMax != 0 && turnNumber >= turnMax) {
			System.out.println("REACHED MAX NUMBER OF TURNS!");
			stop();
		}
		if (state == State.CHECKMATE) {
			System.out.println("CHECKMATE! " + winner + " WINS!");
			stop();
		}
		if (state == State.PAT) {
			System.out.println("PAT! NOBODY WINS");
			stop();
		}
	}

	public void reset() {
		winner = null;
		running = true;
		initGame();
	}

	public Square getSquare(int x, int y) {
		if (x >= 0 && x < ChessEngine.SQUARE_WIDTH && y >= 0 && y < ChessEngine.SQUARE_HEIGHT)
			return board.getSquare(x, y);
		return null;
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

	public ChessOptions getOptions() {
		return options;
	}

}
