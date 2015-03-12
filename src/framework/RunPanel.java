package framework;

import java.awt.BorderLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RunPanel extends JPanel {
	
	public RunPanel() {
		initialize();
		addChessBoard();
		addPlayerOptions();
		addRunOptions();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
	}
	
	private void addChessBoard() {
		ChessBoardPanel panel = new ChessBoardPanel();
		add(panel, BorderLayout.CENTER);
		
	}
	
	private void addPlayerOptions() {
		PlayerOptionsPanel panel = new PlayerOptionsPanel();
		add(panel, BorderLayout.LINE_END);
	}

	private void addRunOptions() {
		RunOptionsPanel panel = new RunOptionsPanel();
		add(panel, BorderLayout.PAGE_END);
	}





}
