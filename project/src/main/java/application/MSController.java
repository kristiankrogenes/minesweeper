package application;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class MSController {
	
	@FXML GridPane grid;
	@FXML Button restartButton;
	@FXML Label statusField;
	@FXML Label bombCountField;
	
	private Board board;
	private int numberOfRemainingBombs;
	private boolean isGameWon;
	
	@FXML
	void initialize() {
		renderNewGame();
	}
	
	@FXML
	public void handleClick(MouseEvent e) {
		Node source = (Node) e.getSource();
		int squareId = Integer.parseInt(source.getId());
		int posX = GridPane.getColumnIndex(source);
		int posY = GridPane.getRowIndex(source);
		
		if (e.getButton() == MouseButton.PRIMARY) {
			checkSquare(squareId, posX, posY);
		}
		if (e.getButton() == MouseButton.SECONDARY) {
			if (board.getSquares().get(squareId).getIsEditable()) {
				((Button) source).setText("?");
			} else {
				((Button) source).setText("");
			}
			board.getSquares().get(squareId).setIsEditable();
			board.getSquares().get(squareId).setIsFlagged();
			updateRemainingBombs();
		}
		
		if (isGameWon()) {
			wonGame();
		}
	}
	
	@FXML 
	void handleRestartButton() {
		renderNewGame();
	}
	
	private void renderNewGame() {
		board = new Board();
		grid.getChildren().clear();
		addButtons();
		updateRemainingBombs();
		updateStatusField("");
	}
	
	private void addButtons() {
		
		int id = 0;
		
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				Button button = new Button();
				button.setMaxWidth(50.0);
				button.setMaxHeight(50.0);
				button.setStyle("-fx-font-size:12; -fx-background-color: #D4D4D4");
				button.setText("");
				button.setOnMouseClicked(e -> handleClick(e));
				button.setId(Integer.toString(id));
				id++;
				grid.add(button, j, i);
			}
		}
	}
	
	private void checkSquare(int id, int posX, int posY) {
		
		String text;
		
		if (board.getSquares().get(id).getIsEditable()) {
			if (board.getSquares().get(id).getIsBomb()) {
				lostGame();
				/*
				text = "X";
				changeSquare(id, posX, posY, text);
				disableButton(id);
				board.getSquares().get(id).setIsEditable();*/
			} else {
				
				int numOfBombs = board.numberOfBombsNearby(posX, posY);
				
				if (numOfBombs == 0) {
					text = "";
					changeSquare(id, posX, posY, text);
					disableButton(id);
					board.getSquares().get(id).setIsEditable();
					openSquare(id, posX, posY);
				} else {
					text = Integer.toString(numOfBombs);
					changeSquare(id, posX, posY, text);
					board.getSquares().get(id).setIsEditable();
					disableButton(id);
				}
			}
		}
	}
	
	private void changeSquare(int id,int posX, int posY, String text) {
		Label label = new Label();
		label.setMaxWidth(50.0);
		label.setMaxHeight(50.0);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setAlignment(Pos.CENTER);
		label.setText(text);
		grid.add(label, posX, posY);
	}
	
	private void disableButton(int id) {
		for (Node node : grid.getChildren()) {
			if (node.getId() != null) {
				if (Integer.parseInt(node.getId()) == id) {
					node.setDisable(true);
				}
			}
		}
	}
	
	private void openSquare(int id, int posX, int posY) {
		for (int x=-1; x<2; x++) {
			for (int y=-1; y<2; y++) {
				
				int nX = posX + x;
				int nY = posY + y;
				
				if ((nX < 10 && nX >= 0) && (nY < 10 && nY >= 0) && !(posX == nX && posY == nY) && !(board.getSquares().get((10*nY) + nX).getIsBomb())) {
					checkSquare((10*nY) + nX, nX, nY);
				}
			}
		}
	}
	
	private void wonGame() {
		board.getSquares().stream().forEach(sq -> {
			int id = board.getSquares().indexOf(sq);
			int posX = id%10;
			int posY = id/10;
			if (sq.getIsBomb()) {
				changeSquare(id, posX, posY, "?");
			}
			disableButton(id);
		});
		updateStatusField("YOU WON");
	}
	
	private void lostGame() {
		board.getSquares().stream().forEach(sq -> {
			int id = board.getSquares().indexOf(sq);
			int posX = id%10;
			int posY = id/10;
			if (sq.getIsBomb()) {
				changeSquare(id, posX, posY, "X");
			}
			disableButton(id);
		});
		updateStatusField("YOU LOST");
	}
	
	private boolean isGameWon() {
		System.out.println("CHECK");
		this.isGameWon = true;
		board.getSquares().stream().forEach(sq -> {
			if (!((sq.getIsBomb() && sq.getIsFlagged()) || (!sq.getIsBomb() && !sq.getIsEditable()))) {
				isGameWon = false;
			}
		});
		
		return isGameWon;
	}
	
	private int checkRemainingBombs() {
		numberOfRemainingBombs = board.getTotalBombs();
		board.getSquares().stream().forEach(sq -> {
			if (sq.getIsFlagged()) {
				numberOfRemainingBombs--;
			}
		});
		return numberOfRemainingBombs;
	}
	
	private void updateRemainingBombs() {
		bombCountField.setText("REMAINING BOMBS:	" + Integer.toString(checkRemainingBombs()));
	}
	
	private void updateStatusField(String text) {
		statusField.setText(text);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
