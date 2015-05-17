package chess;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import chess.pieces.Piece;

@SuppressWarnings("serial")
public class ChessBoard extends JPanel {
	public static final double SQUARE_PIXEL_WIDTH = 62.25;
	public static final double SQUARE_PIXEL_HEIGHT = 62.25;
	public static final int TURN_CHECKER_X = 500;
	public static final int TURN_CHECKER_Y = 435;
	public static final int BOARD_MAX = 495;

	private Assets assets;
	private MouseAction mouse;
	private List<MouseEvent> mouseEvents;
	private ChessGame game;

	public ChessBoard() {
		mouse = new MouseAction();
		addMouseListener(mouse);
		mouseEvents = new ArrayList<MouseEvent>();
		loadAssets();
	}

	private int getSquareX(Square square) {
		return (int) (SQUARE_PIXEL_WIDTH * square.getX());
	}

	private int getSquareY(Square square) {
		return (int) (SQUARE_PIXEL_HEIGHT * square.getY());
	}

	public ChessGame getGame() {
		return game;
	}

	public void setGame(ChessGame game) {
		this.game = game;
	}

	public void loadAssets() {
		assets = new Assets();
		assets.loadAssets();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		boardDraw(g2d);
	}

	private void boardDraw(Graphics2D g2d) {
		g2d.drawImage(assets.getBackground(), 0, 0, null);

		if (game != null) {
			for (Piece piece : game.getWhitePieces()) {
				drawPiece(piece, g2d);
			}
			for (Piece piece : game.getBlackPieces()) {
				drawPiece(piece, g2d);
			}
			if (game.getActive() != null) {
				g2d.drawImage(assets.getActive(), getSquareX(game.getActive()), getSquareY(game.getActive()), null);
				if (game.getActive().getPiece() != null) {
					List<Square> possibleMoves = game.getActive().getPiece().getPossibleMoves();
					if(possibleMoves != null)
					for (Square square : possibleMoves) {
						g2d.drawImage(assets.getActive(), getSquareX(square), getSquareY(square), null);
					}
				}
			}

			g2d.setColor((ChessColor.BLACK == game.getTurn()) ? Color.BLACK : Color.WHITE);
			g2d.fill(new Rectangle(TURN_CHECKER_X, TURN_CHECKER_Y, (int) SQUARE_PIXEL_WIDTH, (int) SQUARE_PIXEL_HEIGHT));
			g2d.drawImage(assets.getActive(), TURN_CHECKER_X, TURN_CHECKER_Y, null);
		}
	}

	private void drawPiece(Piece piece, Graphics2D g2d) {
		Image img = assets.getImage(piece.getClass(), piece.getColor());
		int pixelX = (int) (piece.getX() * SQUARE_PIXEL_WIDTH);
		int pixelY = (int) (piece.getY() * SQUARE_PIXEL_HEIGHT);
		g2d.drawImage(img, pixelX, pixelY, null);
	}

	public List<MouseEvent> getMouseEvents() {
		return mouseEvents;
	}

	private class MouseAction implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (BOARD_MAX > e.getX() && BOARD_MAX > e.getY()) {
				mouseEvents.add(e);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	public void clearMouseEvents() {
		mouseEvents.clear();
	}

}
