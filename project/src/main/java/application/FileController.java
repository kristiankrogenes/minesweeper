package application;

public interface FileController {

    public boolean saveToFile(String filename, Board board);

    public String loadFile(String filename);

}
