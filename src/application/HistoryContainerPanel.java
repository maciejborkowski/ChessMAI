package application;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import chess.engine.HistoryContainer;

@SuppressWarnings("serial")
public class HistoryContainerPanel extends JPanel implements HistoryContainer {
	protected JTextArea historyArea;

	public HistoryContainerPanel() {
		historyArea = new JTextArea(2, 50);
		historyArea.setEditable(false);
		historyArea.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(historyArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
	}

	@Override
	public String extract() {
		return historyArea.getText();
	}

	@Override
	public void rewrite(String history) {
		historyArea.setText(history);
	}

}
