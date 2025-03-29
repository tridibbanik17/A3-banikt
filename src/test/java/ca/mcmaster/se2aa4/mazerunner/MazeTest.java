package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class MazeTest {

    @Test
    void testMazeCreation() {
        Character[][] grid = {
            {'#', '#', ' ', '#'},
            {' ', ' ', '#', '#'},
            {'#', ' ', ' ', '#'},
            {'#', '#', ' ', ' '}
        };
        Maze maze = new Maze(grid);
        assertEquals(1, maze.getEntryRow());
        assertEquals(0, maze.getEntryCol());
        assertEquals(3, maze.getExitRow());
        assertEquals(3, maze.getExitCol());
    }

    @Test
    void testMazeWithNoEntry() {
        Character[][] grid = {
            {'#', '#', '#', '#'},
            {'#', '#', '#', '#'}
        };
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Maze(grid));
        assertEquals("No entry point found at column 0", exception.getMessage());
    }

    @Test
    void testMazeWithNoExit() {
        Character[][] grid = {
            {' ', '#', '#', '#'},
            {'#', '#', '#', '#'}
        };
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Maze(grid));
        assertEquals("No exit point found at the rightmost column.", exception.getMessage());
    }
}
