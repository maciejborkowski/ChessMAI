package chess;

import java.awt.event.MouseEvent;
import java.util.List;

public class HumanPlayer extends Player {
	private int[] move = new int[5];
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
					move[4] = 0;
					processPromotion();
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

	private void processPromotion() {
		// There should be a choice of pieces for player, but it has a low
		// priority in this simulation, so it always creates a Queen
		if (color.equals(Color.WHITE) && move[3] == 0) {
			move[4] = 4;
		} else if (color.equals(Color.BLACK) && move[3] == 7) {
			move[4] = 4;
		}
	}

	private boolean isMovablePieceActive() {
		return engine.getActive() != null && engine.getActive().getPiece() != null
				&& engine.getActive().getPiece().getColor() == color;
	}

}
