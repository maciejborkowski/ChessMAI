package chess;

import javax.swing.*;

@SuppressWarnings("serial")
public class ChessGame extends JFrame implements Runnable {
	static enum State {
		INITIALIZATION, CHECKMATE, WHITE, BLACK
	}

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
		while (true) {
			if (state == State.CHECKMATE) {
				System.out.println("CHECKMATE");
			} else if (state == State.INITIALIZATION) {
				gameInit();
			} else if (state == State.WHITE) {
				whiteTurn();
			} else if (state == State.BLACK) {
				blackTurn();
			}
			if (engine.getBoard() != null) {
				engine.getBoard().repaint();
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
		int[] move = whitePlayer.think();
		engine.move(move);
		state = State.BLACK;
	}

	private void blackTurn() {
		int[] move = blackPlayer.think();
		engine.move(move);
		state = State.WHITE;
	}

	public State getGameState() {
		return state;
	}

	public ChessEngine getEngine() {
		return engine;
	}
}
