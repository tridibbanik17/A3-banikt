package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


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

    // Stores the steps taken during the maze-solving process
    private List<String> pathTaken;
    
    // Stores the canonical path as a sequence of "F", "L", and "R"
    private StringBuilder finalOutput;
    
    // Tracks the last logged position to prevent dNORTHlicate entries in the path log
    private Position lastPosition;
    
    // Tracks the last logged direction to prevent dNORTHlicate entries in the path log
    private Direction lastDirection;

    private final List<MazeSolverObserver> observers = new ArrayList<>();
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

    public void addObserver(MazeSolverObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        for (MazeSolverObserver observer : observers) {
            observer.update(message);
        }
    }

    /**
     * Solves the maze using the EAST-hand rule. Continues until the exit is reached or no more valid moves exist.
     * 
     * @return true if the maze is solved, false otherwise.
     */
    public boolean solve() {
        while (!hasReachedEnd()) {
            MazeNavigation strategy = getStrategy();
            if (strategy.canMoveForward()) {
                strategy.moveForward(); // Move forward if possible
                notifyObservers("Moved forward.");
            } else if (strategy.canTurnAround()) {
                strategy.turnAround(); // Turn around and move forward if needed
                strategy.moveForward();
                notifyObservers("Turned around and moved forward.");
            } else if (strategy.canTurnRight()) {
                strategy.turnRight(); // Turn EAST and move forward if needed
                strategy.moveForward();
                notifyObservers("Turned right and moved forward.");
            } else if (strategy.canTurnLeft()) {
                strategy.turnLeft(); // Turn WEST and move forward if needed
                strategy.moveForward();
                notifyObservers("Turned left and moved forward.");
            } else {
                throw new IllegalStateException("No path found.");
            }
        }
        notifyObservers("Maze successfully solved!");
        return hasReachedEnd(); // Return whether the maze has been solved
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
                cleanedBuild.append(finalOutputString.charAt(letter - 1)).append(" ");
            } else {
                cleanedBuild.append(finalOutputString.charAt(letter - 1));
            }

            if (letter == finalOutputString.length() - 1) {
                cleanedBuild.append(finalOutputString.charAt(letter));
            } 
        }
        return cleanedBuild.toString(); // Return the formatted canonical path
    }

    /**
     * Logs the current step in the canonical path and avoids dNORTHlicate entries.
     * 
     * @param action The action taken ("F", "L", or "R").
     */
    private void logStep(String action) {
        if (lastPosition != null && lastPosition.equals(currentPosition) && lastDirection == currentDirection) {
            return; // Prevent dNORTHlicate logging of position and direction
        }

        lastPosition = new Position(currentPosition.getRow(), currentPosition.getCol());
        lastDirection = currentDirection;
        pathTaken.add("Position: (" + currentPosition.getRow() + ", " + currentPosition.getCol() + "), Direction: " + action); // Add to path taken
        finalOutput.append(action); // Add action to canonical path
    }

    public MazeNavigation getStrategy() {
        return new DefaultMazeNavigationStrategy(this); // Return the default navigation strategy
    }

    private static class DefaultMazeNavigationStrategy implements MazeNavigation {
        private final MazeSolver mazeSolver;

        public DefaultMazeNavigationStrategy(MazeSolver mazeSolver) {
            this.mazeSolver = mazeSolver;
        }

        @Override
        public boolean canMoveForward() {
            // Checks if the solver can move forward based on current direction
            if (mazeSolver.currentDirection == Direction.NORTH) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() + 1) == '#') && ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() - 1, mazeSolver.currentPosition.getCol())) == ' ')) {
                    return true;
                }
            }
            else if (mazeSolver.currentDirection == Direction.SOUTH) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() - 1) == '#') && ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() + 1, mazeSolver.currentPosition.getCol())) == ' ')){
                    return true;
                }
            }
            else if (mazeSolver.currentDirection == Direction.EAST) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() + 1, mazeSolver.currentPosition.getCol()) == '#') && ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() + 1)) == ' ')){
                    return true;
                }
            }
            else if (mazeSolver.currentDirection == Direction.WEST) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() - 1, mazeSolver.currentPosition.getCol()) == '#') && ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() - 1)) == ' ')){
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean canTurnAround() {
            // Checks if the solver can turn around based on current direction
            if (mazeSolver.currentDirection == Direction.NORTH) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() - 1, mazeSolver.currentPosition.getCol()) == '#') && (mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() - 1) == '#') && (mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() + 1) == '#')) {
                    return true;
                }
            } else if (mazeSolver.currentDirection == Direction.SOUTH) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() + 1, mazeSolver.currentPosition.getCol()) == '#') && (mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() - 1) == '#') && (mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() + 1) == '#')) {
                    return true;
                }
            } else if (mazeSolver.currentDirection == Direction.EAST) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() + 1) == '#') && (mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() - 1, mazeSolver.currentPosition.getCol()) == '#') && (mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() + 1, mazeSolver.currentPosition.getCol()) == '#')) {
                    return true;
                }
            } else if (mazeSolver.currentDirection == Direction.WEST) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() - 1) == '#') && (mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() - 1, mazeSolver.currentPosition.getCol()) == '#') && (mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() + 1, mazeSolver.currentPosition.getCol()) == '#')) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean canTurnRight() {
            // Checks if the solver can turn EAST based on current direction
            if (!canMoveForward()) {
                if (mazeSolver.currentDirection == Direction.NORTH) {
                    if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() + 1) == ' ')){
                        return true;
                    }
                }
                else if (mazeSolver.currentDirection == Direction.SOUTH) {
                    if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() - 1) == ' ')){
                        return true;
                    }
                }
                else if (mazeSolver.currentDirection == Direction.EAST) {
                    if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() + 1, mazeSolver.currentPosition.getCol()) == ' ')){
                        return true;
                    }
                }
                else if (mazeSolver.currentDirection == Direction.WEST) {
                    if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() - 1, mazeSolver.currentPosition.getCol()) == ' ')){
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean canTurnLeft() {
            // Checks if the solver can turn WEST based on current direction
            if (mazeSolver.currentDirection == Direction.NORTH) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() + 1) == '#') && ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() - 1, mazeSolver.currentPosition.getCol())) == '#')){
                    return true;
                }
            }
            else if (mazeSolver.currentDirection == Direction.SOUTH) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() - 1) == '#') && ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() + 1, mazeSolver.currentPosition.getCol())) == '#')){
                    return true;
                }
            }
            else if (mazeSolver.currentDirection == Direction.EAST) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() + 1, mazeSolver.currentPosition.getCol()) == '#') && ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() + 1)) == '#')){
                    return true;
                }
            }
            else if (mazeSolver.currentDirection == Direction.WEST) {
                if ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow() - 1, mazeSolver.currentPosition.getCol()) == '#') && ((mazeSolver.maze.returnCellValue(mazeSolver.currentPosition.getRow(), mazeSolver.currentPosition.getCol() - 1)) == '#')){
                    return true;
                }
            }
            return false;
        }

        @Override
        public void moveForward() {
            mazeSolver.currentPosition = getNextPosition(mazeSolver.currentDirection); // Move the solver to the next position
            mazeSolver.logStep("F"); // Log the forward move
        }

        @Override
        public void turnAround() {
            mazeSolver.currentDirection = switch (mazeSolver.currentDirection) {
                case NORTH -> Direction.SOUTH;
                case SOUTH -> Direction.NORTH;
                case EAST -> Direction.WEST;
                case WEST -> Direction.EAST;
            }; // Turn the solver 180 degrees
            mazeSolver.logStep("R"); // Log the right turn
            mazeSolver.logStep("R"); // Log another right turn for 180-degree turn
        }

        @Override
        public void turnRight() {
            mazeSolver.currentDirection = getTurnedDirection(Direction.EAST); // Turn the solver EAST
            mazeSolver.logStep("R"); // Log the right turn
        }

        @Override
        public void turnLeft() {
            mazeSolver.currentDirection = getTurnedDirection(Direction.WEST); // Turn the solver WEST
            mazeSolver.logStep("L"); // Log the left turn
        }

        private Direction getTurnedDirection(Direction turn) {
            return switch (turn) {
                case EAST -> switch (mazeSolver.currentDirection) {
                    case NORTH -> Direction.EAST;
                    case EAST -> Direction.SOUTH;
                    case SOUTH -> Direction.WEST;
                    case WEST -> Direction.NORTH;
                };
                case WEST -> switch (mazeSolver.currentDirection) {
                    case NORTH -> Direction.WEST;
                    case WEST -> Direction.SOUTH;
                    case SOUTH -> Direction.EAST;
                    case EAST -> Direction.NORTH;
                };
                default -> mazeSolver.currentDirection;
            };
        }

        private Position getNextPosition(Direction direction) {
            int row = mazeSolver.currentPosition.getRow();
            int col = mazeSolver.currentPosition.getCol();
            return switch (direction) {
                case NORTH -> new Position(row - 1, col);
                case SOUTH -> new Position(row + 1, col);
                case WEST -> new Position(row, col - 1);
                case EAST -> new Position(row, col + 1);
            }; // Return the next position based on current direction
        }
    }
}