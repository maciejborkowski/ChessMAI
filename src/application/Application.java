package application;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class Application {
	private final static String WINDOW_LABEL = "ChessMAI";
	private final static String RUN_TAB_LABEL = "Run";
	private final static String GENETIC_TAB_LABEL = "Genetic";
	private final static String ANNEALING_TAB_LABEL = "Annealing";
	private final static String ANT_TAB_LABEL = "Ant";

	private final JTabbedPane tabbedPane = new JTabbedPane();
	private RunPanel runPanel;
	private GeneticPanel geneticPanel;
	private AnnealingPanel annealingPanel;
	private AntPanel antPanel;
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
		frame.setBounds(0, 0, 800, 620);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new ApplicationWindowAdapter());
		frame.getContentPane().setLayout(new BorderLayout());
		frame.add(tabbedPane);
	}

	private void initBoardPanel() {
		runPanel = new RunPanel();
		tabbedPane.addTab(RUN_TAB_LABEL, runPanel);
		geneticPanel = new GeneticPanel();
		tabbedPane.addTab(GENETIC_TAB_LABEL, geneticPanel);
		annealingPanel = new AnnealingPanel();
		tabbedPane.addTab(ANNEALING_TAB_LABEL, annealingPanel);
		antPanel = new AntPanel();
		tabbedPane.addTab(ANT_TAB_LABEL, antPanel);
	}

	private class ApplicationWindowAdapter extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			JFrame frame = (JFrame) e.getSource();

			int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit the application?",
					"Exit Application", JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				runPanel.getChessOptions().getAdapterPool().kill();
				antPanel.getColonyOptions().getChessOptions().getAdapterPool().kill();
				if (null != antPanel.getAntColony()) {
					antPanel.getAntColony().stop();
				}
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		}
	}

}
