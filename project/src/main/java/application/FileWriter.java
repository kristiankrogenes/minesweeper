package application;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileWriter implements FileController {

	// Lagrer gametilstanden med tre attributter. Om et felt er en bombe, flagga
	// eller editable
	// Lagres f.eks. som dette: "true-false-false"
	public boolean saveToFile(String filename, Board board) {

		boolean saveStatus = true;

		try {
			File newfile = new File(filename);
			PrintWriter writer = new PrintWriter(newfile);

			board.getSquares().stream().forEach(sq -> {
				String squaredata = sq.getIsBomb() + "-" + sq.getIsEditable() + "-" + sq.getIsFlagged() + ",";
				writer.println(squaredata);
			});
			writer.close();

		} catch (Exception e) {
			saveStatus = false;
		}
		return saveStatus;
	}

	public String loadFile(String filename) {
		String fileData = "";

		try {
			Scanner filescanner = new Scanner(new FileReader(filename));
			while (filescanner.hasNext()) {
				fileData += filescanner.next();
			}
			filescanner.close();
		} catch (Exception e) {
			fileData = null;
		}

		return fileData;
	}

}
