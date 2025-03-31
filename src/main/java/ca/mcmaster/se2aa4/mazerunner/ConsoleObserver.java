package ca.mcmaster.se2aa4.mazerunner;

import java.util.logging.Logger;

public class ConsoleObserver implements MazeSolverObserver {
    private static final Logger logger = Logger.getLogger(ConsoleObserver.class.getName());

    @Override
    public void update(String message) {
        logger.info(message);
    }
}