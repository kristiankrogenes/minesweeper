package application;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileWriter implements FileController {

    public void saveToFile(String filename, Board board) {

	try {
	    File newfile = new File(filename);
	    PrintWriter writer = new PrintWriter(newfile);

	    board.getSquares().stream().forEach(sq -> {
		/*
		 * int isBomb = 0; int isEditable = 0; int isFlagged = 0;
		 * 
		 * if (sq.getIsBomb()) { isBomb = 1; } if (sq.getIsEditable()) { isEditable = 1;
		 * } if (sq.getIsFlagged()) { isFlagged = 1; }
		 */
		String squaredata = sq.getIsBomb() + "-" + sq.getIsEditable() + "-" + sq.getIsFlagged() + ",";
		writer.println(squaredata);
	    });
	    writer.close();

	} catch (Exception e) {
	    System.out.println("FEIL");
	}
	System.out.println("SAVED");
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
