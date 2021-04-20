package application;

public interface FileController {

    public void saveToFile(String filename, Board board);

    public String loadFile(String filename);

}
