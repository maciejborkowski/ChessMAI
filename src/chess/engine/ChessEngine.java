package chess.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import uci.MoveParser;
import chess.pieces.*;

public class ChessEngine {
	public static enum State {
		INIT, NORMAL, CHECKMATE, CHECK
	}

	public static final int SQUARE_WIDTH = 8;
	public static final int SQUARE_HEIGHT = 8;
	private static final HashMap<Integer, Class<? extends Piece>> pieceMap = new HashMap<>();

	static {
		pieceMap.put(1, Knight.class);
		pieceMap.put(2, Bishop.class);
		pieceMap.put(3, Rook.class);
		pieceMap.put(4, Queen.class);
	}

	public static void createPieces(ChessGame game) {
		Piece piece = null;
		List<Piece> whitePieces = game.getWhitePieces();
		List<Piece> blackPieces = game.getBlackPieces();

		for (int i = 0; i < SQUARE_WIDTH; i++) {
			piece = new Pawn(game, ChessColor.WHITE, i, 6);
			whitePieces.add(piece);
			game.getSquare(i, 6).setPiece(piece);
		}

		piece = new Knight(game, ChessColor.WHITE, 1, 7);
		game.getSquare(1, 7).setPiece(piece);
		whitePieces.add(piece);
		piece = new Knight(game, ChessColor.WHITE, 6, 7);
		game.getSquare(6, 7).setPiece(piece);
		whitePieces.add(piece);

		piece = new Bishop(game, ChessColor.WHITE, 2, 7);
		game.getSquare(2, 7).setPiece(piece);
		whitePieces.add(piece);
		piece = new Bishop(game, ChessColor.WHITE, 5, 7);
		game.getSquare(5, 7).setPiece(piece);
		whitePieces.add(piece);

		piece = new Rook(game, ChessColor.WHITE, 0, 7);
		game.getSquare(0, 7).setPiece(piece);
		whitePieces.add(piece);
		piece = new Rook(game, ChessColor.WHITE, 7, 7);
		game.getSquare(7, 7).setPiece(piece);
		whitePieces.add(piece);

		piece = new Queen(game, ChessColor.WHITE, 3, 7);
		game.getSquare(3, 7).setPiece(piece);
		whitePieces.add(piece);

		piece = new King(game, ChessColor.WHITE, 4, 7);
		game.getSquare(4, 7).setPiece(piece);
		whitePieces.add(piece);

		for (int i = 0; i < SQUARE_WIDTH; i++) {
			piece = new Pawn(game, ChessColor.BLACK, i, 1);
			blackPieces.add(piece);
			game.getSquare(i, 1).setPiece(piece);
		}

		piece = new Knight(game, ChessColor.BLACK, 1, 0);
		game.getSquare(1, 0).setPiece(piece);
		blackPieces.add(piece);
		piece = new Knight(game, ChessColor.BLACK, 6, 0);
		game.getSquare(6, 0).setPiece(piece);
		blackPieces.add(piece);

		piece = new Bishop(game, ChessColor.BLACK, 2, 0);
		game.getSquare(2, 0).setPiece(piece);
		blackPieces.add(piece);
		piece = new Bishop(game, ChessColor.BLACK, 5, 0);
		game.getSquare(5, 0).setPiece(piece);
		blackPieces.add(piece);

		piece = new Rook(game, ChessColor.BLACK, 0, 0);
		game.getSquare(0, 0).setPiece(piece);
		blackPieces.add(piece);
		piece = new Rook(game, ChessColor.BLACK, 7, 0);
		game.getSquare(7, 0).setPiece(piece);
		blackPieces.add(piece);

		piece = new Queen(game, ChessColor.BLACK, 3, 0);
		game.getSquare(3, 0).setPiece(piece);
		blackPieces.add(piece);

		piece = new King(game, ChessColor.BLACK, 4, 0);
		game.getSquare(4, 0).setPiece(piece);
		blackPieces.add(piece);
	}

	public static void move(ChessGame game, int[] newMove) {
		Square moveFrom = game.getSquare(newMove[0], newMove[1]);
		Square moveTo = game.getSquare(newMove[2], newMove[3]);
		Piece piece = moveFrom.getPiece();

		if (newMove[4] == 5) {
			game.setState(ChessEngine.State.CHECKMATE);
			game.setWinner(game.getTurn().negate());
			return;
		}

		if (piece != null && piece.getColor() == game.getTurn()) {
			if (removePieceOnSquare(game, moveTo)) {
				return;
			}
			piece.setX(moveTo.getX());
			piece.setY(moveTo.getY());
			moveFrom.setPiece(null);
			moveTo.setPiece(piece);
			piece.setMoved(true);
			processEnPassant(game, moveFrom, moveTo, piece);
			processPromotion(game, newMove, piece);
			processCastling(game, moveFrom, moveTo, piece);
			if (findCheck(game, game.getTurn())) {
				game.setState(ChessEngine.State.CHECKMATE);
				game.setWinner(game.getTurn().negate());
			} else if (findCheck(game, game.getTurn().negate())) {
				game.setState(State.CHECK);
			} else {
				game.setState(State.NORMAL);
			}

			game.getMoveHistory().append(MoveParser.parse(newMove) + " ");
			game.setActive(null);
		}

	}

