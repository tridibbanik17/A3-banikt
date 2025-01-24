package ca.mcmaster.se2aa4.mazerunner;
import java.util.logging.Logger;

public class MazeSolver {
    private static final Logger logger = Logger.getLogger(MazeSolver.class.getName());
    private Maze maze;
    private Position currentPosition;
    private Direction currentDirection;

    // Direction enum to represent movement directions (UP, DOWN, LEFT, RIGHT)
    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // Constructor method
    public MazeSolver(Maze maze) {
        this.maze = maze;
        this.currentPosition = new Position(maze.getEntryRow(), maze.getEntryCol());
        this.currentDirection = Direction.RIGHT;  // Start facing Right
    }

    // Process the movement using the Right-Hand Rule
    public boolean solve() throws Exception {
        while (!hasReachedEnd()) {
            // Try to move with the Right-Hand Rule: check if the right side is clear
            if (canMoveRight()) {
                turnRight();
                moveForward();
            } else if (canMoveForward()) {
                moveForward();
            } else if (canMoveLeft()) {
                turnLeft();
                moveForward();
            } else {
                logger.warning("No valid moves left, path not found");
                throw new Exception("No valid path found, maze cannot be solved.");
            }
        }
        return true; // Exit found
    }

    // Top-left is the origin (0, 0) -> row 0, col 0
    // The cell immediately right to the origin is (0, 1) -> row 0, col 1
    // So, col number increases while going right
    // The cell immediately below the origin is (1, 0) -> row 1, col 0
    // So, row number increases while going down

    // Check if you can move forward (there is no wall)
    private boolean canMoveForward() {
        int newRow = currentPosition.getRow();
        int newCol = currentPosition.getCol();
        switch (currentDirection) {
            case UP: newRow--; break;
            case DOWN: newRow++; break;
            case LEFT: newCol--; break;
            case RIGHT: newCol++; break;
        }
        return maze.isValidMove(newRow, newCol);
    }

    // Check if you can move right (turn right, then move forward)
    private boolean canMoveRight() {
        turnRight();
        boolean canMove = canMoveForward();
        turnLeft(); // turn back to original direction after checking
        return canMove;
    }

    // Check if you can move left (turn left, then move forward)
    private boolean canMoveLeft() {
        turnLeft();
        boolean canMove = canMoveForward();
        turnRight(); // turn back to original direction after checking
        return canMove;
    }

    // Move forward in the current direction
    private void moveForward() {
        int newRow = currentPosition.getRow();
        int newCol = currentPosition.getCol();

        switch (currentDirection) {
            case UP: newRow--; break;
            case DOWN: newRow++; break;
            case LEFT: newCol--; break;
            case RIGHT: newCol++; break;
        }

        // Update the position using the Position class
        currentPosition.setRow(newRow);
        currentPosition.setCol(newCol);
    }

    // Turn right (90 degrees)
    private void turnRight() {
        switch (currentDirection) {
            case UP: currentDirection = Direction.RIGHT; break;
            case RIGHT: currentDirection = Direction.DOWN; break;
            case DOWN: currentDirection = Direction.LEFT; break;
            case LEFT: currentDirection = Direction.UP; break;
        }
    }

    // Turn left (90 degrees)
    private void turnLeft() {
        switch (currentDirection) {
            case UP: currentDirection = Direction.LEFT; break;
            case LEFT: currentDirection = Direction.DOWN; break;
            case DOWN: currentDirection = Direction.RIGHT; break;
            case RIGHT: currentDirection = Direction.UP; break;
        }
    }

    // Check if the solver has reached the exit
    public boolean hasReachedEnd() {
        return maze.isExit(currentPosition.getRow(), currentPosition.getCol());
    }

    // Get current position (for debugging)
    public Position getCurrentPosition() {
        return currentPosition;
    }

    // Get current direction (for debugging)
    public Direction getCurrentDirection() {
        return currentDirection;
    }
}
