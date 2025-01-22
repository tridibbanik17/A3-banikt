package ca.mcmaster.se2aa4.mazerunner;

public class MazeSolver {
    private Maze maze;
    private int currentRow;
    private int currentCol;

    // Constructor to initialize MazeSolver with a maze
    public MazeSolver(Maze maze) {
        this.maze = maze;
        // Initialize current row and col based on the maze
        // this.currentRow = 0;
        // this.currentCol = 0;
    }

    // Method to move the MazeSolver in a specific direction (either up, down, right or left)
    public boolean move(Direction direction) throws InvalidMoveException {
        // Check if the move is valid based on the maze boundaries
        // Update the current position if the move is valid.
        return true; // If valid move
    }

    // Check if the MazaSolver arrived the exit point
    public boolean hasReachedEnd() {
        // Determine if the current position is the exit of the maze
        return true; // If the current position is exit.
    }
}