package framework;

import java.awt.Dimension;

import javax.swing.JPanel;

import chess.ChessBoard;
import chess.ChessOptions;

@SuppressWarnings("serial")
public class ChessBoardPanel extends JPanel {
	public static final int BOARD_WIDTH = 600; //500;
	public static final int BOARD_HEIGHT = 520;

	private ChessBoard chessBoard;
	private ChessOptions options;
	
	public ChessBoardPanel(ChessOptions options) {
		this.options = options;
		chessBoard = new ChessBoard();
		options.setBoard(chessBoard);
		chessBoard.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		setFocusable(true);	
		add(chessBoard);
	}

}
