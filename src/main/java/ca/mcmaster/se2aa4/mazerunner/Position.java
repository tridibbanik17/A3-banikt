package ca.mcmaster.se2aa4.mazerunner;


public class Position {
    private int row;
    private int col;
    private char type; // Empty space or Wall

    // Constructor that initializes cell with its coordinates and types
    public Position(int row, int col, char type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    // Getter methods for row, col and type
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getType() {
        return type;
    }
}