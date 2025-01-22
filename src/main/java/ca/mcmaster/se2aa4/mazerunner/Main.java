package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

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
            String line;
            while ((line = reader.readLine()) != null) {
                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        logger.trace("WALL ");
                    } else if (line.charAt(idx) == ' ') {
                        logger.trace("PASS ");
                    }
                }
                logger.trace("");
            }
            
            reader.close();

        } catch(ParseException e) {
            logger.error("Failed to parse command-line arguments", e);
        } catch(Exception e) {
            logger.error("An error occurred while processing the maze file", e);
        } 
        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}
