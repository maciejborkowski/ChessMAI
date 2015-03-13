package chess;

import javax.swing.*;

@SuppressWarnings("serial")
public class ChessGame extends JFrame implements Runnable {
	static enum State {
		INITIALIZATION, CHECKMATE, WHITE, BLACK
	}

	public static final int UPDATE_RATE = 10; // updates per second
	public static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE; // in
																		// nanoseconds
	private State state;
	private ChessEngine engine;
	private Player whitePlayer, blackPlayer;
	private Thread gameThread = null;

	public ChessGame(final Player white, final Player black, final ChessEngine engine) {
		this.whitePlayer = white;
		this.blackPlayer = black;
		this.engine = engine;
		state = State.INITIALIZATION;
		this.gameThread = new Thread(this);
	}

	public void start() {
		gameThread.start();
	}

	@Override
	public void run() {
		gameLoop();
	}

	private void gameLoop() {
		long beginTime, timeTaken, timeLeft;
		while (true) {
			beginTime = System.nanoTime();
			if (state == State.CHECKMATE) {
				System.out.println("CHECKMATE");
			} else if (state == State.INITIALIZATION) {
				gameInit();
			} else if (state == State.WHITE) {
				whiteTurn();
				if (engine.getBoard() != null) {
					engine.getBoard().repaint();
				}
			} else if (state == State.BLACK) {
				blackTurn();
				if (engine.getBoard() != null) {
					engine.getBoard().repaint();
				}
			}

			timeTaken = System.nanoTime() - beginTime;
			timeLeft = (UPDATE_PERIOD - timeTaken) / 1000000;
			if (timeLeft < 10)
				timeLeft = 10;
			try {
				Thread.sleep(timeLeft);
			} catch (InterruptedException e) {
			}
		}
	}

	public void gameInit() {
		engine.initGame();

		whitePlayer.setColor(Color.WHITE);
		whitePlayer.setBoard(engine.getBoard());
		whitePlayer.setEngine(engine);

		blackPlayer.setColor(Color.BLACK);
		blackPlayer.setBoard(engine.getBoard());
		blackPlayer.setEngine(engine);

		this.state = State.WHITE;
	}

	private void whiteTurn() {
		whitePlayer.think();
		int[] move = whitePlayer.getMove();
		if (move != null) {
			engine.move(move);
		}
		state = State.BLACK;
	}

	private void blackTurn() {
		blackPlayer.think();
		int[] move = blackPlayer.getMove();
		if (move != null) {
			engine.move(move);
		}
		state = State.WHITE;
	}

	public State getGameState() {
		return state;
	}

	public ChessEngine getEngine() {
		return engine;
	}
}
