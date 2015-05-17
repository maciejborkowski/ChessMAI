package chess.pieces;

import java.util.ArrayList;

import chess.ChessEngine;
import chess.ChessGame;
import chess.ChessColor;
import chess.Square;

public final class King extends Piece {
	int[][] offsets = { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, -1 }, { -1, 0 }, { -1, 1 } };

	public King(ChessGame game, ChessColor color, int x, int y) {
		super(game, color, x, y);
	}
	
	private boolean isEnemyPawn(int[][] offsets, Square square)
	{
		for (int[] dir : offsets) {
			Square nextSquare = game.getSquare(square.getX() + dir[0], square.getY() + dir[1]);
			if(nextSquare != null)
				if(nextSquare.getPiece() != null)
					if(nextSquare.getPiece() instanceof Pawn &&
					   nextSquare.getPiece().getColor() != game.getTurn())
						return true;
		}
		return false;
	}
	
	// if return 0 then there is no enemy
	// if return 1 then there is an enemy
	// if return 2 then there is some Piece on the way and thus there is no need to check another squares
	private int isEnemyBishopOrQueen(int x, int y)
	{
		Square nextSquare = game.getSquare(x, y);
		if(nextSquare != null) {
			if(nextSquare.getPiece() != null) {
				if(nextSquare.getPiece() instanceof Bishop || nextSquare.getPiece() instanceof Queen)
				{
					if(nextSquare.getPiece().getColor() != game.getTurn())
						return 1;
					else
						return 2;
				}
				else
					if(nextSquare.getPiece() instanceof King && nextSquare.getPiece().getColor() == game.getTurn())
						return 0;
					return 2;
			}
		}
		return 0;
	}
	
	// if return 0 then there is no enemy
	// if return 1 then there is an enemy
	// if return 2 then there is some Piece on the way and thus there is no need to check another squares
	private int isEnemyQueenOrRook(int x, int y)
	{
		Square nextSquare = game.getSquare(x, y);
		if(nextSquare != null) {
			if(nextSquare.getPiece() != null) {
				if(nextSquare.getPiece() instanceof Queen || nextSquare.getPiece() instanceof Rook)
				{
					if(nextSquare.getPiece().getColor() != game.getTurn())
						return 1;
					else
						return 2;
				}
				else
					if(nextSquare.getPiece() instanceof King && nextSquare.getPiece().getColor() == game.getTurn())
						return 0;
					return 2;
			}
		}
		return 0;
	}
	
	// returns false if square is attacked or true if square is not attacked
	public boolean checkAttackedSquare(Square square)
	{
		int[][] offsets = { { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 }, { 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 } };
		Square nextSquare = null;
		// check if is there enemy Knight
		for (int[] dir : offsets) {
			nextSquare = game.getSquare(square.getX() + dir[0], square.getY() + dir[1]);
			if(nextSquare != null)
				if(nextSquare.getPiece() != null)
					if(nextSquare.getPiece() instanceof Knight &&
					   nextSquare.getPiece().getColor() != game.getTurn())
						return false;
		}
		
		// check if is there enemy King
		int[][] offsets4King = { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, 1 }, { 0, -1 }, { -1, 0 }, { -1, 1 }, { -1, -1 } };
		for(int[] dir : offsets4King) {
			nextSquare = game.getSquare(square.getX() + dir[0], square.getY() + dir[1]);
			if(nextSquare != null)
				if(nextSquare.getPiece() != null)
					if(nextSquare.getPiece() instanceof King &&
					   nextSquare.getPiece().getColor() != game.getTurn())
						return false;
		}
		
		// check if is there enemy Pawn
		if(game.getTurn() == ChessColor.WHITE)
		{
			int[][] offsets4White = { {-1, -1}, {1, -1} };
			if(isEnemyPawn(offsets4White, square))
				return false;
		}
		else {
			int[][] offsets4Black = { {-1, 1}, {1, 1} };
			if(isEnemyPawn(offsets4Black, square))
				return false;
		}
		
		// check if is there enemy Queen or Rook
		int x = square.getX();
		int y = square.getY();
		while(x < ChessEngine.SQUARE_WIDTH - 1)
		{
			x++;
			if(isEnemyQueenOrRook(x, y) == 1)
				return false;
			if(isEnemyQueenOrRook(x, y) == 2)
				break;
		}
		x = square.getX();
		while(x > 0)
		{
			x--;
			if(isEnemyQueenOrRook(x, y) == 1)
				return false;
			if(isEnemyQueenOrRook(x, y) == 2)
				break;
		}
		x = square.getX();
		while(y < ChessEngine.SQUARE_HEIGHT - 1)
		{
			y++;
			if(isEnemyQueenOrRook(x, y) == 1)
				return false;
			if(isEnemyQueenOrRook(x, y) == 2)
				break;
		}
		y = square.getY();
		while(y > 0)
		{
			y--;
			if(isEnemyQueenOrRook(x, y) == 1)
				return false;
			if(isEnemyQueenOrRook(x, y) == 2)
				break;
		}
		 y = square.getY();			
		
		 // check if is there enemy Bishop
		 while(x < ChessEngine.SQUARE_WIDTH - 1 && y > 0)
		 {
			 x++;
			 y--;
			 if(isEnemyBishopOrQueen(x, y) == 1)
				 return false;
			 if(isEnemyBishopOrQueen(x, y) == 2)
				 break;
		 }
		 x = square.getX();
		 y = square.getY();
		 while(x > 0 && y > 0)
		 {
			 x--;
			 y--;
			 if(isEnemyBishopOrQueen(x, y) == 1)
				 return false;
			 if(isEnemyBishopOrQueen(x, y) == 2)
				 break;
		 }
		 x = square.getX();
		 y = square.getY();
		 while(x > 0 && y < ChessEngine.SQUARE_HEIGHT - 1)
		 {
			 x--;
			 y++;
			 if(isEnemyBishopOrQueen(x, y) == 1)
				 return false;
			 if(isEnemyBishopOrQueen(x, y) == 2)
				 break;
		 }
		 x = square.getX();
		 y = square.getY();
		 while(x < ChessEngine.SQUARE_WIDTH - 1 && y < ChessEngine.SQUARE_HEIGHT - 1)
		 {
			 x++;
			 y++;
			 if(isEnemyBishopOrQueen(x, y) == 1)
				 return false;
			 if(isEnemyBishopOrQueen(x, y) == 2)
				 break;
		 }
		 
		return true;
	}

	@Override
	public void createPossibleMoves() {
		possibleMoves = new ArrayList<Square>();

		for (int[] dir : offsets) {
			Square square = game.getSquare(x + dir[0], y + dir[1]);
			if (checkMovableSquare(square) || checkAttackableSquare(square)) {
				if(checkAttackedSquare(square))
					possibleMoves.add(square);
			}
		}

		// Castling
		if (game.getState().equals(ChessEngine.State.NORMAL) && !moved) {
			if ((game.getSquare(x + 3, y).getPiece() != null) && !game.getSquare(x + 3, y).getPiece().getMoved()) {
				Square square1 = game.getSquare(x + 1, y);
				Square square2 = game.getSquare(x + 2, y);
				if (checkMovableSquare(square1) && checkMovableSquare(square2)) {
					possibleMoves.add(square2);
				}
			}
			if ((game.getSquare(x - 4, y).getPiece() != null) && !game.getSquare(x - 4, y).getPiece().getMoved()) {
				Square square1 = game.getSquare(x - 1, y);
				Square square2 = game.getSquare(x - 2, y);
				Square square3 = game.getSquare(x - 3, y);
				if (checkMovableSquare(square1) && checkMovableSquare(square2) && checkMovableSquare(square3)) {
					possibleMoves.add(square2);
				}
			}
		}

	}

	// King blocks king
	@Override
	protected boolean checkMovableSquare(Square square) {
		if (square != null) {
			if (square.getPiece() == null) {
				if (!nearOpponentKing(square)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean nearOpponentKing(Square square) {
		for (int[] dir : offsets) {
			Square tempSquare = game.getSquare(square.getX() + dir[0], square.getY() + dir[1]);
			if (tempSquare != null && tempSquare.getPiece() instanceof King
					&& tempSquare.getPiece().getColor() != color) {
				return true;
			}

		}
		return false;
	}
}
