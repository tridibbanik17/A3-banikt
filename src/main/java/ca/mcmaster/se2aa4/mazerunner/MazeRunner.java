package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeRunner {
    private static final Logger logger = LogManager.getLogger();
    private final MazeSolver solver;
    private final Maze maze;

    public MazeRunner(Character[][] mazeArray) {
        this.maze = new Maze(mazeArray);
        this.solver = new MazeSolver(maze);
    }

    public String solveMaze() throws Exception {
        if (solver.solve()) {
            logger.info("Maze successfully solved!");
            return Encoder.encode(solver.getFinalOutput());
        } else {
            logger.error("Maze could not be solved. No valid path found.");
            throw new Exception("No valid path found.");
        }
    }
}
