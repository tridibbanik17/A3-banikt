package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

/**
 * The MazeSolver class is responsible for solving a maze using the EAST-hand rule.
 * It navigates through the maze and keeps track of the path taken and the canonical output path.
 */
public class RightHandNavigation implements MazeSolver {
    private final Maze maze;
    private Position currentPosition;
    private Direction currentDirection;
    private final List<String> pathTaken;
    private final StringBuilder finalOutput;
    private Position lastPosition;
    private Direction lastDirection;
    private final List<MazeSolverObserver> observers = new ArrayList<>();
    private final List<Position> path;
    private MazeNavigation navigationStrategy;

    public RightHandNavigation(Maze maze) {
        this.maze = maze;
        this.currentPosition = new Position(maze.getEntryRow(), maze.getEntryCol());
        this.currentDirection = Direction.EAST; // Start facing EAST
        this.pathTaken = new ArrayList<>(); 
        this.finalOutput = new StringBuilder(""); // Store the canonical path
        this.lastPosition = null; 
        this.lastDirection = null;
        this.path = new ArrayList<>(); 
    }

    // Factory method to handle initialization safely
    public static RightHandNavigation create(Maze maze) {
        RightHandNavigation solver = new RightHandNavigation(maze);
        solver.navigationStrategy = MazeNavigationFactory.createStrategy(solver);
        return solver;
    }

    public boolean isSolved() {
        return maze.isSolved(); 
    }

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

    public MazeNavigation getStrategy() {
        if (navigationStrategy == null) {
            throw new IllegalStateException("Navigation strategy is not initialized.");
        }
        return navigationStrategy;
    }

    /**
     * Solves the maze using the righthand rule. It returns a List of Positions representing the path from start to exit.
     */
    @Override
    public void solve() {
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
                strategy.turnRight(); // Turn right and move forward if needed
                strategy.moveForward();
                notifyObservers("Turned right and moved forward.");
            } else if (strategy.canTurnLeft()) {
                strategy.turnLeft(); // Turn left and move forward if needed
                strategy.moveForward();
                notifyObservers("Turned left and moved forward.");
            } else {
                throw new IllegalStateException("No path found.");
            }
        }
        notifyObservers("Maze successfully solved!");
    }

    public Maze getMaze() {
        return this.maze;
    }

    public List<String> getPathTaken() {
        return pathTaken;
    }

    public Position getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(Position newPosition) {
        this.currentPosition = newPosition;
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public void setCurrentDirection(Direction newDirection) {
        this.currentDirection = newDirection;
    }

    /**
     * Retrieves the canonical path as a sequence of "F", "L", and "R".
     */
    @Override
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
        return cleanedBuild.toString();
    }

    /**
     * Logs the current step and adds the position to the path list.
     */
    public void logStep(String action) {
        if (lastPosition != null && lastPosition.equals(currentPosition) && lastDirection == currentDirection) {
            return;
        }

        lastPosition = new Position(currentPosition.getRow(), currentPosition.getCol());
        lastDirection = currentDirection;
        pathTaken.add("Position: (" + currentPosition.getRow() + ", " + currentPosition.getCol() + "), Direction: " + action);
        finalOutput.append(action); 
        path.add(new Position(currentPosition.getRow(), currentPosition.getCol())); 
    }
}