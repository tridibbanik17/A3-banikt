package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MazeNavigationFactoryTest {
    
    private Maze createTestMaze() {
        return new Maze(new Character[][]{
            {'#', '#', '#', '#', '#'},
            {' ', ' ', '#', ' ', ' '},
            {'#', ' ', ' ', ' ', '#'},
            {'#', '#', '#', '#', '#'},
        });
    }

    @Test
    void testCreateDefaultMazeNavigationStrategy() {
        Maze testMaze = createTestMaze();
        RightHandNavigation solver = new RightHandNavigation(testMaze);
        MazeNavigation strategy = MazeNavigationFactory.createStrategy(solver);

        assertNotNull(strategy, "Factory should return a non-null strategy");
        assertInstanceOf(DefaultMazeNavigationStrategy.class, strategy, "Default strategy should be Right-Hand Rule");
    }

    @Test
    void testCreateRightHandSolver() {
        Maze testMaze = createTestMaze();
        MazeSolver solver = MazeNavigationFactory.createSolver(testMaze, "righthand");

        assertNotNull(solver, "Factory should return a non-null solver");
        assertInstanceOf(RightHandNavigation.class, solver, "Solver should be RightHandNavigation");
    }

    @Test
    void testCreateDefaultSolver() {
        Maze testMaze = createTestMaze();
        MazeSolver solver = MazeNavigationFactory.createSolver(testMaze, "default");

        assertNotNull(solver, "Factory should return a non-null solver");
        assertInstanceOf(RightHandNavigation.class, solver, "Default solver should be RightHandNavigation");
    }

    @Test
    void testInvalidSolverTypeThrowsException() {
        Maze testMaze = createTestMaze();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MazeNavigationFactory.createSolver(testMaze, "invalidAlgorithm");
        });

        String expectedMessage = "Unknown algorithm type: invalidAlgorithm";
        assertTrue(exception.getMessage().contains(expectedMessage), "Exception should contain the invalid type message");
    }
}