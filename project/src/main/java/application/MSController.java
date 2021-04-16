package application;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class MSController implements FileController {

    @FXML
    GridPane msgrid;
    @FXML
    Button restartButton;
    @FXML
    Button saveButton;
    @FXML
    Button loadButton;
    @FXML
    Label statusField;
    @FXML
    Label bombCountField;

    private Board board;
    private int numberOfRemainingBombs;
    private boolean isGameWon;
    private String filename = "msdata.txt";
    private String flag = new String(Character.toChars(0x1F6A9));
    private String bomb = new String(Character.toChars(0x1F4A3));

    @FXML
    public void initialize() {
	renderNewGame(false, "");
    }

    @FXML
    public void handleRestartButton() {
	renderNewGame(false, "");
    }

    private void renderNewGame(boolean isLoadGame, String data) {
	if (isLoadGame) {
	    board = new Board(data);
	} else {
	    board = new Board();
	}
	msgrid.getChildren().clear();
	addButtons();
	updateRemainingBombs();
	updateStatusField("");
    }

    private void addButtons() {
	int id = 0;
	for (int i = 0; i < 10; i++) {
	    for (int j = 0; j < 10; j++) {
		createButton(id, j, i, "");
		id++;
	    }
	}
    }

    private void createButton(int id, int posX, int posY, String buttontext) {
	Button button = new Button();
	button.setMaxWidth(50.0);
	button.setMaxHeight(50.0);
	button.setStyle("-fx-font-size:15; -fx-background-color: #BFBFBF; -fx-font-weight: bold");
	button.setText(buttontext);
	button.setTextFill(Color.RED);
	button.setOnMouseClicked(e -> handleClick(e));
	button.setId(Integer.toString(id));
	msgrid.add(button, posX, posY);
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
		((Button) source).setText(flag);
	    } else {
		((Button) source).setText("");
	    }
	    board.getSquares().get(squareId).setIsEditable();
	    board.getSquares().get(squareId).setIsFlagged();
	    updateRemainingBombs();
	}

	if (isGameWon()) {
	    updateEndGame("YOU WON", "?");
	}
    }

    private void checkSquare(int id, int posX, int posY) {

	String text;

	if (board.getSquares().get(id).getIsEditable()) {
	    if (board.getSquares().get(id).getIsBomb()) {
		updateEndGame("YOU LOST", "X");
	    } else {

		int numOfBombs = board.numberOfBombsNearby(posX, posY);

		if (numOfBombs == 0) {
		    text = "";
		    changeSquare(id, posX, posY, text, Color.WHITE);
		    board.getSquares().get(id).setIsEditable();
		    disableButton(id, false);
		    openNearbySquares(id, posX, posY);
		} else {
		    text = Integer.toString(numOfBombs);
		    Color color = getColor(numOfBombs);
		    changeSquare(id, posX, posY, text, color);
		    board.getSquares().get(id).setIsEditable();
		    disableButton(id, false);
		}
	    }
	}
    }

    private void changeSquare(int id, int posX, int posY, String text, Color color) {
	Label label = new Label();
	label.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
	label.setMaxWidth(50.0);
	label.setMaxHeight(50.0);
	label.setTextAlignment(TextAlignment.CENTER);
	label.setAlignment(Pos.CENTER);
	label.setText(text);
	label.setTextFill(color);
	msgrid.add(label, posX, posY);
    }

    private void disableButton(int id, boolean buttonVisible) {
	for (Node node : msgrid.getChildren()) {
	    if (node.getId() != null) {
		if (Integer.parseInt(node.getId()) == id) {
		    node.setDisable(true);
		    if (buttonVisible) {
			node.setOpacity(1);
		    } else {
			node.setOpacity(0.3);
		    }
		}
	    }
	}
    }

    private void openNearbySquares(int id, int posX, int posY) {
	for (int x = -1; x < 2; x++) {
	    for (int y = -1; y < 2; y++) {
		int nX = posX + x;
		int nY = posY + y;
		if ((nX < 10 && nX >= 0) && (nY < 10 && nY >= 0) && !(posX == nX && posY == nY)
			&& !(board.getSquares().get((10 * nY) + nX).getIsBomb())) {
		    checkSquare((10 * nY) + nX, nX, nY);
		}
	    }
	}
    }

    private Color getColor(int num) {
	Color color;
	ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.BLUE, Color.GREEN, Color.RED, Color.DARKBLUE,
		Color.DARKRED, Color.CYAN, Color.YELLOW, Color.BLACK));
	color = colors.get(num - 1);
	return color;
    }

    private void updateEndGame(String gameStatus, String squareText) {
	board.getSquares().stream().forEach(sq -> {
	    int id = board.getSquares().indexOf(sq);
	    int posX = id % 10;
	    int posY = id / 10;
	    if (sq.getIsBomb()) {
		changeSquare(id, posX, posY, squareText, Color.BLACK);
		disableButton(id, true);
	    } else {
		if (sq.getIsEditable() || sq.getIsFlagged()) {
		    disableButton(id, true);
		} else {
		    disableButton(id, false);
		}
	    }
	});
	updateStatusField(gameStatus);
    }

    private boolean isGameWon() {
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

    @FXML
    public void handleSaveButton() {
	saveToFile(filename, board);
    }

    @FXML
    public void handleLoadButton() {
	renderNewGame(true, loadFile(filename));
	updateLoadedGame();
    }

    private void updateLoadedGame() {
	msgrid.getChildren().clear();
	board.getSquares().stream().forEach(sq -> {
	    int id = board.getSquares().indexOf(sq);
	    int posX = id % 10;
	    int posY = id / 10;
	    if (sq.getIsEditable()) {
		createButton(id, posX, posY, "");
	    } else {
		if (sq.getIsFlagged()) {
		    createButton(id, posX, posY, "?");
		} else {
		    if (sq.getIsBomb()) {
			createButton(id, posX, posY, "");
			disableButton(id, false);
			changeSquare(id, posX, posY, "X", Color.BLACK);
		    } else {
			createButton(id, posX, posY, "");
			disableButton(id, false);
			if (board.numberOfBombsNearby(posX, posY) == 0) {
			    changeSquare(id, posX, posY, "", Color.WHITE);
			} else {
			    int numOfBombs = board.numberOfBombsNearby(posX, posY);
			    changeSquare(id, posX, posY, Integer.toString(numOfBombs), getColor(numOfBombs));
			}

		    }
		}
	    }
	});
    }

}
