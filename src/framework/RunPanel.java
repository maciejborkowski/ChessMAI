package framework;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import chess.ChessOptions;

@SuppressWarnings("serial")
public class RunPanel extends JPanel {
	private ChessOptions options;

	public ChessOptions getOptions() {
		return options;
	}

	public void setOptions(ChessOptions options) {
		this.options = options;
	}

	public RunPanel() {
		options = new ChessOptions();
		initialize();
		addChessBoard();
		addPlayerOptions();
		addRunOptions();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
	}

	private void addChessBoard() {
		ChessBoardPanel panel = new ChessBoardPanel(options);
		add(panel, BorderLayout.CENTER);

	}

	private void addPlayerOptions() {
		PlayerOptionsPanel panel = new PlayerOptionsPanel(options);
		add(panel, BorderLayout.LINE_END);
	}

	private void addRunOptions() {
		RunOptionsPanel panel = new RunOptionsPanel(options);
		add(panel, BorderLayout.PAGE_END);
	}

}
