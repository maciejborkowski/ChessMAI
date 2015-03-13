package chess;


import java.util.List;

public abstract class Piece {
	public static final int WHITE_PAWN = 0;
	public static final int WHITE_KNIGHT = 1;
	public static final int WHITE_BISHOP = 2;
	public static final int WHITE_ROOK = 3;
	public static final int WHITE_QUEEN = 4;
	public static final int WHITE_KING = 5;
	public static final int BLACK_PAWN = 6;
	public static final int BLACK_KNIGHT = 7;
	public static final int BLACK_BISHOP = 8;
	public static final int BLACK_ROOK = 9;
	public static final int BLACK_QUEEN = 10;
	public static final int BLACK_KING = 11;
	
	protected ChessEngine game;
	protected int x, y;
	protected int type;
	protected Color color;
	protected List<Square> possibleMoves = null;
	
	public Piece(ChessEngine game, int type, Color color, int x, int y) {
		this.game = game;
		this.type = type;
		this.color = color;
		this.x = x;
		this.y = y;
	}
	
	public abstract void createPossibleMoves();
	
	public List<Square> getPossibleMoves() {
		createPossibleMoves();	
		return possibleMoves;
	}
	
	public boolean move(int x, int y) {
		Square ms = game.getSquare(x, y);
	
		createPossibleMoves();
		
		for(Square s : possibleMoves) {
			System.out.println(s.getX() + " - " + s.getY());
		}

		
		if(possibleMoves.contains(ms)) {
				return true;
		}
		
		return false;
	}
	
	public boolean checkMovableSquare(int dx, int dy) {
		
		Square s = game.getSquare(dx,dy);
		if(s != null) {
			if(s.getPiece() != null) {
				if(isOpponent(s.getPiece())) possibleMoves.add(s);
				return false;
			}
			else {
				possibleMoves.add(s); 
				return true;
			}
		}
		return false;

	}
	
	public boolean isOpponent(Piece p) {
		if(getColor() == Color.WHITE && p.getColor() == Color.BLACK) return true;
		if(getColor() == Color.BLACK && p.getColor() == Color.WHITE) return true;
		return false;
	}
	
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}

}
