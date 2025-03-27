package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.logging.*;
import java.util.ArrayList;
import java.util.List;

class MazeRunnerTest {

    // Test case for maze creation
    @Test
    void testMazeCreation() {
        // Example grid with walls (#) and open spaces ( )
        Character[][] grid = {
            {'#', '#', ' ', '#'}, // row 0
            {' ', ' ', '#', '#'}, // row 1
            {'#', ' ', ' ', '#'}, // row 2
            {'#', '#', ' ', ' '}  // row 3
        };
        Maze maze = new Maze(grid);
        // Assert that entry and exit points are correct
        assertEquals(1, maze.getEntryRow());
        assertEquals(0, maze.getEntryCol());
        assertEquals(3, maze.getExitRow());
        assertEquals(3, maze.getExitCol());
    }

    // Test case for maze with no entry point
    @Test
    void testMazeWithNoEntry() {
        Character[][] grid = {
            {'#', '#', '#', '#'},
            {'#', '#', '#', '#'}
        };
        // Assert that an IllegalStateException is thrown when there's no entry
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Maze(grid));
        assertEquals("No entry point found at column 0", exception.getMessage());
    }

    // Test case for maze with no exit point
    @Test
    void testMazeWithNoExit() {
        Character[][] grid = {
            {' ', '#', '#', '#'},
            {'#', '#', '#', '#'}
        };
        // Assert that an IllegalStateException is thrown when there's no exit
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Maze(grid));
        assertEquals("No exit point found at the rightmost column.", exception.getMessage());
    }

    // Test case for a maze with a straight path
    @Test
    void testMazeSolverStraightPath() {
        Character[][] grid = {
            {'#', '#', '#', '#'},
            {'#', '#', '#', '#'},
            {' ', ' ', ' ', ' '},
            {'#', '#', '#', '#'},
            {'#', '#', '#', '#'}
        };
        Maze maze = new Maze(grid);
        MazeSolver solver = new MazeSolver(maze);
        // Solve the maze and check the output path
        assertTrue(solver.solve());
        assertEquals("FFF", solver.getFinalOutput().trim()); // 3 steps forward from entry column to exit column
    }

    // Test case for a maze with turns
    @Test
    void testMazeSolverWithTurns() {
        Character[][] grid = {
            {'#', '#', '#', '#'},
            {'#', ' ', ' ', ' '},
            {' ', ' ', '#', '#'},
            {'#', '#', '#', '#'},
        };
        Maze maze = new Maze(grid);
        MazeSolver solver = new MazeSolver(maze);
        // Solve the maze and check the output path with turns
        assertTrue(solver.solve());
        assertEquals("F L F R FF", solver.getFinalOutput());
    }

    // Test case for position equality
    @Test
    void testPositionEquality() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(1, 2);
        // Assert that two positions with the same coordinates are equal
        assertEquals(pos1, pos2);
    }

    // Test case for turning right (clockwise)
    @Test
    void testDirectionTurnRight() {
        Direction direction = Direction.EAST;
        // Assert that turning right from EAST results in SOUTH
        assertEquals(Direction.SOUTH, direction.turnRight());
    }

    // Test case for turning left (counter-clockwise)
    @Test
    void testDirectionTurnLeft() {
        Direction direction = Direction.NORTH;
        // Assert that turning left from NORTH results in WEST
        assertEquals(Direction.WEST, direction.turnLeft());
    }

    // Test case for turning around (180 degrees)
    @Test
    void testDirectionTurnAround() {
        Direction direction = Direction.NORTH;
        // Assert that turning around from NORTH results in SOUTH
        assertEquals(Direction.SOUTH, direction.turnAround());
    }

    // Test case for maze solver observer (log capture)
    @Test
    void testMazeSolverObserver() {
        Logger logger = Logger.getLogger(ConsoleObserver.class.getName());

        try (LogCaptor logCaptor = new LogCaptor(logger)) {
            logCaptor.attach(); // Attach the log captor

            // Setup and solve maze
            ConsoleObserver consoleObserver = new ConsoleObserver();
            MazeSolver solver = new MazeSolver(new Maze(new Character[][]{
                {'#', '#', '#', '#'},
                {'#', ' ', '#', '#'},
                {' ', ' ', ' ', ' '},
                {'#', '#', '#', '#'},
            }));

            solver.addObserver(consoleObserver);
            solver.solve();

            // Assert that ConsoleObserver logs the success message
            assertFalse(logCaptor.getCapturedLogs().isEmpty(), "ConsoleObserver should log messages.");
            assertTrue(logCaptor.getCapturedLogs().stream().anyMatch(msg -> msg.contains("Maze successfully solved!")),
                    "ConsoleObserver log should contain 'Maze successfully solved!'");
        }
    }

    // LogCaptor class to capture log output during tests
    static class LogCaptor extends Handler implements AutoCloseable {
        private final Logger logger;
        private final List<String> capturedLogs = new ArrayList<>();
        private final Handler oldHandler;

        LogCaptor(Logger logger) {
            this.logger = logger;
            this.oldHandler = logger.getHandlers().length > 0 ? logger.getHandlers()[0] : null;
            logger.setUseParentHandlers(false);
        }

        // Attach the log handler to capture logs
        void attach() {
            logger.addHandler(this); 
        }

        @Override
        public void publish(LogRecord record) {
            capturedLogs.add(record.getMessage());
        }

        @Override
        public void flush() {}

        @Override
        public void close() {
            // Remove the log handler after the test
            logger.removeHandler(this);
            if (oldHandler != null) {
                logger.addHandler(oldHandler);
            }
        }

        // Return the captured logs
        List<String> getCapturedLogs() {
            return capturedLogs;
        }
    }
}