	public static List<int[]> availableMoves(ChessGame game) {
		List<Piece> pieces;
		if (game.getTurn() == ChessColor.WHITE) {
			pieces = game.getWhitePieces();
		} else {
			pieces = game.getBlackPieces();
		}

		List<int[]> moves = new LinkedList<>();
		for (Piece piece : pieces) {
			for (Square target : piece.getPossibleMoves()) {
				int move[] = new int[5];
				move[0] = piece.getX();
				move[1] = piece.getY();
				move[2] = target.getX();
				move[3] = target.getY();
				moves.add(move);
			}
		}
		return moves;
	}

	public static boolean canMove(ChessGame game, int x, int y) {
		if (game.getActive() == null) {
			return false;
		}
		Piece piece = game.getActive().getPiece();
		if (piece != null && piece.getColor() == game.getTurn()) {
			if (piece.canMove(x, y)) {
				return true;
			}
		}
		return false;
	}

	private static void processEnPassant(ChessGame game, Square moveFrom, Square moveTo, Piece piece) {
		if (piece instanceof Pawn) {
			if (Math.abs(moveFrom.getY() - moveTo.getY()) == 2) {
				((Pawn) piece).setPassantTarget(true);
			} else {
				Square passant;
				if (piece.getColor().equals(ChessColor.BLACK)) {
					passant = game.getSquare(piece.getX(), piece.getY() - 1);
				} else {
					passant = game.getSquare(piece.getX(), piece.getY() + 1);
				}
				if (passant.getPiece() instanceof Pawn && ((Pawn) passant.getPiece()).isEnPassantTarget()) {
					removePieceOnSquare(game, passant);
				}
			}
		}
		List<Piece> pieces;
		if (piece.getColor().equals(ChessColor.BLACK)) {
			pieces = game.getWhitePieces();
		} else {
			pieces = game.getBlackPieces();
		}
		for (Piece aPiece : pieces) {
			if (aPiece instanceof Pawn) {
				((Pawn) aPiece).setPassantTarget(false);
			}
		}
	}

	private static void processPromotion(ChessGame game, int[] newMove, Piece piece) {
		if (newMove[4] != 0 && piece instanceof Pawn) {
			promote(game, piece, pieceMap.get(newMove[4]));
		}
	}

	private static void promote(ChessGame game, Piece piece, Class<? extends Piece> clazz) {
		try {
			List<Piece> piecesList;
			if (piece.getColor().equals(ChessColor.WHITE)) {
				piecesList = game.getWhitePieces();
			} else {
				piecesList = game.getBlackPieces();
			}

			piecesList.remove(piece);
			Piece newPiece = clazz.getConstructor(ChessGame.class, ChessColor.class, int.class, int.class).newInstance(
					game, piece.getColor(), piece.getX(), piece.getY());
			game.getSquare(piece.getX(), piece.getY()).setPiece(newPiece);
			piecesList.add(newPiece);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			System.out.println("PROMOTION FAILED");
			e.printStackTrace();
		}
	}

	private static void processCastling(ChessGame game, Square moveFrom, Square moveTo, Piece piece) {
		if (piece instanceof King) {
			if (moveFrom.getX() - moveTo.getX() == 2) {
				Piece rook = game.getSquare(0, piece.getY()).getPiece();
				game.getSquare(0, piece.getY()).setPiece(null);
				game.getSquare(3, piece.getY()).setPiece(rook);
				rook.setX(3);
			} else if (moveFrom.getX() - moveTo.getX() == -2) {
				Piece rook = game.getSquare(7, piece.getY()).getPiece();
				game.getSquare(7, piece.getY()).setPiece(null);
				game.getSquare(5, piece.getY()).setPiece(rook);
				rook.setX(5);
			}
		}
	}

	public static boolean findCheck(ChessGame game, ChessColor color) {
		List<Piece> pieces;
		if (color.equals(ChessColor.WHITE)) {
			pieces = game.getWhitePieces();
		} else {
			pieces = game.getBlackPieces();
		}

		for (Piece piece : pieces) {
			if (piece instanceof King && ((King) piece).checkAttackedSquare(game.getSquare(piece.getX(), piece.getY()))) {
				return true;
			}
		}
		return false;
	}

	public static boolean removePieceOnSquare(ChessGame game, Square square) {
		Piece piece = square.getPiece();
		if (piece != null) {
			if (piece instanceof King) {
				game.setState(State.CHECKMATE);
				game.setWinner(game.getTurn());
				return true;
			}
			if (piece.getColor() == ChessColor.WHITE) {
				game.getWhitePieces().remove(piece);
			} else {
				game.getBlackPieces().remove(piece);
			}
			square.setPiece(null);
		}
		return false;
	}

}
