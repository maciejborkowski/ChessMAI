package framework;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import chess.AIPlayer;
import chess.Color;
import chess.HumanPlayer;
import chess.Player;

@SuppressWarnings("serial")
public class PlayerOptionsPanel extends JPanel {
	private static final String BLACK_LABEL = "Black: ";
	private static final String WHITE_LABEL = "White: ";
	private static final String AI_LABEL = "AI";
	private static final String METAHEURESTIC_LABEL = "Metaheuristic";
	private static final String HUMAN_LABEL = "Human";

	private ChessOptions options;

	public PlayerOptionsPanel(ChessOptions options) {
		this.options = options;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		options.setBlack(new HumanPlayer());
		options.setWhite(new HumanPlayer());
		addBlack();
		add(Box.createRigidArea(new Dimension(0, 50)));
		addWhite();
	}

	private void addBlack() {
		JLabel playerLabel = new JLabel(BLACK_LABEL);
		add(playerLabel);
		JRadioButton ai = new JRadioButton(AI_LABEL);
		ai.addActionListener(new SelectionActionListener(Color.BLACK, AIPlayer.class));
		add(ai);

		JRadioButton metaheurestic = new JRadioButton(METAHEURESTIC_LABEL);
		add(metaheurestic);

		JRadioButton player = new JRadioButton(HUMAN_LABEL);
		player.addActionListener(new SelectionActionListener(Color.BLACK, HumanPlayer.class));
		player.setSelected(true);
		add(player);

		ButtonGroup group = new ButtonGroup();
		group.add(ai);
		group.add(metaheurestic);
		group.add(player);
	}

	private void addWhite() {
		JLabel playerLabel = new JLabel(WHITE_LABEL);
		add(playerLabel);
		JRadioButton ai = new JRadioButton(AI_LABEL);
		ai.addActionListener(new SelectionActionListener(Color.WHITE, AIPlayer.class));
		add(ai);

		JRadioButton metaheurestic = new JRadioButton(METAHEURESTIC_LABEL);
		add(metaheurestic);

		JRadioButton player = new JRadioButton(HUMAN_LABEL);
		player.addActionListener(new SelectionActionListener(Color.WHITE, HumanPlayer.class));
		player.setSelected(true);
		add(player);

		ButtonGroup group = new ButtonGroup();
		group.add(ai);
		group.add(metaheurestic);
		group.add(player);
	}

	private class SelectionActionListener implements ActionListener {
		private final Class<? extends Player> clazz;
		private final Color color;

		public SelectionActionListener(final Color color, final Class<? extends Player> clazz) {
			this.color = color;
			this.clazz = clazz;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				if (color.equals(Color.WHITE)) {
					options.setWhite(clazz.getConstructor().newInstance());
				} else {
					options.setBlack(clazz.getConstructor().newInstance());
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				System.out.println("GENERIC CLASS CONSTRUCTOR INVOCATION FAILED MISERABLY");
			}
		}
	}
}
