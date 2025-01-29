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
        options.addOption("p", "path", true, "Factorized path");

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

            // Showing usage of -p flag
            if (cmd.hasOption("i") && (!cmd.hasOption("p"))) {
                logger.error("Wrong use of -p flag. You can validate the generated solution path using -p flag.\nUsage: -i <maze_file_path> -p <factorized_form_of_the_path>");
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

            logger.info("Maze read successfully with {} rows and {} columns.", lines.size(), lines.get(0).length());

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
                    // Log the detailed steps taken to solve the maze.
                    logger.info("Detailed path steps:\n");
                    solver.getPathTaken().forEach(logger::info);

                    logger.info("\n\nMaze was succussfully solved by entering through ({}, {}) and exiting through ({}, {}).\n",
                    maze.getEntryRow(), maze.getEntryCol(), maze.getExitRow(), maze.getExitCol());

                    // Log the complete canonical path.
                    String canonicalPath = solver.getFinalOutput();
                    logger.info("\n\nMaze solved successfully! Canonical path: \n{}\n", canonicalPath);

                    // Encode the canonical path using the Encoder utility.
                    String factorizedPath = Encoder.encoder(canonicalPath);
                    logger.info("\n\nFactorized path: \n{}\n", factorizedPath);
                    if (cmd.hasOption("p")) {
                        String cmdPathValidArg = cmd.getOptionValue("p");
                        String cleanedPath = factorizedPath.trim();
                        if (cleanedPath.equals(cmdPathValidArg)) {
                            logger.info("\n\nValidation successful: The factorized path matches the expected path.\n");
                        } else {
                            logger.error("\n\nValidation failed: path entered in the cmd arg '{}',\n but generated path '{}'.\n", cmdPathValidArg, cleanedPath);
                        }
                    }
                } else {
                    logger.info("\nCurrent position is: {}", solver.getCurrentPosition());
                    logger.error("\nMaze could not be solved. No valid path to the exit.");
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