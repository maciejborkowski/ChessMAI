package framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import chess.ChessGame;

@SuppressWarnings("serial")
public class RunOptionsPanel extends JPanel {
	private static final String RUN_LABEL = "Run";

	private ChessOptions options;

	public RunOptionsPanel(ChessOptions options) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.options = options;

		JButton button = new JButton(RUN_LABEL);
		button.addActionListener(new RunActionListener());
		add(button);
	}

	private class RunActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ChessGame game = new ChessGame(options.getWhite(), options.getBlack(), options.getBoard());
			game.start();
		}
	}
}
