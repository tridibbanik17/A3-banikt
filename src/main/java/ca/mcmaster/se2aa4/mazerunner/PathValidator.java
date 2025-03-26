package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathValidator {
    private static final Logger logger = LogManager.getLogger();

    public static void validatePath(String generatedPath, String providedPath) {
        if (generatedPath.equals(providedPath)) {
            System.out.println("correct path");
            logger.info("Validation successful: The generated factorized path matches.");
        } else {
            System.out.println("incorrect path");
            logger.error("Validation failed: Expected '{}', but got '{}'.", providedPath, generatedPath);
        }
    }
}
