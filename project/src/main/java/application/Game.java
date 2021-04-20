package application;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.paint.Color;

public class Game {

	private Board board;
	private int numberOfRemainingBombs;
	private boolean isGameWon;

	public Game() {
		this.board = new Board();
	}

	public Game(String data) {
		this.board = new Board(data);
	}

	public Board getBoard() {
		return board;
	}

	public boolean isGameWon() {
		this.isGameWon = true;
		board.getSquares().stream().forEach(sq -> {
			if (!((sq.getIsBomb() && sq.getIsFlagged()) || (!sq.getIsBomb() && !sq.getIsEditable()))) {
				isGameWon = false;
			}
		});

		return isGameWon;
	}
	
	public int numberOfBombsNearby(int posX, int posY) {

		int bombCount = 0;

		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {

				int nX = posX + x;
				int nY = posY + y;

				if (isSquareInBoard(nX, nY)) {
					if (isSquareBomb((10 * nY) + nX)) {
						bombCount++;
					}
				}
			}
		}

		return bombCount;
	}

	public int checkRemainingBombs() {
		numberOfRemainingBombs = board.getTotalBombs();
		board.getSquares().stream().forEach(sq -> {
			if (sq.getIsFlagged()) {
				numberOfRemainingBombs--;
			}
		});
		return numberOfRemainingBombs;
	}
	
	public boolean isSquareEditable(int id) {
		return board.getSquares().get(id).getIsEditable();
	}
	 
	public boolean isSquareBomb(int id) {
		return board.getSquares().get(id).getIsBomb();
	}
	
	public boolean isSquareInBoard(int posX, int posY) {
		return ((posX < 10 && posX >= 0) && (posY < 10 && posY >= 0));
	}
	
	public boolean isNearbySquareEmpty(int posX, int posY, int nX, int nY) {
		return (isSquareInBoard(nX, nY) && !(posX == nX && posY == nY) && !(isSquareBomb((10 * nY) + nX)));
	}
	
	public Color getColor(int num) {
		Color color;
		ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.BLUE, Color.GREEN, Color.RED, Color.DARKBLUE,
				Color.DARKRED, Color.CYAN, Color.YELLOW, Color.BLACK));
		color = colors.get(num - 1);
		return color;
	}
}
