package chess;

import java.util.LinkedList;
import java.util.List;

import chess.pieces.*;

public class ChessEngine {
	public static final int SQUARE_WIDTH = 8;
	public static final int SQUARE_HEIGHT = 8;

	private Square[][] squares;
	private List<Piece> whitePieces = new LinkedList<>();
	private List<Piece> blackPieces = new LinkedList<>();
	private Square active;
	private Color currentTurn;
	private ChessBoard board;

	public ChessBoard getBoard() {
		return board;
	}

	public void setBoard(ChessBoard board) {
		this.board = board;
	}

	public ChessEngine() {
	}

	public ChessEngine(ChessBoard board) {
		this.board = board;
	}

	public void initGame() {
		setActive(null);
		whitePieces = new LinkedList<Piece>();
		blackPieces = new LinkedList<Piece>();
		squares = new Square[SQUARE_HEIGHT][SQUARE_WIDTH];
		for (int i = 0; i < SQUARE_HEIGHT; i++) {
			for (int j = 0; j < SQUARE_WIDTH; j++) {
				squares[i][j] = new Square(i, j);
			}
		}
		createPieces();
		currentTurn = Color.WHITE;
	}

	public void createPieces() {
		Piece piece = null;

		for (int i = 0; i < SQUARE_WIDTH; i++) {
			piece = new Pawn(this, Piece.WHITE_PAWN, Color.WHITE, i, 6);
			whitePieces.add(piece);
			squares[i][6].setPiece(piece);
		}

		piece = new Knight(this, Piece.WHITE_KNIGHT, Color.WHITE, 1, 7);
		squares[1][7].setPiece(piece);
		whitePieces.add(piece);
		piece = new Knight(this, Piece.WHITE_KNIGHT, Color.WHITE, 6, 7);
		squares[6][7].setPiece(piece);
		whitePieces.add(piece);

		piece = new Bishop(this, Piece.WHITE_BISHOP, Color.WHITE, 2, 7);
		squares[2][7].setPiece(piece);
		whitePieces.add(piece);
		piece = new Bishop(this, Piece.WHITE_BISHOP, Color.WHITE, 5, 7);
		squares[5][7].setPiece(piece);
		whitePieces.add(piece);

		piece = new Rook(this, Piece.WHITE_ROOK, Color.WHITE, 0, 7);
		squares[0][7].setPiece(piece);
		whitePieces.add(piece);
		piece = new Rook(this, Piece.WHITE_ROOK, Color.WHITE, 7, 7);
		squares[7][7].setPiece(piece);
		whitePieces.add(piece);

		piece = new Queen(this, Piece.WHITE_QUEEN, Color.WHITE, 3, 7);
		squares[3][7].setPiece(piece);
		whitePieces.add(piece);

		piece = new King(this, Piece.WHITE_KING, Color.WHITE, 4, 7);
		squares[4][7].setPiece(piece);
		whitePieces.add(piece);

		for (int i = 0; i < SQUARE_WIDTH; i++) {
			piece = new Pawn(this, Piece.BLACK_PAWN, Color.BLACK, i, 1);
			blackPieces.add(piece);
			squares[i][1].setPiece(piece);
		}

		piece = new Knight(this, Piece.BLACK_KNIGHT, Color.BLACK, 1, 0);
		squares[1][0].setPiece(piece);
		blackPieces.add(piece);
		piece = new Knight(this, Piece.BLACK_KNIGHT, Color.BLACK, 6, 0);
		squares[6][0].setPiece(piece);
		blackPieces.add(piece);

		piece = new Bishop(this, Piece.BLACK_BISHOP, Color.BLACK, 2, 0);
		squares[2][0].setPiece(piece);
		blackPieces.add(piece);
		piece = new Bishop(this, Piece.BLACK_BISHOP, Color.BLACK, 5, 0);
		squares[5][0].setPiece(piece);
		blackPieces.add(piece);

		piece = new Rook(this, Piece.BLACK_ROOK, Color.BLACK, 0, 0);
		squares[0][0].setPiece(piece);
		blackPieces.add(piece);
		piece = new Rook(this, Piece.BLACK_ROOK, Color.BLACK, 7, 0);
		squares[7][0].setPiece(piece);
		blackPieces.add(piece);

		piece = new Queen(this, Piece.BLACK_QUEEN, Color.BLACK, 3, 0);
		squares[3][0].setPiece(piece);
		blackPieces.add(piece);

		piece = new King(this, Piece.BLACK_KING, Color.BLACK, 4, 0);
		squares[4][0].setPiece(piece);
		blackPieces.add(piece);
	}

	public boolean move(int[] newMove) {
		Square moveFrom = squares[newMove[0]][newMove[1]];
		Square moveTo = squares[newMove[2]][newMove[3]];
		Piece piece = moveFrom.getPiece();
		if (piece != null && piece.getColor() == currentTurn) {
			System.out.println("TURN");
			if (piece.move(moveTo.getX(), moveTo.getY())) {
				if (moveTo.getPiece() != null) {
					removePiece(moveTo.getPiece());
				}
				piece.setX(moveTo.getX());
				piece.setY(moveTo.getY());
				System.out.println("POSSIBLE");
				moveFrom.setPiece(null);
				moveTo.setPiece(piece);
				setActive(null);
				changeTurn();
				return true;
			} else {
				System.out.println("MOVE FAILED");
			}
		}
		return false;
	}

	public boolean canMove(int x, int y) {
		if (getActive() == null) {
			System.out.println("ERROR canMove() called while active == null!");
			return false;
		}
		Piece piece = getActive().getPiece();
		if (piece != null && piece.getColor() == currentTurn) {
			if (piece.move(x, y)) {
				return true;
			}
		}
		return false;
	}

	private void removePiece(Piece piece) {
		if (piece.getColor() == Color.WHITE) {
			whitePieces.remove(piece);
		} else {
			blackPieces.remove(piece);
		}
	}

	private void changeTurn() {
		switch (currentTurn) {
		case WHITE:
			currentTurn = Color.BLACK;
			break;
		case BLACK:
			currentTurn = Color.WHITE;
			break;
		}
	}

	public Square getActive() {
		return active;
	}

	public void setActive(Square active) {
		this.active = active;
	}

	public void setActive(int x, int y) {
		this.active = squares[x][y];

	}

	public Square getSquare(int x, int y) {
		if (x >= 0 && x < SQUARE_WIDTH && y >= 0 && y < SQUARE_HEIGHT)
			return squares[x][y];
		return null;
	}

	public List<Piece> getWhitePieces() {
		return whitePieces;
	}

	public List<Piece> getBlackPieces() {
		return blackPieces;
	}

	public Color getTurn() {
		return currentTurn;
	}
}
