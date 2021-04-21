package application;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileWriter implements FileController {

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
			System.out.println("FEIL");
			saveStatus = false;
		}
		System.out.println("SAVED");
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
