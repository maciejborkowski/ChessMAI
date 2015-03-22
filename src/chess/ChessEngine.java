package chess;

import java.util.LinkedList;
import java.util.List;

import uci.MovesParser;
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
	private StringBuilder moveHistory = new StringBuilder();

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

	public Square getActive() {
		return active;
	}

	public void setActive(Square active) {
		this.active = active;
	}

	public void setActive(int x, int y) {
		this.active = squares[x][y];

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

	public Square getSquare(int x, int y) {
		if (x >= 0 && x < SQUARE_WIDTH && y >= 0 && y < SQUARE_HEIGHT)
			return squares[x][y];
		return null;
	}

	public void initGame() {
		setActive(null);
		whitePieces = new LinkedList<Piece>();
		blackPieces = new LinkedList<Piece>();
		squares = new Square[SQUARE_WIDTH][SQUARE_HEIGHT];
		for (int i = 0; i < SQUARE_WIDTH; i++) {
			for (int j = 0; j < SQUARE_HEIGHT; j++) {
				squares[i][j] = new Square(i, j);
			}
		}
		createPieces();
		currentTurn = Color.WHITE;
	}

	public void createPieces() {
		Piece piece = null;

		for (int i = 0; i < SQUARE_WIDTH; i++) {
			piece = new Pawn(this, Color.WHITE, i, 6);
			whitePieces.add(piece);
			squares[i][6].setPiece(piece);
		}

		piece = new Knight(this, Color.WHITE, 1, 7);
		squares[1][7].setPiece(piece);
		whitePieces.add(piece);
		piece = new Knight(this, Color.WHITE, 6, 7);
		squares[6][7].setPiece(piece);
		whitePieces.add(piece);

		piece = new Bishop(this, Color.WHITE, 2, 7);
		squares[2][7].setPiece(piece);
		whitePieces.add(piece);
		piece = new Bishop(this, Color.WHITE, 5, 7);
		squares[5][7].setPiece(piece);
		whitePieces.add(piece);

		piece = new Rook(this, Color.WHITE, 0, 7);
		squares[0][7].setPiece(piece);
		whitePieces.add(piece);
		piece = new Rook(this, Color.WHITE, 7, 7);
		squares[7][7].setPiece(piece);
		whitePieces.add(piece);

		piece = new Queen(this, Color.WHITE, 3, 7);
		squares[3][7].setPiece(piece);
		whitePieces.add(piece);

		piece = new King(this, Color.WHITE, 4, 7);
		squares[4][7].setPiece(piece);
		whitePieces.add(piece);

		for (int i = 0; i < SQUARE_WIDTH; i++) {
			piece = new Pawn(this, Color.BLACK, i, 1);
			blackPieces.add(piece);
			squares[i][1].setPiece(piece);
		}

		piece = new Knight(this, Color.BLACK, 1, 0);
		squares[1][0].setPiece(piece);
		blackPieces.add(piece);
		piece = new Knight(this, Color.BLACK, 6, 0);
		squares[6][0].setPiece(piece);
		blackPieces.add(piece);

		piece = new Bishop(this, Color.BLACK, 2, 0);
		squares[2][0].setPiece(piece);
		blackPieces.add(piece);
		piece = new Bishop(this, Color.BLACK, 5, 0);
		squares[5][0].setPiece(piece);
		blackPieces.add(piece);

		piece = new Rook(this, Color.BLACK, 0, 0);
		squares[0][0].setPiece(piece);
		blackPieces.add(piece);
		piece = new Rook(this, Color.BLACK, 7, 0);
		squares[7][0].setPiece(piece);
		blackPieces.add(piece);

		piece = new Queen(this, Color.BLACK, 3, 0);
		squares[3][0].setPiece(piece);
		blackPieces.add(piece);

		piece = new King(this, Color.BLACK, 4, 0);
		squares[4][0].setPiece(piece);
		blackPieces.add(piece);
	}

	public boolean move(int[] newMove) {
		Square moveFrom = squares[newMove[0]][newMove[1]];
		Square moveTo = squares[newMove[2]][newMove[3]];
		Piece piece = moveFrom.getPiece();
		if (piece != null && piece.getColor() == currentTurn) {
			if (moveTo.getPiece() != null) {
				removePiece(moveTo.getPiece());
			}
			piece.setX(moveTo.getX());
			piece.setY(moveTo.getY());
			moveFrom.setPiece(null);
			moveTo.setPiece(piece);
			piece.setMoved(true);
			processEnPassant(moveFrom, moveTo, piece);
			processPromotion(piece);
			processCastling(moveFrom, moveTo, piece);
			moveHistory.append(MovesParser.parse(newMove) + " ");
			setActive(null);
			changeTurn();
			return true;
		}
		return false;
	}

	public boolean canMove(int x, int y) {
		if (getActive() == null) {
			return false;
		}
		Piece piece = getActive().getPiece();
		if (piece != null && piece.getColor() == currentTurn) {
			if (piece.canMove(x, y)) {
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

	private void processEnPassant(Square moveFrom, Square moveTo, Piece piece) {
		if (piece instanceof Pawn) {
			if (Math.abs(moveFrom.getY() - moveTo.getY()) == 2) {
				((Pawn) piece).setPassantTarget(true);
			} else {
				Square passant;
				if (piece.getColor().equals(Color.BLACK)) {
					passant = squares[piece.getX()][piece.getY() - 1];
				} else {
					passant = squares[piece.getX()][piece.getY() + 1];
				}
				if (passant.getPiece() instanceof Pawn && ((Pawn) passant.getPiece()).isEnPassantTarget()) {
					removePiece(passant.getPiece());
					passant.setPiece(null);
				}
			}
		}
		List<Piece> pieces;
		if (piece.getColor().equals(Color.BLACK)) {
			pieces = whitePieces;
		} else {
			pieces = blackPieces;
		}
		for (Piece aPiece : pieces) {
			if (aPiece instanceof Pawn) {
				((Pawn) aPiece).setPassantTarget(false);
			}
		}
	}

	private void processPromotion(Piece piece) {
		// There should be a choice of pieces, but it has a low priority in this
		// simulation, so it always creates Queen
		if (piece instanceof Pawn) {
			if (piece.getColor().equals(Color.BLACK) && piece.getY() == 7) {
				blackPieces.remove(piece);
				Piece newPiece = new Queen(this, Color.BLACK, piece.getX(), 7);
				squares[piece.getX()][piece.getY()].setPiece(newPiece);
				blackPieces.add(newPiece);
			} else if (piece.getColor().equals(Color.WHITE) && piece.getY() == 0) {
				whitePieces.remove(piece);
				Piece newPiece = new Queen(this, Color.WHITE, piece.getX(), 0);
				squares[piece.getX()][piece.getY()].setPiece(newPiece);
				whitePieces.add(newPiece);
			}
		}
	}

	private void processCastling(Square moveFrom, Square moveTo, Piece piece) {
		// There should a check for checkmate
		if (piece instanceof King) {
			if (moveFrom.getX() - moveTo.getX() == 2) {
				Piece rook = squares[0][piece.getY()].getPiece();
				squares[0][piece.getY()].setPiece(null);
				squares[3][piece.getY()].setPiece(rook);
				rook.setX(3);
			} else if (moveFrom.getX() - moveTo.getX() == -2) {
				Piece rook = squares[7][piece.getY()].getPiece();
				squares[7][piece.getY()].setPiece(null);
				squares[5][piece.getY()].setPiece(rook);
				rook.setX(5);
			}
		}
	}

	public String getMoveHistory() {
		return moveHistory.toString();
	}

}
