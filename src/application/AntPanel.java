package application;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uci.AdapterPool;
import chess.engine.ChessOptions;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;
import metaheuristic.antsearch.AntColony;
import metaheuristic.antsearch.ColonyOptions;

@SuppressWarnings("serial")
public class AntPanel extends JPanel {
	private static final String TURNS_LABEL = "Number of turns";
	private static final String CONCURRENT_ANTS_LABEL = "Concurrent ants";
	private static final String DISSIPATION_LABEL = "Dissipation level";
	private static final String KING_LABEL = "King weight";
	private static final String QUEEN_LABEL = "Queen weight";
	private static final String PAWN_LABEL = "Pawn weight";
	private static final String ROOK_LABEL = "Rook weight";
	private static final String KNIGHT_LABEL = "Knight weight";
	private static final String BISHOP_LABEL = "Bishop weight";
	private static final String HISTORY_LABEL = "Move history";

	private static final String TURNS_DEFAULT = "10";
	private static final String CONCURRENT_ANTS_DEFAULT = "4";
	private static final String DISSIPATION_DEFAULT = "0.1";
	private static final String KING_DEFAULT = "100";
	private static final String QUEEN_DEFAULT = "10";
	private static final String PAWN_DEFAULT = "1";
	private static final String ROOK_DEFAULT = "5";
	private static final String KNIGHT_DEFAULT = "3";
	private static final String BISHOP_DEFAULT = "3";
	private static final String HISTORY_DEFAULT = "g1f3  g8f6  b1c3  b8c6  d2d4  d7d5  c1f4  c8f5  e2e3  e7e6  f1d3  f8d6  e1g1  e8g8  d3f5  e6f5  d1d3  d6f4  e3f4  d8d7  f3e5  d7e6  c3b5  f8c8  e5f3  f6e4  d3e3  a7a6  b5c3  c6b4  f1c1  c8e8  a2a3  b4c6  c1e1  f7f6  f3d2  e6d6  g2g3  d6d7  d2e4  f5e4  a1b1  c6e7  e3e2  e7f5  b1d1  c7c6  c3a4  d7c7  a4c5  e4e3  e2d3  e3f2  g1f2  f5d6  f2g2  f6f5  d3b3  b7b6  c5d3  d6e4  c2c4  a8d8  d3e5  e8e6  c4d5  c6d5  g3g4  d8c8  b3d5  c7d6  d5b3  g7g6  g4f5  g6f5  e1e4  d6e7  e4e3  b6b5  b3d3  e6f6  d4d5  g8h8  e3g3  e7d6  d1e1  b5b4  a3a4  a6a5  h2h4  d6c7  d5d6  c7b7  g2h3  c8f8  d6d7  b7a8  d3c4  a8d8  c4d5  d8c7  e5f7  f8f7";

	private static final String START_BUTTON = "Start";
	private static final String STOP_BUTTON = "Stop";

	private JTextField turnsField = new JTextField(TURNS_DEFAULT);
	private JTextField concurrentAntsField = new JTextField(CONCURRENT_ANTS_DEFAULT);
	private JTextField dissipationField = new JTextField(DISSIPATION_DEFAULT);
	private JTextField kingField = new JTextField(KING_DEFAULT);
	private JTextField queenField = new JTextField(QUEEN_DEFAULT);
	private JTextField pawnField = new JTextField(PAWN_DEFAULT);
	private JTextField rookField = new JTextField(ROOK_DEFAULT);
	private JTextField knightField = new JTextField(KNIGHT_DEFAULT);
	private JTextField bishopField = new JTextField(BISHOP_DEFAULT);
	private JTextField historyField = new JTextField(HISTORY_DEFAULT);

	private JButton runButton;

	private AntColony antColony;
	private ColonyOptions colonyOptions;

	public AntPanel() {
		setLayout(new GridBagLayout());
		colonyOptions = new ColonyOptions();
		ChessOptions options = new ChessOptions();
		options.setAdapterPool(new AdapterPool());
		colonyOptions.setChessOptions(options);

		GridBagConstraints constraints = new GridBagConstraints();
		addComponent(TURNS_LABEL, turnsField, constraints);
		addComponent(CONCURRENT_ANTS_LABEL, concurrentAntsField, constraints);
		addComponent(DISSIPATION_LABEL, dissipationField, constraints);
		addComponent(KING_LABEL, kingField, constraints);
		addComponent(QUEEN_LABEL, queenField, constraints);
		addComponent(PAWN_LABEL, pawnField, constraints);
		addComponent(ROOK_LABEL, rookField, constraints);
		addComponent(KNIGHT_LABEL, knightField, constraints);
		addComponent(BISHOP_LABEL, bishopField, constraints);
		addComponent(HISTORY_LABEL, historyField, constraints);

		runButton = new JButton(START_BUTTON);
		runButton.addActionListener(new RunListener());
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.2;
		constraints.gridx = 1;
		constraints.gridy++;
		add(runButton, constraints);
	}

	private void addComponent(String labelString, JTextField field, GridBagConstraints constraints) {
		JLabel label = new JLabel(labelString);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.1;
		constraints.gridx = 0;
		constraints.gridy++;
		add(label, constraints);

		constraints.weightx = 0.9;
		constraints.gridx = 1;
		add(field, constraints);
	}

	private final class RunListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (runButton.getText() == START_BUTTON) {
				colonyOptions.setMaxLength(Integer.parseInt(turnsField.getText()));
				colonyOptions.getChessOptions().setInitialMoveHistory(historyField.getText());
				colonyOptions.setDissipation(Double.parseDouble(dissipationField.getText()));
				colonyOptions.setConcurrentAnts(Integer.parseInt(concurrentAntsField.getText()));
				HashMap<Class<? extends Piece>, Double> pieceCostMap = new HashMap<>();
				pieceCostMap.put(King.class, Double.parseDouble(kingField.getText()));
				pieceCostMap.put(Queen.class, Double.parseDouble(queenField.getText()));
				pieceCostMap.put(Pawn.class, Double.parseDouble(pawnField.getText()));
				pieceCostMap.put(Rook.class, Double.parseDouble(rookField.getText()));
				pieceCostMap.put(Knight.class, Double.parseDouble(knightField.getText()));
				pieceCostMap.put(Bishop.class, Double.parseDouble(bishopField.getText()));
				colonyOptions.setPieceCostMap(pieceCostMap);
				antColony = new AntColony(colonyOptions);
				Thread antColonyThread = new Thread(antColony);
				antColonyThread.start();

				runButton.setText(STOP_BUTTON);
			} else {
				antColony.stop();
				runButton.setText(START_BUTTON);
			}
		}
	}

	public ColonyOptions getColonyOptions() {
		return colonyOptions;
	}

	public AntColony getAntColony() {
		return antColony;
	}

}
