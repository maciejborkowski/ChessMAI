package application;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
	private HistoryContainerPanel historyContainer;

	public RunOptionsPanel(ChessOptions options) {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		this.options = options;
		/*
		 * constraints.weightx = 0.5; constraints.gridx = 0; constraints.gridy =
		 * 0; JLabel historyLabel = new JLabel(HISTORY_LABEL);
		 * add(historyLabel);
		 */
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.gridy = 0;
		historyField = new JTextField(200);
		add(historyField, constraints);

		constraints.weightx = 0.1;
		constraints.gridx = 0;
		constraints.gridwidth = 1;
		constraints.gridy = 1;
		JButton button = new JButton(RUN_LABEL);
		button.addActionListener(new RunActionListener());
		add(button, constraints);

		constraints.weightx = 0.5;
		constraints.gridx = 1;
		constraints.gridheight = 2;
		constraints.gridy = 1;
		historyContainer = new HistoryContainerPanel();
		add(historyContainer, constraints);

	}

	private class RunActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (game != null && game.isRunning()) {
				game.stop();
			}

			options.setInitialMoveHistory(historyField.getText());
			options.setMoveHistoryContainer(historyContainer);
			game = new ChessGame(options);
			Thread gameThread = new Thread(game);
			gameThread.start();
		}
	}
}
