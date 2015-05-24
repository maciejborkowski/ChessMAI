package application;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uci.AdapterPool;
import chess.engine.ChessOptions;
import metaheuristic.antsearch.AntColony;
import metaheuristic.antsearch.ColonyOptions;

@SuppressWarnings("serial")
public class AntPanel extends JPanel {
	private static final String TURNS_LABEL = "Number of turns";
	private static final String TURNS_DEFAULT = "10";
	private static final String DISSIPATION_DEFAULT = "0.0002";
	private static final String CONCURRENT_ANTS_LABEL = "Concurrent ants";
	private static final String CONCURRENT_ANTS_DEFAULT = "1";
	private static final String START_BUTTON = "Start";
	private static final String STOP_BUTTON = "Stop";
	private static final String HISTORY_LABEL = "Move history";
	private static final String DISSIPATION_LABEL = "Dissipation level";

	private JLabel turnsLabel;
	private JTextField turnsField;

	private JLabel concurrentAntsLabel;
	private JTextField concurrentAntsField;

	private JButton runButton;

	private AntColony antColony;
	private ColonyOptions colonyOptions;

	private JTextField historyField;
	private JTextField dissipationField;

	public AntPanel() {
		setLayout(new GridBagLayout());
		colonyOptions = new ColonyOptions();
		ChessOptions options = new ChessOptions();
		options.setAdapterPool(new AdapterPool());
		colonyOptions.setChessOptions(options);

		GridBagConstraints constraints = new GridBagConstraints();
		turnsLabel = new JLabel(TURNS_LABEL);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.2;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(turnsLabel, constraints);

		turnsField = new JTextField(TURNS_DEFAULT);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.2;
		constraints.gridx = 1;
		constraints.gridy = 0;
		turnsField.setPreferredSize(new Dimension(50, 20));
		add(turnsField, constraints);

		/*
		 * concurrentAntsLabel = new JLabel(CONCURRENT_ANTS_LABEL);
		 * constraints.gridx = 0; constraints.gridy = 2;
		 * add(concurrentAntsLabel, constraints);
		 * 
		 * concurrentAntsField = new JTextField(CONCURRENT_ANTS_DEFAULT);
		 * constraints.gridx = 0; constraints.gridy = 3; //
		 * concurrentAntsField.setPreferredSize(new Dimension(50, 20));
		 * add(concurrentAntsField, constraints);
		 */

		JLabel dissipationLabel = new JLabel(DISSIPATION_LABEL);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.1;
		constraints.gridx = 0;
		constraints.gridy = 4;
		add(dissipationLabel, constraints);

		dissipationField = new JTextField(DISSIPATION_DEFAULT);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.9;
		constraints.gridx = 1;
		constraints.gridy = 4;
		add(dissipationField, constraints);

		JLabel historyLabel = new JLabel(HISTORY_LABEL);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.1;
		constraints.gridx = 0;
		constraints.gridy = 5;
		add(historyLabel, constraints);

		historyField = new JTextField("");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.9;
		constraints.gridx = 1;
		constraints.gridy = 5;
		add(historyField, constraints);

		runButton = new JButton(START_BUTTON);
		runButton.addActionListener(new RunListener());
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.2;
		constraints.gridx = 1;
		constraints.gridy = 6;
		add(runButton, constraints);
	}

	private final class RunListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (runButton.getText() == START_BUTTON) {
				colonyOptions.setMaxLength(Integer.parseInt(turnsField.getText()));
				colonyOptions.getChessOptions().setMoveHistory(historyField.getText());
				colonyOptions.setDissipation(Double.parseDouble(dissipationField.getText()));
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
