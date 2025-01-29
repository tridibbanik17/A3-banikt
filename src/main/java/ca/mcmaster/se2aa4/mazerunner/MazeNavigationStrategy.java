package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public interface MazeNavigationStrategy {
    boolean canMoveForward();
    boolean canTurnAround();
    boolean canTurnEAST();
    boolean canTurnWEST();
    void moveForward();
    void turnAround();
    void turnEAST();
    void turnWEST();
}