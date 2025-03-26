package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("i", "input", true, "Path to the maze input file");
        options.addOption("p", "path", true, "Factorized path");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            // Ensure input file is provided
            if (!cmd.hasOption("i")) {
                logger.error("Missing required -i flag for the input file.");
                return;
            }

            // Read the maze from the input file
            String inputFile = cmd.getOptionValue("i");
            Character[][] mazeArray = MazeFileReader.readMaze(inputFile);
            MazeRunner runner = new MazeRunner(mazeArray);

            // Solve the maze
            String factorizedPath = runner.solveMaze();

            // Print out the factorized path
            if (!cmd.hasOption("p")) {
                System.out.println(factorizedPath);
            } else { // Validate the correctness of the path
                String providedPath = cmd.getOptionValue("p");
                PathValidator.validatePath(factorizedPath, providedPath);
            }

        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage(), e);
        }
    }
}
