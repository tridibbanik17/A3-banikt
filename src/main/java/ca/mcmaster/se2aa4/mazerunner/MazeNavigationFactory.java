package ca.mcmaster.se2aa4.mazerunner;

/**
 * Factory class for creating different MazeNavigation strategies.
 */
public class MazeNavigationFactory {

    /**
     * This method returns a MazeNavigation strategy.
     * For now, assume we have a RightHandNavigation strategy.
     */
    public static MazeNavigation createStrategy(RightHandNavigation solver) {
        return new DefaultMazeNavigationStrategy(solver);
    }

    /**
     * This method creates a MazeSolver 
     * The algorithmType determines which solver is created
     */
    public static MazeSolver createSolver(Maze maze, String algorithmType) {
        if (algorithmType == null || algorithmType.isEmpty() || algorithmType.equalsIgnoreCase("righthand")) {
            return RightHandNavigation.create(maze);
        }
        // Add future solvers here, e.g., BFS, DFS, etc.
        throw new IllegalArgumentException("Unknown algorithm type: " + algorithmType);
    }
}
