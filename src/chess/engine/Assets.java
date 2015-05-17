package chess.engine;

import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class Assets {
	private static final HashMap<Class<? extends Piece>, Integer> imageMap = new HashMap<>();
	private Image background;
	private Image active;
	private Image[] images;

	static {
		imageMap.put(Pawn.class, 0);
		imageMap.put(Rook.class, 1);
		imageMap.put(Bishop.class, 2);
		imageMap.put(Knight.class, 3);
		imageMap.put(King.class, 4);
		imageMap.put(Queen.class, 5);
	}

	public Assets() {
	}

	public void loadAssets() {
		ImageIcon background = new ImageIcon("img/chessgrid.png");
		this.background = background.getImage();

		ImageIcon active = new ImageIcon("img/active.png");
		this.active = active.getImage();

		// Load white assets:
		Image whitePawn = new ImageIcon("img/pieces/chess_piece_2_white_pawn.png").getImage();
		Image whiteRook = new ImageIcon("img/pieces/chess_piece_2_white_rook.png").getImage();
		Image whiteBishop = new ImageIcon("img/pieces/chess_piece_2_white_bishop.png").getImage();
		Image whiteQueen = new ImageIcon("img/pieces/chess_piece_2_white_queen.png").getImage();
		Image whiteKing = new ImageIcon("img/pieces/chess_piece_2_white_king.png").getImage();
		Image whiteKnight = new ImageIcon("img/pieces/chess_piece_2_white_knight.png").getImage();
		// Load black assets:
		Image blackPawn = new ImageIcon("img/pieces/chess_piece_2_black_pawn.png").getImage();
		Image blackKing = new ImageIcon("img/pieces/chess_piece_2_black_king.png").getImage();
		Image blackRook = new ImageIcon("img/pieces/chess_piece_2_black_rook.png").getImage();
		Image blackBishop = new ImageIcon("img/pieces/chess_piece_2_black_bishop.png").getImage();
		Image blackQueen = new ImageIcon("img/pieces/chess_piece_2_black_queen.png").getImage();
		Image blackKnight = new ImageIcon("img/pieces/chess_piece_2_black_knight.png").getImage();

		images = new Image[12];
		images[0] = whitePawn;
		images[1] = whiteRook;
		images[2] = whiteBishop;
		images[3] = whiteKnight;
		images[4] = whiteKing;
		images[5] = whiteQueen;

		images[6] = blackPawn;
		images[7] = blackRook;
		images[8] = blackBishop;
		images[9] = blackKnight;
		images[10] = blackKing;
		images[11] = blackQueen;
	}

	public Image getBackground() {
		return background;
	}

	public Image getActive() {
		return active;
	}

	public Image getImage(Class<? extends Piece> clazz, ChessColor color) {
		if (color.equals(ChessColor.WHITE)) {
			return images[imageMap.get(clazz)];
		} else {
			return images[imageMap.get(clazz) + 6];
		}
	}

}
