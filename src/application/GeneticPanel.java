package application;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metaheuristic.genetic.GeneticOptions;
import metaheuristic.genetic.Genetic;
import metaheuristic.genetic.GeneticTest;
import uci.AdapterPool;
//import application.AntPanel.RunListener;
import chess.engine.ChessOptions;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

@SuppressWarnings("serial")
public class GeneticPanel extends JPanel {
	private static final String TURNS_LABEL = "Number of turns";
	private static final String TURNS_DEFAULT = "5";
	private static final String NUMBER_POPULATION_LABEL = "Number of population";
	private static final String GOOD_GENS_LABEL = "How many individuals have good gens(in percent)";
	private static final String GOOD_GENS_DEFAULT = "20";
	private static final String POPULATION_DEFAULT = "100";
	private static final String START_BUTTON = "Start";
	private static final String TEST_BUTTON = "Test";
	private static final String STOP_BUTTON = "Stop";
	private static final String HISTORY_LABEL = "Move history";
	
	private JLabel turnsLabel;
	private JTextField turnsField;
	private JTextField populationField;
	private JTextField goodGensField;
	private JTextField historyField;
	private JButton runButton;
	private JButton testButton;
	private Genetic genetic;
	private GeneticTest test;
	private GeneticOptions geneticOptions;
	private ChessOptions options;
	
	public GeneticPanel() {
		setLayout(new GridBagLayout());
		geneticOptions = new GeneticOptions();
		options = new ChessOptions();
		options.setAdapterPool(new AdapterPool());
		geneticOptions.setChessOptions(options);

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

		JLabel dissipationLabel = new JLabel(NUMBER_POPULATION_LABEL);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.1;
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(dissipationLabel, constraints);

		populationField = new JTextField(POPULATION_DEFAULT);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.9;
		constraints.gridx = 1;
		constraints.gridy = 2;
		add(populationField, constraints);
		
		JLabel goodGensLabel = new JLabel(GOOD_GENS_LABEL);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.1;
		constraints.gridx = 0;
		constraints.gridy = 3;
		add(goodGensLabel, constraints);

		goodGensField = new JTextField(GOOD_GENS_DEFAULT);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.9;
		constraints.gridx = 1;
		constraints.gridy = 3;
		add(goodGensField, constraints);

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
		
		testButton = new JButton(TEST_BUTTON);
		testButton.addActionListener(new RunListener());
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.2;
		constraints.gridx = 1;
		constraints.gridy = 7;
		add(testButton, constraints);
	}
	
	private final class RunListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			if (b.getText() == TEST_BUTTON) {
				test = new GeneticTest(options);
				Thread geneticTestThread = new Thread(test);
				geneticTestThread.start();
			} else
			{
				if (runButton.getText() == START_BUTTON) {
					geneticOptions.setMaxLength(Integer.parseInt(turnsField.getText()));
					geneticOptions.setPopulation(Integer.parseInt(populationField.getText()));
					geneticOptions.setGoodGensPercent(Integer.parseInt(goodGensField.getText()));
					geneticOptions.getChessOptions().setInitialMoveHistory(historyField.getText());
					HashMap<Class<? extends Piece>, Double> pieceCostMap = new HashMap<>();
					pieceCostMap.put(Bishop.class, 3.0);
					pieceCostMap.put(King.class, 100.0);
					pieceCostMap.put(Knight.class, 3.0);
					pieceCostMap.put(Pawn.class, 1.0);
					pieceCostMap.put(Queen.class, 10.0);
					pieceCostMap.put(Rook.class, 5.0);
					geneticOptions.setPieceCostMap(pieceCostMap);
					genetic = new Genetic(geneticOptions);
					Thread geneticThread = new Thread(genetic);
					geneticThread.start();
	
					runButton.setText(STOP_BUTTON);
				} else {
					genetic.stop();
					runButton.setText(START_BUTTON);
				}
			}
		}
	}
	
	/*public ChessOptions getChessOptions() {
		return options;
	}*/

	public Genetic getGenetic() {
		return genetic;
	}
}
