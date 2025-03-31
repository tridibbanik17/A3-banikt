package ca.mcmaster.se2aa4.mazerunner;

/**
 * Interface defining movement behaviours for navigating a maze.
 */
public interface MazeNavigation {
    boolean canMoveForward();
    boolean canTurnAround();
    boolean canTurnRight();
    boolean canTurnLeft();
    void moveForward();
    void turnAround();
    void turnRight();
    void turnLeft();
}