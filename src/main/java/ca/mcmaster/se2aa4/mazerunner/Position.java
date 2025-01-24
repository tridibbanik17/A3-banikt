package ca.mcmaster.se2aa4.mazerunner;

public class Position {
    private int row;
    private int col;

    // Constructor
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Getter methods
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    // Setter methods
    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    // Return the current position in the form of (x,y) where x is the row number and y is the column number.
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
