package ca.mcmaster.se2aa4.mazerunner;

public class Maze {
    private Character[][] grid;
    private int entryRow;
    private int entryCol = 0; // Assuming entryCol always occurs at column 0.
    private int exitRow;
    private int exitCol; // Taking the first row as a reference and assuming all rows are the same size.

    // Constructor
    public Maze(Character[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid cannot be null or empty");
        }
        this.exitCol = grid[this.entryCol].length - 1;
        this.grid = grid;
    }

    // Get entry row assuming the entry row always occurs at the leftmost column, which is column 0 and there is only one entry point.
    public int getEntryRow() {
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][entryCol] == ' ') {
                entryRow = i;
                return entryRow;
            }
        }
        throw new IllegalStateException("No entry point found at column 0");
    }

    // Getter method to get the entry column
    public int getEntryCol() {
        return this.entryCol;
    }

    // Get exit row assuming the exit row always occurs at the rightmost column
    public int getExitRow() {
        // Taking the first row as a reference and assuming all rows are the same size.
        for (int j = 0; j < grid.length; j++) {
            if (grid[j][exitCol] == ' ') {
                exitRow = j;
                return exitRow;
            }
        }
        throw new IllegalStateException("No exit point found at the rightmost column.");
    }

    // Getter method to get the exit column
    public int getExitCol() {
        return this.exitCol;
    }

    // Getter method to get the 2D grid
    public Character[][] getGrid() {
        return this.grid;
    }

    // Check if a given position is the exit
    public boolean isExit(int row, int col) {
        return row == getExitRow() && col == getExitCol();
    }

    // Check if a move to a given position is valid
    // row and col need to be between 0 and (grid's length - 1)
    // Plus, the position must be an empty space and not a wall
    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && grid[row][col] == ' ';
    }

    StringBuilder mazeLook = new StringBuilder("\n");
    // Method to print the maze (for visualization and verification)
    public String printMaze() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                mazeLook.append(grid[i][j] + " ");
            }
            mazeLook.append("\n");
        }
        return mazeLook.toString();
    }
}
