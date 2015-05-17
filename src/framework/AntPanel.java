package framework;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metaheuristic.antsearch.AntColony;

@SuppressWarnings("serial")
public class AntPanel extends JPanel {
	private static final String TURNS_LABEL = "Number of turns";
	private static final String TURNS_DEFAULT = "5";
	private static final String START_BUTTON = "Start";
	private static final String STOP_BUTTON = "Stop";

	private JLabel turnsLabel;
	private JTextField turnsField;
	private JButton runButton;

	private AntColony antColony;
	
	public AntPanel() {
		turnsLabel = new JLabel(TURNS_LABEL);
		add(turnsLabel);
		turnsField = new JTextField(TURNS_DEFAULT);
		turnsField.setPreferredSize(new Dimension(50, 20));
		add(turnsField);

		runButton = new JButton(START_BUTTON);
		runButton.addActionListener(new RunListener());
		add(runButton);
	}

	private final class RunListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (runButton.getText() == START_BUTTON) {
				antColony = new AntColony(Integer.parseInt(turnsField.getText()));
				runButton.setText(STOP_BUTTON);
			} else {
				antColony.stop();
				runButton.setText(START_BUTTON);
			}
		}

	}
}
