package application;

import java.io.File;
import java.io.PrintWriter;

public interface FileController {
	
	public default void saveToFile(String filename, Board board) {
		
		try {
			File newfile = new File(filename);
			PrintWriter writer = new PrintWriter(newfile);
			
			board.getSquares().stream().forEach(sq -> {
				int isBomb = 0;
				int isEditable = 0;
				int isFlagged = 0;
				
				if (sq.getIsBomb()) {
					isBomb = 1;
				}
				if (sq.getIsEditable()) {
					isEditable = 1;
				}
				if (sq.getIsFlagged()) {
					isFlagged = 1;
				}
				String squaredata = isBomb + "" + isEditable + "" + isFlagged +",";
				writer.println(squaredata);
			});
			writer.close();
			
		} catch(Exception e) {
			System.out.println("FEIL");
			
		}
		System.out.println("SAVED");
	}

}
