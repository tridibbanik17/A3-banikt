package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The MazeSolver class is responsible for solving a maze using the right-hand rule.
 * It navigates through the maze and keeps track of the path taken and the canonical output path.
 */
public class MazeSolver {
    private static final Logger logger = Logger.getLogger(MazeSolver.class.getName());
    
    // Represents the maze to be solved
    private Maze maze;
    
    // Tracks the current position of the solver in the maze
    private Position currentPosition;
    
    // Tracks the current direction of movement in the maze
    private Direction currentDirection;
    
    // Stores the steps taken during the maze-solving process
    private List<String> pathTaken;
    
    // Stores the canonical path as a sequence of "F", "L", and "R"
    private StringBuilder finalOutput;
    
    // Tracks the last logged position to prevent duplicate entries in the path log
    private Position lastPosition;
    
    // Tracks the last logged direction to prevent duplicate entries in the path log
    private Direction lastDirection;

    /**
     * Constructor for MazeSolver.
     * Initializes the maze, starting position, direction, and path trackers.
     * 
     * @param maze The maze to be solved.
     */
    public MazeSolver(Maze maze) {
        this.maze = maze;
        this.currentPosition = new Position(maze.getEntryRow(), maze.getEntryCol());
        this.currentDirection = Direction.RIGHT; // Start facing right
        this.pathTaken = new ArrayList<>(); // Initialize the path tracker
        this.finalOutput = new StringBuilder(); // Store the canonical path
        this.lastPosition = null; // Used to track duplicate entries
        this.lastDirection = null; // Used to track duplicate entries
    }

    /**
     * Checks if the current position is at the exit of the maze.
     * 
     * @return true if the current position is the exit, false otherwise.
     */
    public boolean hasReachedEnd() {
        return maze.isExit(currentPosition.getRow(), currentPosition.getCol());
    }

    /**
     * Solves the maze using the right-hand rule. Continues until the exit is reached or no more valid moves exist.
     * 
     * @return true if the maze is solved, false otherwise.
     * @throws Exception if an error occurs during the maze-solving process.
     */
    public boolean solve() throws Exception {
        while (!hasReachedEnd()) {
            if (canMoveRight()) {
                turnRight();
            } else if (canMoveForward()) {
                moveForward();
            } else if (canMoveLeft()) {
                turnLeft();
            } else {
                break; // No valid moves left
            }
        }
        return hasReachedEnd();
    }

    /**
     * Checks if the solver can move forward in the current direction.
     * 
     * @return true if the forward move is valid, false otherwise.
     */
    private boolean canMoveForward() {
        Position nextPosition = getNextPosition(currentDirection);
        return maze.isValidMove(nextPosition.getRow(), nextPosition.getCol());
    }

    /**
     * Checks if the solver can turn right and move in that direction.
     * 
     * @return true if the right move is valid, false otherwise.
     */
    private boolean canMoveRight() {
        Direction rightDirection = getTurnedDirection(Direction.RIGHT);
        Position nextPosition = getNextPosition(rightDirection);
        return maze.isValidMove(nextPosition.getRow(), nextPosition.getCol());
    }

    /**
     * Checks if the solver can turn left and move in that direction.
     * 
     * @return true if the left move is valid, false otherwise.
     */
    private boolean canMoveLeft() {
        Direction leftDirection = getTurnedDirection(Direction.LEFT);
        Position nextPosition = getNextPosition(leftDirection);
        return maze.isValidMove(nextPosition.getRow(), nextPosition.getCol());
    }

    /**
     * Moves the solver forward in the current direction.
     */
    private void moveForward() {
        currentPosition = getNextPosition(currentDirection);
        logStep("F");
    }

    /**
     * Turns the solver to the right and updates the direction.
     */
    private void turnRight() {
        currentDirection = getTurnedDirection(Direction.RIGHT);
        logStep("R");
    }

    /**
     * Turns the solver to the left and updates the direction.
     */
    private void turnLeft() {
        currentDirection = getTurnedDirection(Direction.LEFT);
        logStep("L");
    }

    /**
     * Calculates the direction after a turn (left or right).
     * 
     * @param turn The direction to turn (LEFT or RIGHT).
     * @return The new direction after the turn.
     */
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

    /**
     * Calculates the next position based on the current position and direction.
     * 
     * @param direction The direction to move in.
     * @return The next position after moving in the specified direction.
     */
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

    /**
     * Logs the current step in the canonical path and avoids duplicate entries.
     * 
     * @param action The action taken ("F", "L", or "R").
     */
    private void logStep(String action) {
        if (lastPosition != null && lastPosition.equals(currentPosition) && lastDirection == currentDirection) {
            return; // Prevent duplicate logging
        }

        lastPosition = new Position(currentPosition.getRow(), currentPosition.getCol());
        lastDirection = currentDirection;
        pathTaken.add("Position: (" + currentPosition.getRow() + ", " + currentPosition.getCol() + "), Direction: " + action);
        finalOutput.append(action);
    }

    /**
     * Retrieves the detailed path taken by the solver.
     * 
     * @return A list of steps taken during the maze-solving process.
     */
    public List<String> getPathTaken() {
        return pathTaken;
    }

    /**
     * Retrieves the canonical path as a sequence of "F", "L", and "R".
     * 
     * @return The canonical path string.
     */
    public String getFinalOutput() {
        return finalOutput.toString();
    }

    /**
     * Enum representing possible directions in the maze.
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
