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
	private static final String KING_DEFAULT = "1000";
	private static final String QUEEN_DEFAULT = "10";
	private static final String PAWN_DEFAULT = "1";
	private static final String ROOK_DEFAULT = "5";
	private static final String KNIGHT_DEFAULT = "3";
	private static final String BISHOP_DEFAULT = "3";

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
	private JTextField historyField = new JTextField();

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
