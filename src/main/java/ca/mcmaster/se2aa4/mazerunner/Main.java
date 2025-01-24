package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    // Custom exception to be thrown when a path is not found
    public class PathNotFoundException extends Exception {

        // Constructor to allow custom error message
        public PathNotFoundException(String message) {
            super(message);  // Pass the message to the superclass constructor
        }
    }

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        // Create Options for Apache CLI
        Options options = new Options();
        options.addOption("i", "input", true, "destination to the maze input file");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            // Parse command line arguments
            cmd = parser.parse(options, args);

            // Check if -i flag is provided
            if (!cmd.hasOption("i")) {
                logger.error("Missing required -i flag for the input file. Please include -i followed by the specific maze text file");
                return;
            }

            String inputFile = cmd.getOptionValue("i");
            logger.info("Reading the maze from file: {}", inputFile);

            // Read and process the maze file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            // logger.info("The text file read is", reader);
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                logger.info("Each line is {} ", line);
                lines.add(line);
            }
            logger.info("Lines list {} ", lines);

            reader.close();


            // Find the maximum length of the lines
            int maxLength = 0;
            for (String item : lines) {
                if (item.length() > maxLength) {
                    maxLength = item.length();
                }
            }

            Character[][] mazeArray = new Character[lines.size()][maxLength];

            // Fill the 2D array with characters from the lines
            for (int i = 0; i < lines.size(); i++) {
                String str = lines.get(i);
                for (int j = 0; j < maxLength; j++) {
                    if (j < str.length()) {
                        mazeArray[i][j] = str.charAt(j);
                    } else {
                        mazeArray[i][j] = ' ';  // Fill with empty space if the line is shorter
                    }
                }
            }

            logger.info("Maze read with {} rows and {} columns.", lines.size(), lines.get(0).length());
            logger.debug("Maze Array: {}", (Object) mazeArray);

            logger.info("lines : {} ", lines);
            logger.info("The size of the maze: {}", lines.size());
            logger.info("mazeArray: {}", (Object) mazeArray);
            // Create the Maze object
            Maze maze = new Maze(mazeArray);
            logger.info("Maze object created: {}", maze);
            int entryRow = maze.getEntryRow();
            int entryCol = maze.getEntryCol();
            int exitRow = maze.getExitRow();
            int exitCol = maze.getExitCol();


            logger.info("entryRow: {}", entryRow);
            logger.info("entryCol: {}", entryCol);
            logger.info("exitRow: {}" , exitRow);
            logger.info("exitCol: {}", exitCol);

            // Log entry and exit points
            logger.info("Entry point: (" + entryRow + ", " + entryCol + ")");
            logger.info("Exit point: (" + exitRow + ", " + exitCol + ")");

            // Initialize the MazeSolver
            MazeSolver solver = new MazeSolver(maze);
            logger.info("solver : " + solver);
            

            // Solve the maze using the right-hand rule
            logger.info("**** Computing path");
            try {
                boolean pathFound = solver.solve();
                if (pathFound) {
                    logger.info("Path found!");
                    // logger.info("Exit reached at: (" + maze.getGrid()[exitRow][exitCol] + ")");
                    logger.info("Current position: (" + solver.getCurrentPosition().getRow() + ", " + solver.getCurrentPosition().getCol() + ")");
                    logger.info("Direction faced: " + solver.getCurrentDirection());
                }
            } catch (Exception e) {
                logger.error("Path not found: " + e.getMessage());
            }

        } catch (ParseException e) {
            logger.error("Failed to parse command-line arguments", e);
        } catch (Exception e) {
            logger.error("An error occurred while processing the maze file", e);
        }

        logger.info("** End of MazeRunner");
    }
}
