package framework;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class PlayerOptionsPanel extends JPanel {
	private static final String PLAYER1_LABEL = "Player1: ";
	private static final String PLAYER2_LABEL = "Player2: ";
	private static final String AI_LABEL = "AI";
	private static final String METAHEURESTIC_LABEL = "Metaheurestic";
	private static final String PLAYER_LABEL = "Player";
	
	public PlayerOptionsPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		addPlayer1();
		add(Box.createRigidArea(new Dimension(0, 50)));
		addPlayer2();
	}

	private void addPlayer1() {
		JLabel playerLabel = new JLabel(PLAYER1_LABEL);
		add(playerLabel);
		JRadioButton ai = new JRadioButton(AI_LABEL);
		ai.setSelected(true);
		add(ai);
		
		JRadioButton metaheurestic = new JRadioButton(METAHEURESTIC_LABEL);
		add(metaheurestic);
		
		JRadioButton player = new JRadioButton(PLAYER_LABEL);
		add(player);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(ai);
	    group.add(metaheurestic);
	    group.add(player);
	}
	
	private void addPlayer2() {
		JLabel playerLabel = new JLabel(PLAYER2_LABEL);
		add(playerLabel);
		JRadioButton ai = new JRadioButton(AI_LABEL);
		ai.setSelected(true);
		add(ai);
		
		JRadioButton metaheurestic = new JRadioButton(METAHEURESTIC_LABEL);
		add(metaheurestic);
		
		JRadioButton player = new JRadioButton(PLAYER_LABEL);
		add(player);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(ai);
	    group.add(metaheurestic);
	    group.add(player);
	}
}
