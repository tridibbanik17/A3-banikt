package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MazeSolverTest {

    private Maze createTestMaze(Character[][] grid) {
        return new Maze(grid);
    }

    @Test
    void testMazeSolverStraightPath() {
        Character[][] grid = {
            {'#', '#', '#', '#'},
            {'#', '#', '#', '#'},
            {' ', ' ', ' ', ' '},
            {'#', '#', '#', '#'},
            {'#', '#', '#', '#'}
        };
        Maze maze = createTestMaze(grid);
        MazeSolver solver = MazeNavigationFactory.createSolver(maze, "righthand");
        solver.solve();
        assertEquals("3F", Encoder.encode(solver.getFinalOutput())); // 3 steps forward
    }

    @Test
    void testMazeSolverWithTurns() {
        Character[][] grid = {
            {'#', '#', '#', '#'},
            {'#', ' ', ' ', ' '},
            {' ', ' ', '#', '#'},
            {'#', '#', '#', '#'}
        };
        Maze maze = createTestMaze(grid);
        MazeSolver solver = MazeNavigationFactory.createSolver(maze, "righthand");
        solver.solve();
        assertEquals("F L F R 2F", Encoder.encode(solver.getFinalOutput()));
    }

    @Test
    void testMazeWithMultipleSolutions() {
        Character[][] grid = {
            {'#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#'},
            {' ', ' ', ' ', ' ', ' '},
            {'#', ' ', ' ', ' ', '#'},
            {'#', '#', '#', '#', '#'}
        };
        Maze maze = createTestMaze(grid);
        MazeSolver solver = MazeNavigationFactory.createSolver(maze, "righthand");
        solver.solve();
        assertEquals("F R F L 2F L F R F", Encoder.encode(solver.getFinalOutput()));
    }

    @Test
    void testFactorizedPathTurnAround() {
        Character[][] grid = {
            {'#', '#', '#', '#'},
            {'#', ' ', ' ', ' '},
            {'#', ' ', '#', '#'},
            {' ', ' ', ' ', '#'},
            {'#', '#', '#', '#'}
        };
        Maze maze = createTestMaze(grid);
        MazeSolver solver = MazeNavigationFactory.createSolver(maze, "righthand");
        solver.solve();
        assertEquals("2F 2R F R 2F R 2F", Encoder.encode(solver.getFinalOutput()));
    }
}