package ca.mcmaster.se2aa4.mazerunner;
import java.io.IOException;

public class Maze {
    // Private attributes
    private char[][] mazeData;

    // Constructor that reads maze from a file
    public Maze(String filename) throws IOException {

    }

    // Extracts value of a cell based on the specific coordinates
    public char getMazeData(int row, int col) {
        return mazeData[row][col];
    }
}