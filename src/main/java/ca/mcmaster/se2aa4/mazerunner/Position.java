package ca.mcmaster.se2aa4.mazerunner;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    // Check if two positions are equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return row == position.row && col == position.col;
    }

    // Generate a hash code for hashing structures
    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    // Return position in (x, y) format
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
