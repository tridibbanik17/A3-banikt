package ca.mcmaster.se2aa4.mazerunner;

/**
 * Factory class for creating different MazeNavigation strategies.
 */
public class MazeNavigationFactory {

    // Return a MazeNavigation strategy. For now, assume we only have a RightHandNavigation strategy.
    public static MazeNavigation createStrategy(RightHandNavigation solver) {
        return new DefaultMazeNavigationStrategy(solver);
    }

    // Create a MazeSolver and the algorithmType determines which solver is created.
    public static MazeSolver createSolver(Maze maze, String algorithmType) {
        if (algorithmType == null || algorithmType.isEmpty() || algorithmType.equalsIgnoreCase("righthand") || algorithmType.equalsIgnoreCase("default")) {
            return RightHandNavigation.create(maze);
        }
        // Add future solvers here, e.g., BFS, DFS, etc.
        throw new IllegalArgumentException("Unknown algorithm type: " + algorithmType);
    }
}
