package chess;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ChessBoard extends JPanel {
	public static final double SQUARE_PIXEL_WIDTH = 62.25;
	public static final double SQUARE_PIXEL_HEIGHT = 62.25;

	private Assets assets;
	private MouseAction mouse;
	private ChessEngine engine;
	private List<MouseEvent> mouseEvents;

	public ChessBoard(ChessEngine engine) {
		this.mouse = new MouseAction();
		this.engine = engine;
		engine.setBoard(this);
		addMouseListener(mouse);
		mouseEvents = new ArrayList<MouseEvent>();
		loadAssets();
	}

	public void loadAssets() {
		this.assets = new Assets();
		this.assets.loadAssets();
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		boardDraw(g2d);
	}

	private void boardDraw(Graphics2D g2d) {
		if (engine != null) {
			g2d.drawImage(assets.getBackground(), 0, 0, null);

			for (Piece piece : engine.getWhitePieces()) {
				drawPiece(piece, g2d);
			}
			for (Piece piece : engine.getBlackPieces()) {
				drawPiece(piece, g2d);
			}
			if (engine.getActive() != null) {
				g2d.drawImage(assets.getActive(), getSquareX(engine.getActive()), getSquareY(engine.getActive()),
						null);
				if (engine.getActive().getPiece() != null) {
					List<Square> possibleMoves = engine.getActive().getPiece().getPossibleMoves();
					for (Square square : possibleMoves) {
						g2d.drawImage(assets.getActive(), getSquareX(square), getSquareY(square), null);
					}
				}
			}
		}
	}

	private void drawPiece(Piece p, Graphics2D g2d) {
		Image img = assets.getPiece(p.getType());
		int pixelX = (int) (p.getX() * SQUARE_PIXEL_WIDTH);
		int pixelY = (int) (p.getY() * SQUARE_PIXEL_HEIGHT);
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
			mouseEvents.add(e);
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

	private int getSquareX(Square square) {
		return (int) (SQUARE_PIXEL_WIDTH * square.getX());
	}

	private int getSquareY(Square square) {
		return (int) (SQUARE_PIXEL_HEIGHT * square.getY());
	}
}
