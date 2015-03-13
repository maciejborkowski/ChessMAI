package chess;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Assets {
	private Image background;
	private Image active;
	
	private final Image white_pawn;
	private final Image white_rook;
	private final Image white_bishop;
	private final Image white_knight;
	private final Image white_queen;
	private final Image white_king;
	private final Image black_pawn;
	private final Image black_rook;
	private final Image black_bishop;
	private final Image black_knight;
	private final Image black_queen;
	private final Image black_king;
	
	private Image[] pieces;
	
	public Assets() {
		//Load white assets:
		ImageIcon white_pawn = new ImageIcon("img/pieces/chess_piece_2_white_pawn.png");
		this.white_pawn = white_pawn.getImage();
		ImageIcon white_rook = new ImageIcon("img/pieces/chess_piece_2_white_rook.png");
		this.white_rook = white_rook.getImage();
		ImageIcon white_bishop = new ImageIcon("img/pieces/chess_piece_2_white_bishop.png");
		this.white_bishop = white_bishop.getImage();
		ImageIcon white_queen = new ImageIcon("img/pieces/chess_piece_2_white_queen.png");
		this.white_queen = white_queen.getImage();
		ImageIcon white_king = new ImageIcon("img/pieces/chess_piece_2_white_king.png");
		this.white_king = white_king.getImage();
		ImageIcon white_knight = new ImageIcon("img/pieces/chess_piece_2_white_knight.png");
		this.white_knight = white_knight.getImage();
		//Load black assets:
		ImageIcon black_pawn = new ImageIcon("img/pieces/chess_piece_2_black_pawn.png");
		this.black_pawn = black_pawn.getImage();		
		ImageIcon black_king = new ImageIcon("img/pieces/chess_piece_2_black_king.png");
		this.black_king = black_king.getImage();
		ImageIcon black_rook = new ImageIcon("img/pieces/chess_piece_2_black_rook.png");
		this.black_rook = black_rook.getImage();
		ImageIcon black_bishop = new ImageIcon("img/pieces/chess_piece_2_black_bishop.png");
		this.black_bishop = black_bishop.getImage();
		ImageIcon black_queen = new ImageIcon("img/pieces/chess_piece_2_black_queen.png");
		this.black_queen = black_queen.getImage();
		ImageIcon black_knight = new ImageIcon("img/pieces/chess_piece_2_black_knight.png");
		this.black_knight = black_knight.getImage();
		
	}
	
	public void loadAssets() {
		
		ImageIcon background = new ImageIcon("img/chessgrid.png");
		this.background = background.getImage();
		
		ImageIcon active = new ImageIcon("img/active.png");
		this.active = active.getImage();
		
		pieces = new Image[12];
		int i = 0;
		pieces[i++] = white_pawn;
		pieces[i++] = white_knight;
		pieces[i++] = white_bishop;
		pieces[i++] = white_rook;
		pieces[i++] = white_queen;
		pieces[i++] = white_king;
		pieces[i++] = black_pawn;
		pieces[i++] = black_knight;
		pieces[i++] = black_bishop;
		pieces[i++] = black_rook;
		pieces[i++] = black_queen;
		pieces[i++] = black_king;
	}

	public Image getBackground() {
		return background;
	}
	
	public Image getActive() {
		return active;
	}

	public Image getPiece(int piece) {
		return pieces[piece];
	}
	
	public Image getWhite_pawn() {
		return white_pawn;
	}

	public Image getWhite_rook() {
		return white_rook;
	}

	public Image getWhite_bishop() {
		return white_bishop;
	}

	public Image getWhite_knight() {
		return white_knight;
	}

	public Image getWhite_queen() {
		return white_queen;
	}

	public Image getWhite_king() {
		return white_king;
	}

	public Image getBlack_pawn() {
		return black_pawn;
	}

	public Image getBlack_rook() {
		return black_rook;
	}

	public Image getBlack_king() {
		return black_king;
	}

	public Image getBlack_queen() {
		return black_queen;
	}

	public Image getBlack_knight() {
		return black_knight;
	}

	public Image getBlack_bishop() {
		return black_bishop;
	}

}
