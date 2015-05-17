package application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uci.AdapterPool;
import chess.engine.ChessOptions;
import metaheuristic.antsearch.AntColony;

@SuppressWarnings("serial")
public class AntPanel extends JPanel {
	private static final String TURNS_LABEL = "Number of turns";
	private static final String TURNS_DEFAULT = "3";
	private static final String CONCURRENT_ANTS_LABEL = "Concurrent ants";
	private static final String CONCURRENT_ANTS_DEFAULT = "1";
	private static final String START_BUTTON = "Start";
	private static final String STOP_BUTTON = "Stop";

	private JLabel turnsLabel;
	private JTextField turnsField;

	private JLabel concurrentAntsLabel;
	private JTextField concurrentAntsField;

	private JButton runButton;

	private AntColony antColony;
	private ChessOptions options;

	public AntPanel() {
		options = new ChessOptions();
		options.setAdapterPool(new AdapterPool());

		turnsLabel = new JLabel(TURNS_LABEL);
		add(turnsLabel);
		turnsField = new JTextField(TURNS_DEFAULT);
		turnsField.setPreferredSize(new Dimension(50, 20));
		add(turnsField);

		concurrentAntsLabel = new JLabel(CONCURRENT_ANTS_LABEL);
		add(concurrentAntsLabel);
		concurrentAntsField = new JTextField(CONCURRENT_ANTS_DEFAULT);
		concurrentAntsField.setPreferredSize(new Dimension(50, 20));
		add(concurrentAntsField);

		runButton = new JButton(START_BUTTON);
		runButton.addActionListener(new RunListener());
		add(runButton);
	}

	private final class RunListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (runButton.getText() == START_BUTTON) {
				options.setMaxLength(Integer.parseInt(turnsField.getText()));
				antColony = new AntColony(options);
				Thread antColonyThread = new Thread(antColony);
				antColonyThread.start();

				runButton.setText(STOP_BUTTON);
			} else {
				antColony.stop();
				runButton.setText(START_BUTTON);
			}
		}

	}

	public ChessOptions getChessOptions() {
		return options;
	}

	public AntColony getAntColony() {
		return antColony;
	}
}
