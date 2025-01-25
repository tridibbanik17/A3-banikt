package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The main class responsible for orchestrating the Maze Runner application.
 * It parses command-line arguments, reads the maze from a file, initializes the maze solver,
 * and outputs the solution if one exists.
 */
public class Main {

    /**
     * Custom exception to handle cases where no valid path is found in the maze.
     */
    public static class PathNotFoundException extends Exception {

        /**
         * Constructor to initialize the exception with a custom error message.
         *
         * @param message The error message describing the exception.
         */
        public PathNotFoundException(String message) {
            super(message);  
        }
    }

    // Logger instance for logging application flow and debug information.
    private static final Logger logger = LogManager.getLogger();

    /**
     * The entry point for the Maze Runner application.
     * This method handles command-line argument parsing, maze reading, and solving.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        // Define options for the command-line interface.
        Options options = new Options();
        options.addOption("i", "input", true, "Path to the maze input file");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            // Parse the command-line arguments.
            cmd = parser.parse(options, args);

            // Ensure the required input file option is provided.
            if (!cmd.hasOption("i")) {
                logger.error("Missing required -i flag for the input file. Usage: -i <maze_file_path>");
                return;
            }

            // Retrieve the path to the maze input file.
            String inputFile = cmd.getOptionValue("i");
            logger.info("Reading the maze from file: {}", inputFile);

            // Read the maze file line by line.
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            logger.info("Maze read successfully with {} rows.", lines.size());

            // Determine the maximum row length to create a 2D maze array.
            int maxLength = lines.stream().mapToInt(String::length).max().orElse(0);
            Character[][] mazeArray = new Character[lines.size()][maxLength];

            // Populate the 2D maze array with characters from the file.
            for (int i = 0; i < lines.size(); i++) {
                String row = lines.get(i);
                for (int j = 0; j < maxLength; j++) {
                    mazeArray[i][j] = j < row.length() ? row.charAt(j) : ' ';
                }
            }

            // Create the Maze object and log its details.
            Maze maze = new Maze(mazeArray);
            logger.info("Maze object created successfully. Entry: ({}, {}), Exit: ({}, {})",
                    maze.getEntryRow(), maze.getEntryCol(), maze.getExitRow(), maze.getExitCol());

            // Log the visual representation of the maze.
            logger.info("The maze looks like: {}", maze.printMaze());

            // Initialize the MazeSolver with the Maze object.
            MazeSolver solver = new MazeSolver(maze);
            logger.info("MazeSolver initialized. Starting solution process...");

            try {
                // Solve the maze and handle the solution output.
                if (solver.solve()) {
                    String canonicalPath = solver.getFinalOutput();
                    logger.info("Maze solved successfully! Canonical path: {}", canonicalPath);

                    // Encode the canonical path using the Encoder utility.
                    String factorizedPath = Encoder.encoder(canonicalPath);
                    logger.info("Factorized path: {}", factorizedPath);

                    // Log the detailed steps taken to solve the maze.
                    logger.info("Detailed path steps:");
                    solver.getPathTaken().forEach(logger::info);
                } else {
                    logger.error("Maze could not be solved. No valid path to the exit.");
                }
            } catch (Exception e) {
                logger.error("An error occurred while solving the maze: {}", e.getMessage(), e);
            }

        } catch (Exception e) {
            logger.error("An error occurred while processing the maze file: {}", e.getMessage(), e);
        }

        logger.info("** End of Maze Runner");
    }
}
