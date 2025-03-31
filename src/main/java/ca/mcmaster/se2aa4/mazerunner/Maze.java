package ca.mcmaster.se2aa4.mazerunner;

public class Maze {
    private final Character[][] grid;
    private final int entryRow;
    private final int entryCol = 0; // Entry always at column 0
    private final int exitRow;
    private final int exitCol;
    private boolean solved;

    public Maze(Character[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid cannot be null or empty");
        }
        this.grid = grid;
        this.entryRow = findEntryRow();
        this.exitCol = grid[0].length - 1;
        this.exitRow = findExitRow();
        this.solved = false; 
    }

    // Set the maze as solved
    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    // Check if the maze is solved
    public boolean isSolved() {
        return solved;
    }

    private int findEntryRow() {
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][entryCol] == ' ') {
                return i;
            }
        }
        throw new IllegalStateException("No entry point found at column 0");
    }

    private int findExitRow() {
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][exitCol] == ' ') {
                return i;
            }
        }
        throw new IllegalStateException("No exit point found at the rightmost column.");
    }

    public int getEntryRow() { return entryRow; }
    public int getEntryCol() { return entryCol; }
    public int getExitRow() { return exitRow; }
    public int getExitCol() { return exitCol; }
    public Character[][] getGrid() { return grid; }

    public boolean isExit(int row, int col) {
        return row == exitRow && col == exitCol;
    }

    public char returnCellValue(int row, int col) {
        return grid[row][col];
    }

    // Valid change in position while moving forward (Change in position can only occur while moving forward)
    public boolean isValidMove(Position position, Direction direction) {
        int row = position.getRow();
        int col = position.getCol();

        return switch (direction) {
            case NORTH -> row > 0 && grid[row - 1][col] == ' ';
            case SOUTH -> row < grid.length - 1 && grid[row + 1][col] == ' ';
            case EAST -> col < grid[0].length - 1 && grid[row][col + 1] == ' ';
            case WEST -> col > 0 && grid[row][col - 1] == ' ';
            default -> false;
        };
    }

    public String printMaze() {
        StringBuilder sb = new StringBuilder();
        for (Character[] row : grid) {
            for (Character cell : row) {
                sb.append(cell).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}