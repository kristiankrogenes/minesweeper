package application;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
	
	private ArrayList<Square> squares = new ArrayList<Square>();
	private int totalBombs;
	
	public Board() {
		for (int i=0; i<100; i++) {
			squares.add(new Square(15));
		}
		setTotalNumberOfBombs();
		
	}
	
	public Board (String data) {
		ArrayList<String> squaresData = new ArrayList<String>(Arrays.asList(data.split(",")));
		for (int i=0; i<100; i++) {
			ArrayList<String> squareData = new ArrayList<String>(Arrays.asList(squaresData.get(i).split("-")));
			boolean isBomb = Boolean.parseBoolean(squareData.get(0));
			boolean isEditable = Boolean.parseBoolean(squareData.get(1));
			boolean isFlagged = Boolean.parseBoolean(squareData.get(2));
			squares.add(new Square(isBomb, isEditable, isFlagged));
		}
		setTotalNumberOfBombs();
	}
	
	public ArrayList<Square> getSquares() {
		return this.squares;
	}
	
	public int getTotalBombs() {
		return this.totalBombs;
	}
	
	public int numberOfBombsNearby(int posX, int posY) {
		
		int bombCount = 0;
		
		for (int x=-1; x<2; x++) {
			for (int y=-1; y<2; y++) {
				
				int nX = posX + x;
				int nY = posY + y;
				
				if ((nX < 10 && nX >= 0) && (nY < 10 && nY >= 0)) {
					if (this.squares.get((10*nY) + nX).getIsBomb()) {
						bombCount++;
					}
				}
			}
		}
		
		return bombCount;
	}
	
	private void setTotalNumberOfBombs() {
		squares.stream().forEach(sq -> {
			if (sq.getIsBomb()) {
				this.totalBombs++;
			}
		});
	}
}
