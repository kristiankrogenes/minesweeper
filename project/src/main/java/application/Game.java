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
	
	public int numberOfBombsNearby(int posX, int posY) {

		int bombCount = 0;

		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {

				int nX = posX + x;
				int nY = posY + y;

				if ((nX < 10 && nX >= 0) && (nY < 10 && nY >= 0)) {
					if (board.getSquares().get((10 * nY) + nX).getIsBomb()) {
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

	public void openNearbySquares(int id, int posX, int posY) {
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				int nX = posX + x;
				int nY = posY + y;
				if ((nX < 10 && nX >= 0) && (nY < 10 && nY >= 0) && !(posX == nX && posY == nY)
						&& !(this.getBoard().getSquares().get((10 * nY) + nX).getIsBomb())) {
					//checkSquare((10 * nY) + nX, nX, nY);
					checkSquare((10 * nY) + nX);
				}
			}
		}
	}
	
	private int checkSquare(int id) {
		return id;
	}
	
	public boolean isSquareEditable(int id) {
		return board.getSquares().get(id).getIsEditable();
	}
	 
	public boolean isSquareBomb(int id) {
		return board.getSquares().get(id).getIsBomb();
	}
}
