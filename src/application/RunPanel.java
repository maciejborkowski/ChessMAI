package application;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import chess.engine.ChessOptions;

@SuppressWarnings("serial")
public class RunPanel extends JPanel {
	private ChessOptions chessOptions;

	public ChessOptions getChessOptions() {
		return chessOptions;
	}

	public void setChessOptions(ChessOptions chessOptions) {
		this.chessOptions = chessOptions;
	}

	public RunPanel() {
		chessOptions = new ChessOptions();
		initialize();
		addChessBoard();
		addPlayerOptions();
		addRunOptions();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
	}

	private void addChessBoard() {
		ChessBoardPanel panel = new ChessBoardPanel(chessOptions);
		add(panel, BorderLayout.CENTER);
	}

	private void addPlayerOptions() {
		PlayerOptionsPanel panel = new PlayerOptionsPanel(chessOptions);
		add(panel, BorderLayout.LINE_END);
	}

	private void addRunOptions() {
		RunOptionsPanel panel = new RunOptionsPanel(chessOptions);
		add(panel, BorderLayout.PAGE_END);
	}

}
