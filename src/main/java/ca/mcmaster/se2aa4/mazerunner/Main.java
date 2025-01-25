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

public class Main {

    // PathNotFoundException class raise exception to be thrown when a path is not found
    public static class PathNotFoundException extends Exception {

        // Constructor to display an error message 
        public PathNotFoundException(String message) {
            super(message);  
        }
    }

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        Options options = new Options();
        options.addOption("i", "input", true, "Path to the maze input file");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            if (!cmd.hasOption("i")) {
                logger.error("Missing required -i flag for the input file. Usage: -i <maze_file_path>");
                return;
            }

            String inputFile = cmd.getOptionValue("i");
            logger.info("Reading the maze from file: {}", inputFile);

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            logger.info("Maze read successfully with {} rows.", lines.size());



        } catch (Exception e) {
            logger.error("An error occurred while processing the maze file: {}", e.getMessage(), e);
        }

        logger.info("** End of Maze Runner");
    }
}
