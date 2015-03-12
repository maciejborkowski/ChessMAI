package framework;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Application {
	private final static String WINDOW_LABEL = "Document Integrity";
	private final static String RUN_TAB_LABEL = "Run";
	private final JTabbedPane tabbedPane = new JTabbedPane();
	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Application() {
		initializeApplication();
	}

	private void initializeApplication() {
		initFrame();
		initBoardPanel();
		initOtherBoard();
	}

	private void initFrame() {
		frame = new JFrame(WINDOW_LABEL);
		frame.setBounds(100, 100, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.add(tabbedPane);
	}

	private void initBoardPanel() {
		JPanel panel = new RunPanel();
		tabbedPane.addTab(RUN_TAB_LABEL, panel);
	}

	private void initOtherBoard() {
		JPanel panel = new JPanel();
		tabbedPane.addTab("other", panel);
	}
}
