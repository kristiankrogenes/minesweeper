package application;

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

    public int checkRemainingBombs() {
	numberOfRemainingBombs = board.getTotalBombs();
	board.getSquares().stream().forEach(sq -> {
	    if (sq.getIsFlagged()) {
		numberOfRemainingBombs--;
	    }
	});
	return numberOfRemainingBombs;
    }

    public void openNearbySquares(int id, int posX, int posY) {
	for (int x = -1; x < 2; x++) {
	    for (int y = -1; y < 2; y++) {
		int nX = posX + x;
		int nY = posY + y;
		if ((nX < 10 && nX >= 0) && (nY < 10 && nY >= 0) && !(posX == nX && posY == nY)
			&& !(this.getBoard().getSquares().get((10 * nY) + nX).getIsBomb())) {
		    checkSquare((10 * nY) + nX, nX, nY);
		}
	    }
	}
    }
}
