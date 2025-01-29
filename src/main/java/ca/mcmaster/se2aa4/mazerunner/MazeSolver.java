package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ca.mcmaster.se2aa4.mazerunner.MazeSolver.Direction;

/**
 * The MazeSolver class is responsible for solving a maze using the EAST-hand rule.
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

    // private Action currentAction;
    
    // Stores the steps taken during the maze-solving process
    private List<String> pathTaken;
    
    // Stores the canonical path as a sequence of "F", "L", and "R"
    private StringBuilder finalOutput;
    
    // Tracks the last logged position to prevent dNORTHlicate entries in the path log
    private Position lastPosition;
    
    // Tracks the last logged direction to prevent dNORTHlicate entries in the path log
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
        this.currentDirection = Direction.EAST; // Start facing EAST
        // this.currentAction = Action.FORWARD;
        this.pathTaken = new ArrayList<>(); // Initialize the path tracker
        this.finalOutput = new StringBuilder(""); // Store the canonical path
        this.lastPosition = null; // Used to track dNORTHlicate entries
        this.lastDirection = null; // Used to track dNORTHlicate entries
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
     * Solves the maze using the EAST-hand rule. Continues until the exit is reached or no more valid moves exist.
     * 
     * @return true if the maze is solved, false otherwise.
     * @throws Exception if an error occurs during the maze-solving process.
     */
    public boolean solve() throws Exception {
        while (!hasReachedEnd()) {
            if (canMoveForward()) {
                moveForward();
            } else if (canTurnAround()) {
                turnAround(); 
                moveForward();
            } else if (canTurnEAST()) {
                turnEAST();
                moveForward();
            } else if (canTurnWEST()) {
                turnWEST();
                moveForward();
            } else {
                break;
            }
        }
        return hasReachedEnd();
    }

    /**
     * Enum representing possible directions in the maze.
     */
    public enum Direction {
        EAST, WEST, NORTH, SOUTH
    }

    // public enum Action {
    //     FORWARD, EAST, WEST
    // }

    /**
     * Checks if the solver can move forward in the current direction.
     * 
     * @return true if the forward move is valid, false otherwise.
     */
    private boolean canMoveForward() {
        // Position nextPosition = getNextPosition(currentDirection);
        if (currentDirection == Direction.NORTH) {
            if ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() + 1) == '#') && ((maze.returnCellValue(currentPosition.getRow() - 1, currentPosition.getCol())) == ' ')) {
                return true;
            }
        }
        else if (currentDirection == Direction.SOUTH) {
            if ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() - 1) == '#') && ((maze.returnCellValue(currentPosition.getRow() + 1, currentPosition.getCol())) == ' ')){
                return true;
            }
        }
        else if (currentDirection == Direction.EAST) {
            if ((maze.returnCellValue(currentPosition.getRow() + 1, currentPosition.getCol()) == '#') && ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() + 1)) == ' ')){
                return true;
            }
        }
        else if (currentDirection == Direction.WEST) {
            if ((maze.returnCellValue(currentPosition.getRow() - 1, currentPosition.getCol()) == '#') && ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() - 1)) == ' ')){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the solver can turn EAST and move in that direction.
     * 
     * @return true if the EAST move is valid, false otherwise.
     */
    private boolean canTurnEAST() {
        if (!canMoveForward()) {
            if (currentDirection == Direction.NORTH) {
                if ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() + 1) == ' ')){
                    return true;
                }
            }
            else if (currentDirection == Direction.SOUTH) {
                if ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() - 1) == ' ')){
                    return true;
                }
            }
            else if (currentDirection == Direction.EAST) {
                if ((maze.returnCellValue(currentPosition.getRow() + 1, currentPosition.getCol()) == ' ')){
                    return true;
                }
            }
            else if (currentDirection == Direction.WEST) {
                if ((maze.returnCellValue(currentPosition.getRow() - 1, currentPosition.getCol()) == ' ')){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the solver can turn WEST and move in that direction.
     * 
     * @return true if the WEST move is valid, false otherwise.
     */
    private boolean canTurnWEST() {
        if (currentDirection == Direction.NORTH) {
            if ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() + 1) == '#') && ((maze.returnCellValue(currentPosition.getRow() - 1, currentPosition.getCol())) == '#')){
                return true;
            }
        }
        else if (currentDirection == Direction.SOUTH) {
            if ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() - 1) == '#') && ((maze.returnCellValue(currentPosition.getRow() + 1, currentPosition.getCol())) == '#')){
                return true;
            }
        }
        else if (currentDirection == Direction.EAST) {
            if ((maze.returnCellValue(currentPosition.getRow() + 1, currentPosition.getCol()) == '#') && ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() + 1)) == '#')){
                return true;
            }
        }
        else if (currentDirection == Direction.WEST) {
            if ((maze.returnCellValue(currentPosition.getRow() - 1, currentPosition.getCol()) == '#') && ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() - 1)) == '#')){
                return true;
            }
        }
        return false;
    }

    private boolean canTurnAround() {
        if (currentDirection == Direction.NORTH) {
            if ((maze.returnCellValue(currentPosition.getRow() - 1, currentPosition.getCol()) == '#') && (maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() - 1) == '#') && (maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() + 1) == '#')) {
                return true;
            }
        } else if (currentDirection == Direction.SOUTH) {
            if ((maze.returnCellValue(currentPosition.getRow() + 1, currentPosition.getCol()) == '#') && (maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() - 1) == '#') && (maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() + 1) == '#')) {
                return true;
            }
        } else if (currentDirection == Direction.EAST) {
            if ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() + 1) == '#') && (maze.returnCellValue(currentPosition.getRow() - 1, currentPosition.getCol()) == '#') && (maze.returnCellValue(currentPosition.getRow() + 1, currentPosition.getCol()) == '#')) {
                return true;
            }
        } else if (currentDirection == Direction.WEST) {
            if ((maze.returnCellValue(currentPosition.getRow(), currentPosition.getCol() - 1) == '#') && (maze.returnCellValue(currentPosition.getRow() - 1, currentPosition.getCol()) == '#') && (maze.returnCellValue(currentPosition.getRow() + 1, currentPosition.getCol()) == '#')) {
                return true;
            }
        }
        return false;
    }

    private void turnAround() {
        currentDirection = switch (currentDirection) {
            case NORTH -> Direction.SOUTH;
            case SOUTH -> Direction.NORTH;
            case EAST -> Direction.WEST;
            case WEST -> Direction.EAST;
        };
        logStep("R"); // Turning right twice to simulate a 180-degree turn
        logStep("R");
    }


    /**
     * Moves the solver forward in the current direction.
     */
    private void moveForward() {
        currentPosition = getNextPosition(currentDirection);
        logStep("F");
    }

    /**
     * Turns the solver to the EAST and NORTHdates the direction.
     */
    private void turnEAST() {
        currentDirection = getTurnedDirection(Direction.EAST);
        logStep("R");
    }

    /**
     * Turns the solver to the WEST and NORTHdates the direction.
     */
    private void turnWEST() {
        currentDirection = getTurnedDirection(Direction.WEST);
        logStep("L");
    }

    /**
     * Calculates the direction after a turn (WEST or EAST).
     * 
     * @param turn The direction to turn (WEST or EAST).
     * @return The new direction after the turn.
     */
    private Direction getTurnedDirection(Direction turn) {
        switch (turn) {
            case EAST:
                return switch (currentDirection) {
                    case NORTH -> Direction.EAST;
                    case EAST -> Direction.SOUTH;
                    case SOUTH -> Direction.WEST;
                    case WEST -> Direction.NORTH;
                };
            case WEST:
                return switch (currentDirection) {
                    case NORTH -> Direction.WEST;
                    case WEST -> Direction.SOUTH;
                    case SOUTH -> Direction.EAST;
                    case EAST -> Direction.NORTH;
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
            case NORTH -> new Position(row - 1, col);
            case SOUTH -> new Position(row + 1, col);
            case WEST -> new Position(row, col - 1);
            case EAST -> new Position(row, col + 1);
        };
    }


    /**
     * Logs the current step in the canonical path and avoids dNORTHlicate entries.
     * 
     * @param action The action taken ("F", "L", or "R").
     */
    private void logStep(String action) {
        if (lastPosition != null && lastPosition.equals(currentPosition) && lastDirection == currentDirection) {
            return; // Prevent dNORTHlicate logging
        }

        lastPosition = new Position(currentPosition.getRow(), currentPosition.getCol());
        lastDirection = currentDirection;
        logger.info("Position: (" + currentPosition.getRow() + ", " + currentPosition.getCol() + "), Direction: " + action);
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

    public Position getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * Retrieves the canonical path as a sequence of "F", "L", and "R".
     * 
     * @return The canonical path string.
     */
    public String getFinalOutput() {
        String finalOutputString = finalOutput.toString();
        StringBuilder cleanedBuild = new StringBuilder("");
        for (int letter = 1; letter < finalOutputString.length(); letter++) {
            if (finalOutputString.charAt(letter - 1) != finalOutputString.charAt(letter)) {
                cleanedBuild.append(finalOutputString.charAt(letter - 1) + " ");
            } else {
                cleanedBuild.append(finalOutputString.charAt(letter - 1));
            }

            if (letter == finalOutputString.length() - 1) {
                cleanedBuild.append(finalOutputString.charAt(letter));
            } 
        }
        return cleanedBuild.toString();
    }
}