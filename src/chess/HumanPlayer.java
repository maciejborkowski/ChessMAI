package chess;

import java.awt.event.MouseEvent;
import java.util.List;

public class HumanPlayer extends Player {
	private int[] move = new int[4];
	private boolean moved = false;

	public int[] think() {
		moved = false;
		
		while (!moved) {
			List<MouseEvent> mouseEvents = board.getMouseEvents();
			if (mouseEvents.size() > 0) {
				MouseEvent event = mouseEvents.get(0);
				int x = event.getX() / (int) ChessBoard.SQUARE_PIXEL_WIDTH;
				int y = event.getY() / (int) ChessBoard.SQUARE_PIXEL_HEIGHT;
				if (isMovablePieceActive() && engine.canMove(x, y)) {
					move[0] = engine.getActive().getX();
					move[1] = engine.getActive().getY();
					move[2] = x;
					move[3] = y;
					moved = true;
				} else {
					engine.setActive(x, y);
				}
				mouseEvents.remove(event);
				if (engine.getBoard() != null) {
					engine.getBoard().repaint();
				}
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				System.out.println("PLAYER THINKING THREAD INTERRUPTED");
			}
		}
		board.clearMouseEvents();
		return move;
	}

	private boolean isMovablePieceActive() {
		return engine.getActive() != null && engine.getActive().getPiece() != null
				&& engine.getActive().getPiece().getColor() == color;
	}

}
