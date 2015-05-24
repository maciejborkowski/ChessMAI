package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import chess.engine.ChessGame;
import chess.engine.ChessOptions;

@SuppressWarnings("serial")
public class RunOptionsPanel extends JPanel {
	private static final String RUN_LABEL = "Run";
	private static final String HISTORY_LABEL = "Move history";

	private ChessOptions options;
	private ChessGame game = null;
	private JTextField historyField;

	public RunOptionsPanel(ChessOptions options) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.options = options;

		JLabel historyLabel = new JLabel(HISTORY_LABEL);
		add(historyLabel);
		historyField = new JTextField("");
		add(historyField);

		JButton button = new JButton(RUN_LABEL);
		button.addActionListener(new RunActionListener());
		add(button);
	}

	private class RunActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (game != null && game.isRunning()) {
				game.stop();
			}

			options.setMoveHistory(historyField.getText());
			game = new ChessGame(options);
			Thread gameThread = new Thread(game);
			gameThread.start();
		}
	}
}
