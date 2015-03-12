package framework;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RunOptionsPanel extends JPanel {
	private static final String RUN_LABEL = "Run";
	
	public RunOptionsPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JButton button = new JButton(RUN_LABEL);
		add(button);
	}
}
