package framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import chess.ChessGame;
import chess.Player;

@SuppressWarnings("serial")
public class RunOptionsPanel extends JPanel {
	private static final String RUN_LABEL = "Run";

	private ChessOptions options;
	private ChessGame game = null;

	public RunOptionsPanel(ChessOptions options) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.options = options;

		JButton button = new JButton(RUN_LABEL);
		button.addActionListener(new RunActionListener());
		add(button);
	}

	private class RunActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (game != null && game.isRunning()) {
				game.setRunning(false);
			}
			try {
				Player white = options.getWhitePlayer().getConstructor().newInstance();
				Player black = options.getBlackPlayer().getConstructor().newInstance();

				game = new ChessGame(white, black, options.getBoard());
				game.start();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				System.out.println("GENERIC CLASS CONSTRUCTOR INVOCATION FAILED MISERABLY");
			}
		}
	}
}
