package application;

public class Game {

	private Board board;
	private int numberOfRemainingBombs;
	private boolean isGameWon;
	private boolean isGameLost = false;

	public Game() {
		this.board = new Board();
	}

	// Lager et nytt game fra fil, sendes videre til board
	public Game(String data) {
		this.board = new Board(data);
	}

	public Board getBoard() {
		return board;
	}

	// "Trykker" på en knapp og oppdaterer antall bomber rundt feltet eller hvis
	// det
	// er en bombe og du har tapt. Dette er det vi i kontrolleren kaller
	// primaryclick
	public void openSquare(int id) {
		if (!this.isGameLost) {
			if (isSquareEditable(id)) {
				if (isSquareBomb(id)) {
					this.isGameLost = true;
				} else {

					int numOfBombs = numberOfBombsNearby(id);

					if (numOfBombs == 0) {
						setSquareEditable(id);
						openNearbySquares(id);
					} else {
						setSquareEditable(id);
						setNearbyBombs(id, numOfBombs);
					}
				}
			}
		}
	}

	// Flagger en knapp. Dette er det vi i kontrolleren kaller secondaryclick
	public void flagSquare(int id) {
		if (!this.isGameLost) {
			setSquareEditable(id);
			setIsFlagged(id);
			updateRemainingBombs();
		}
	}

	// Åpner opp felter hvis det ikke er noen bomber rundt, kalles fra openSquare
	// og
	// sendes tilbake dit helt til alle felter som kan åpnes er åpne
	private void openNearbySquares(int id) {
		int posX = id % 10;
		int posY = id / 10;
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				int nX = posX + x;
				int nY = posY + y;
				int newid = (10 * nY) + nX;
				if (isNearbySquareEmpty(posX, posY, nX, nY)) {
					openSquare(newid);
				}
			}
		}
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

	public boolean getIsGameLost() {
		return this.isGameLost;
	}

	// Sjekker antall bomber rundt et spesifikt felt
	public int numberOfBombsNearby(int id) {
		int posX = id % 10;
		int posY = id / 10;

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

	// Oppdaterer antall bomber som ikke er flagget
	public void updateRemainingBombs() {
		numberOfRemainingBombs = board.getTotalBombs();
		board.getSquares().stream().forEach(sq -> {
			if (sq.getIsFlagged()) {
				numberOfRemainingBombs--;
			}
		});
	}

	// Sjekker om et felt faktisk er i brettet
	public boolean isSquareInBoard(int posX, int posY) {
		return ((posX < 10 && posX >= 0) && (posY < 10 && posY >= 0));
	}

	// Sjekker om nabofeltet tomt
	public boolean isNearbySquareEmpty(int posX, int posY, int nX, int nY) {
		return (isSquareInBoard(nX, nY) && !(posX == nX && posY == nY) && !(isSquareBomb((10 * nY) + nX)));
	}

	// Disse metodene er hjelpemetoder for å gjøre koden litt penere

	public boolean isSquareEditable(int id) {
		return board.getSquares().get(id).getIsEditable();
	}

	public void setSquareEditable(int id) {
		this.board.getSquares().get(id).setIsEditable();
	}

	public void setNearbyBombs(int id, int num) {
		this.board.getSquares().get(id).setNearbyBombs(num);
	}

	public int getRemainingBombs() {
		return this.numberOfRemainingBombs;
	}

	public void setIsFlagged(int id) {
		this.board.getSquares().get(id).setIsFlagged();
	}

	public boolean isSquareBomb(int id) {
		return board.getSquares().get(id).getIsBomb();
	}
}
