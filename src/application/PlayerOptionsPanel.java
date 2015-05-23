package application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import uci.AdapterPool;
import uci.WindowsUCIAdapter;
import chess.engine.ChessColor;
import chess.engine.ChessOptions;
import chess.player.AIPlayer;
import chess.player.AnnealingPlayer;
import chess.player.AntPlayer;
import chess.player.GeneticPlayer;
import chess.player.HumanPlayer;
import chess.player.Player;

@SuppressWarnings("serial")
public class PlayerOptionsPanel extends JPanel {
	private static final String BLACK_LABEL = "Black: ";
	private static final String WHITE_LABEL = "White: ";
	private static final String AI_LABEL = "AI";
	private static final String HUMAN_LABEL = "Human";
	private static final String ANT_LABEL = "Ant Colony";
	private static final String GENETIC_LABEL = "Genetic";
	private static final String ANNEALING_LABEL = "Simulated Annealing";
	private static final String CHOOSE_FILE_LABEL = "File";

	private static final JFileChooser fileChooser = new JFileChooser();

	private ChessOptions options;

	public PlayerOptionsPanel(ChessOptions options) {
		this.options = options;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		options.setPlayerBlack(HumanPlayer.class);
		options.setPlayerWhite(HumanPlayer.class);
		addPlayer(ChessColor.BLACK);
		add(Box.createRigidArea(new Dimension(0, 50)));
		addPlayer(ChessColor.WHITE);
		AdapterPool adapterPool = new AdapterPool();
		adapterPool.addAdapter(new WindowsUCIAdapter());
		adapterPool.addAdapter(new WindowsUCIAdapter());
		options.setAdapterPool(adapterPool);
	}

	private void addPlayer(ChessColor color) {
		if (color == ChessColor.BLACK) {
			JLabel playerLabel = new JLabel(BLACK_LABEL);
			add(playerLabel);
		} else {
			JLabel playerLabel = new JLabel(WHITE_LABEL);
			add(playerLabel);
		}

		JRadioButton ai = new JRadioButton(AI_LABEL);
		ai.addActionListener(new SelectionActionListener(color, AIPlayer.class));
		add(ai);

		JRadioButton genetic = new JRadioButton(GENETIC_LABEL);
		genetic.addActionListener(new SelectionActionListener(color, GeneticPlayer.class));
		add(genetic);

		JRadioButton ant = new JRadioButton(ANT_LABEL);
		ant.addActionListener(new SelectionActionListener(color, AntPlayer.class));
		add(ant);

		JRadioButton annealing = new JRadioButton(ANNEALING_LABEL);
		annealing.addActionListener(new SelectionActionListener(color, AnnealingPlayer.class));
		add(annealing);

		JRadioButton player = new JRadioButton(HUMAN_LABEL);
		player.addActionListener(new SelectionActionListener(color, HumanPlayer.class));
		player.setSelected(true);
		add(player);

		JButton fileButton = new JButton(CHOOSE_FILE_LABEL);
		fileButton.addActionListener(new FileChooserListener(color));
		add(fileButton);

		ButtonGroup group = new ButtonGroup();
		group.add(ai);
		group.add(genetic);
		group.add(ant);
		group.add(annealing);
		group.add(player);
	}

	private class SelectionActionListener implements ActionListener {
		private final Class<? extends Player> clazz;
		private final ChessColor color;

		public SelectionActionListener(final ChessColor color, final Class<? extends Player> clazz) {
			this.color = color;
			this.clazz = clazz;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			if (color == ChessColor.WHITE) {
				options.setPlayerWhite(clazz);
			} else {
				options.setPlayerBlack(clazz);
			}
		}
	}

	private class FileChooserListener implements ActionListener {
		private final ChessColor color;

		public FileChooserListener(ChessColor color) {
			this.color = color;
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			int returnVal = fileChooser.showOpenDialog(PlayerOptionsPanel.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (color == ChessColor.WHITE) {
					options.setMetaheuristicSolutionWhite(file);
				} else {
					options.setMetaheuristicSolutionBlack(file);
				}
			}
		}
	}
}
