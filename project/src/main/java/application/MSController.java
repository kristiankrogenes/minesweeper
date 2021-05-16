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

public class MSController {

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

	private FileWriter fileWriter = new FileWriter();
	private Game game;
	private String filename = "msdata.txt";
	private String flagString = new String(Character.toChars(0x1F6A9));
	private String bombString = new String(Character.toChars(0x1F4A3));

	// Initialiserer et nytt game
	@FXML
	public void initialize() {
		renderNewGame(false, "");
	}

	// Restart et nytt game
	@FXML
	public void handleRestartButton() {
		renderNewGame(false, "");
	}

	// Lager nytt gameobjekt, og rendrer brettet
	private void renderNewGame(boolean isLoadGame, String data) {
		if (isLoadGame) {
			game = new Game(data);
		} else {
			game = new Game();
		}
		msgrid.getChildren().clear();
		game.updateRemainingBombs();
		addButtons();
		updateBombCountField();
		updateStatusField("");
		updateGame();
	}

	// Oppdaterer bomb count visuelt
	private void updateBombCountField() {
		bombCountField.setText("REMAINING BOMBS:	" + Integer.toString(game.getRemainingBombs()));
	}

	// Oppdateres ved tap eller vinn, også ved lagring og loading av fil
	private void updateStatusField(String text) {
		statusField.setText(text);
	}

	// Hjelpemetode for å legge til alle knappene
	private void addButtons() {
		int id = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				createButton(id, j, i, "");
				id++;
			}
		}
	}

	// Lager en ny button med riktig spesifikasjoner og legger til i gridpanen
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

	// Lager en ny label med riktig spesifikasjoner og legger til i gridpanen
	private void createLabel(int id, int posX, int posY, String text, Color color) {
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

	// Håndterer klikk på brettet, oppdaterer gameobjektet deretter
	@FXML
	public void handleClick(MouseEvent e) {
		Node source = (Node) e.getSource();
		int squareId = Integer.parseInt(source.getId());
		if (e.getButton() == MouseButton.PRIMARY) {
			game.openSquare(squareId);
		} else if (e.getButton() == MouseButton.SECONDARY) {
			game.flagSquare(squareId);
		}
		updateGame();
	}

	// Iterer gjennom brettet og rendrer de ulike spesifikasjonene
	private void updateGame() {
		msgrid.getChildren().clear();
		game.getBoard().getSquares().stream().forEach(sq -> {
			int id = game.getBoard().getSquares().indexOf(sq);
			int posX = id % 10;
			int posY = id / 10;

			if (sq.getIsEditable()) {
				createButton(id, posX, posY, "");
			} else {
				if (sq.getIsFlagged()) {
					createButton(id, posX, posY, flagString);
				} else {
					if (sq.getIsBomb()) {
						createButton(id, posX, posY, "");
						disableButton(id, false);
						createLabel(id, posX, posY, bombString, Color.BLACK);
					} else {
						createButton(id, posX, posY, "");
						disableButton(id, false);
						if (game.numberOfBombsNearby(id) == 0) {
							createLabel(id, posX, posY, "", Color.WHITE);
						} else {
							int numOfBombs = game.numberOfBombsNearby(id);
							createLabel(id, posX, posY, Integer.toString(numOfBombs), getColor(numOfBombs));
						}
					}
				}
			}
		});
		checkGameStatus();
		updateBombCountField();

	}

	// Sjekker om du har vunnet eller tapt etter hvert klikk
	private void checkGameStatus() {
		if (game.isGameWon()) {
			updateEndGame("YOU WON", flagString);
		} else if (game.getIsGameLost()) {
			updateEndGame("YOU LOST", bombString);
		}
	}

	// Hjelpemetode for å gjøre knappene utilgjengelige
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

	// Rendrer brettet visuelt ved end game. �pner alle bombene.
	private void updateEndGame(String gameStatus, String squareText) {
		game.getBoard().getSquares().stream().forEach(sq -> {
			int id = game.getBoard().getSquares().indexOf(sq);
			int posX = id % 10;
			int posY = id / 10;
			if (sq.getIsBomb()) {
				createLabel(id, posX, posY, squareText, Color.BLACK);
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

	// Loader et lagret game
	@FXML
	public void handleLoadButton() {
		String str = fileWriter.loadFile(filename);
		
		if (str == null) {
			updateStatusField("COULD NOT LOAD GAME");
		} else {
			renderNewGame(true, str);
			updateGame();
			updateStatusField("SUCCESSFULLY LOADED GAME");
		}
	}

	// Saver et game
	@FXML
	public void handleSaveButton() {
		boolean isGameSaved = fileWriter.saveToFile(filename, game.getBoard());
		if (isGameSaved) {
			updateStatusField("SUCSESSFULLY SAVED GAME");
		} else {
			updateStatusField("COULD NOT SAVE GAME");
		}
	}

	// Hjelpemetode for å style feltene med bombs nearby
	public Color getColor(int num) {
		Color color;
		ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.BLUE, Color.GREEN, Color.RED, Color.DARKBLUE,
				Color.DARKRED, Color.CYAN, Color.YELLOW, Color.BLACK));
		color = colors.get(num - 1);
		return color;
	}

}
