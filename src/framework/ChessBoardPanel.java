package framework;

import java.awt.Dimension;

import javax.swing.JPanel;

import chess.ChessBoard;
import chess.ChessEngine;

@SuppressWarnings("serial")
public class ChessBoardPanel extends JPanel {
	public static final int BOARD_WIDTH = 500;
	public static final int BOARD_HEIGHT = 500;

	private ChessBoard chessBoard;
	private ChessOptions options;
	
	public ChessBoardPanel(ChessOptions options) {
		this.options = options;
		ChessEngine engine = new ChessEngine();
		options.setEngine(engine);
		chessBoard = new ChessBoard(engine);
		chessBoard.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		setFocusable(true);	
		add(chessBoard);
	}

}
