package chess;

import java.awt.event.MouseEvent;
import java.util.List;

import uci.MoveParser;

public class HumanPlayer extends Player {
	private int[] move = new int[5];
	private boolean moved = false;

	public int[] think() throws Exception {
		ChessBoard board = game.getBoard();
		if (board == null) {
			throw new Exception("THERE IS NO GUI FOR BOARD");
		}
		moved = false;

		System.out.println(color + " THINKS");

		while (!moved && game.isRunning()) {
			List<MouseEvent> mouseEvents = board.getMouseEvents();
			if (mouseEvents.size() > 0) {
				MouseEvent event = mouseEvents.get(0);
				int x = event.getX() / (int) ChessBoard.SQUARE_PIXEL_WIDTH;
				int y = event.getY() / (int) ChessBoard.SQUARE_PIXEL_HEIGHT;
				if (isMovablePieceActive() && ChessEngine.canMove(game, x, y)) {
					move[0] = game.getActive().getX();
					move[1] = game.getActive().getY();
					move[2] = x;
					move[3] = y;
					move[4] = 0;
					processPromotion();
					moved = true;
				} else {
					game.setActive(x, y);
				}
				mouseEvents.remove(event);
				board.repaint();
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				System.out.println("PLAYER THINKING THREAD INTERRUPTED");
			}
		}
		System.out.println(color.toString() + " MOVE: " + MoveParser.parse(move));
		board.clearMouseEvents();
		return move;
	}

	private void processPromotion() {
		// There should be a choice of pieces for player, but it has a low
		// priority in this simulation, so it always creates a Queen
		if (color.equals(ChessColor.WHITE) && move[3] == 0) {
			move[4] = 4;
		} else if (color.equals(ChessColor.BLACK) && move[3] == 7) {
			move[4] = 4;
		}
	}

	private boolean isMovablePieceActive() {
		return game.getActive() != null && game.getActive().getPiece() != null
				&& game.getActive().getPiece().getColor() == color;
	}

}
