package framework;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Application {
	private final static String WINDOW_LABEL = "ChessMAI";
	private final static String RUN_TAB_LABEL = "Run";
	private final static String GENETIC_TAB_LABEL = "Genetic";
	private final static String ANNEALING_TAB_LABEL = "Annealing";
	private final static String ANT_TAB_LABEL = "Ant";

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
	}

	private void initFrame() {
		frame = new JFrame(WINDOW_LABEL);
		frame.setBounds(0, 0, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.add(tabbedPane);
	}

	private void initBoardPanel() {
		JPanel runPanel = new RunPanel();
		tabbedPane.addTab(RUN_TAB_LABEL, runPanel);
		JPanel geneticPanel = new GeneticPanel();
		tabbedPane.addTab(GENETIC_TAB_LABEL, geneticPanel);
		JPanel annealingPanel = new AnnealingPanel();
		tabbedPane.addTab(ANNEALING_TAB_LABEL, annealingPanel);
		JPanel antPanel = new AntPanel();
		tabbedPane.addTab(ANT_TAB_LABEL, antPanel);
	}

}
