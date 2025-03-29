package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeRunner {
    private static final Logger logger = LogManager.getLogger();
    private final MazeSolver solver;

    public MazeRunner(Character[][] mazeArray, String algorithmType) {
        Maze maze = new Maze(mazeArray);
        this.solver = MazeNavigationFactory.createSolver(maze, algorithmType);
    }

    public String solveMaze() throws Exception {
        solver.solve();
        logger.info("Maze solved using " + solver.getClass().getSimpleName());
        return Encoder.encode(solver.getFinalOutput());
    }
}
