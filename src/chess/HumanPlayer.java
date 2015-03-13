package chess;

import java.awt.event.MouseEvent;
import java.util.List;

public class HumanPlayer extends Player {
	private int[] nextMove = new int[4];
	private boolean moved = false;

	public HumanPlayer() {
	}

	public void think() {
		List<MouseEvent> mouseEvents = board.getMouseEvents();
		if (mouseEvents.size() > 0) {
			MouseEvent e = mouseEvents.get(0);
			int x = e.getX() / (int) ChessBoard.SQUARE_PIXEL_WIDTH;
			int y = e.getY() / (int) ChessBoard.SQUARE_PIXEL_HEIGHT;
			if (engine.getActive() != null && engine.getActive().getPiece() != null
					&& engine.getActive().getPiece().getColor() == color) {
				if (engine.canMove(x, y)) {
					nextMove[0] = engine.getActive().getX();
					nextMove[1] = engine.getActive().getY();
					nextMove[2] = x;
					nextMove[3] = y;
					moved = true;
					board.clearMouseEvents();
					return;
				}
			}
			engine.setActive(x, y);
		}
		board.clearMouseEvents();
	}

	@Override
	public int[] getMove() {
		if (moved) {
			moved = false;
			return nextMove;
		}
		return null;
	}
}
