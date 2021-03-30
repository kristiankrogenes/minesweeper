package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class MSController {
	
	@FXML GridPane grid;
	
	private Board board;
	
	@FXML
	void initialize() {
		board = new Board();
		addButtons();
	}
	
	private void addButtons() {
		
		int id = 0;
		
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				Button button = new Button();
				button.setMaxWidth(50.0);
				button.setMaxHeight(50.0);
				button.setStyle("-fx-font-size:12; -fx-background-color: #AAAAAA");
				button.setText("" + board.getSquares().get(id).getIsBomb());
				button.setOnMouseClicked(e -> handleClick(e));
				button.setId(Integer.toString(id));
				id++;
				grid.add(button, j, i);
			}
		}
	}
	
	@FXML
	void handleClick(MouseEvent e) {
		Node source = (Node) e.getSource();
		int squareId = Integer.parseInt(source.getId());
		int posX = GridPane.getColumnIndex(source);
		int posY = GridPane.getRowIndex(source);
		checkSquare(squareId, posX, posY);
		
	}
	
	private void checkSquare(int id, int posX, int posY) {
		String text;
		
		if (board.getSquares().get(id).getIsBomb()) {
			text = "X";
		} else {
			text = Integer.toString(board.numberOfBombsNearby(posX, posY));
		}
		
		changeSquare(id, posX, posY, text);
		disableButton(id);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
