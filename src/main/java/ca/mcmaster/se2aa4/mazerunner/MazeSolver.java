package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MazeSolver {
    private static final Logger logger = Logger.getLogger(MazeSolver.class.getName());
    private Maze maze;
    private Position currentPosition;
    private Direction currentDirection;
    private List<String> pathTaken; // List to track the path taken
    private StringBuilder finalOutput;
    private Position lastPosition;  // Tracks the last logged position
    private Direction lastDirection; // Tracks the last logged direction

    public MazeSolver(Maze maze) {
        this.maze = maze;
        this.currentPosition = new Position(maze.getEntryRow(), maze.getEntryCol());
        this.currentDirection = Direction.RIGHT;  // Start facing right
        this.pathTaken = new ArrayList<>();    // Initialize the path tracker
        this.finalOutput = new StringBuilder(); // Final output of the path in canonical form
        this.lastPosition = null;  // Initialize to null to track first position
        this.lastDirection = null; // Initialize to null to track first direction
    }

    public boolean hasReachedEnd() {
        return maze.isExit(currentPosition.getRow(), currentPosition.getCol());
    }

    public boolean solve() throws Exception {
        while (!hasReachedEnd()) {
            if (canMoveRight()) {
                turnRight();
            } else if (canMoveForward()) {
                moveForward();
            } else if (canMoveLeft()) {
                turnLeft();
            } else {
                break;
            }
        }
        return hasReachedEnd();
    }

    private boolean canMoveForward() {
        Position nextPosition = getNextPosition(currentDirection);
        return maze.isValidMove(nextPosition.getRow(), nextPosition.getCol());
    }

    private boolean canMoveRight() {
        Direction rightDirection = getTurnedDirection(Direction.RIGHT);
        Position nextPosition = getNextPosition(rightDirection);
        return maze.isValidMove(nextPosition.getRow(), nextPosition.getCol());
    }

    private boolean canMoveLeft() {
        Direction leftDirection = getTurnedDirection(Direction.LEFT);
        Position nextPosition = getNextPosition(leftDirection);
        return maze.isValidMove(nextPosition.getRow(), nextPosition.getCol());
    }

    private void moveForward() {
        currentPosition = getNextPosition(currentDirection);
        logStep("F");
    }

    private void turnRight() {
        currentDirection = getTurnedDirection(Direction.RIGHT);
        logStep("R");
    }

    private void turnLeft() {
        currentDirection = getTurnedDirection(Direction.LEFT);
        logStep("L");
    }

    private Direction getTurnedDirection(Direction turn) {
        switch (turn) {
            case RIGHT:
                return switch (currentDirection) {
                    case UP -> Direction.RIGHT;
                    case RIGHT -> Direction.DOWN;
                    case DOWN -> Direction.LEFT;
                    case LEFT -> Direction.UP;
                };
            case LEFT:
                return switch (currentDirection) {
                    case UP -> Direction.LEFT;
                    case LEFT -> Direction.DOWN;
                    case DOWN -> Direction.RIGHT;
                    case RIGHT -> Direction.UP;
                };
            default:
                return currentDirection;
        }
    }

    private Position getNextPosition(Direction direction) {
        int row = currentPosition.getRow();
        int col = currentPosition.getCol();
        return switch (direction) {
            case UP -> new Position(row - 1, col);
            case DOWN -> new Position(row + 1, col);
            case LEFT -> new Position(row, col - 1);
            case RIGHT -> new Position(row, col + 1);
        };
    }

    private void logStep(String action) {
        // Prevent logging the same position and direction repeatedly
        if (lastPosition != null && lastPosition.equals(currentPosition) && lastDirection == currentDirection) {
            return;
        }

        lastPosition = new Position(currentPosition.getRow(), currentPosition.getCol());
        lastDirection = currentDirection;
        pathTaken.add("Position: (" + currentPosition.getRow() + ", " + currentPosition.getCol() + "), Direction: " + action);
        finalOutput.append(action);
    }

    public List<String> getPathTaken() {
        return pathTaken;
    }

    public String getFinalOutput() {
        return finalOutput.toString();
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
