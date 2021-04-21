package application;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private ArrayList<Square> squares = new ArrayList<Square>();
    private int totalBombs;

    // Konstruktør ved nytt game/brett
    public Board() {
	for (int i = 0; i < 100; i++) {
	    squares.add(new Square(15));
	}
	setTotalNumberOfBombs();

    }

    // Konstruktør ved loading av fil
    public Board(String data) {
	ArrayList<String> squaresData = new ArrayList<String>(Arrays.asList(data.split(",")));
	for (int i = 0; i < 100; i++) {
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

    private void setTotalNumberOfBombs() {
	squares.stream().forEach(sq -> {
	    if (sq.getIsBomb()) {
		this.totalBombs++;
	    }
	});
    }
}
