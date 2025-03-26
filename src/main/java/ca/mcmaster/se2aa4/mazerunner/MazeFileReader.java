package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeFileReader {
    private static final Logger logger = LogManager.getLogger();

    // Reads a maze from a file and returns it as a 2D character array
    public static Character[][] readMaze(String filePath) throws Exception {
        logger.info("Reading the maze from file: {}", filePath);

        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line); // Store each line of the maze
            }
        }

        if (lines.isEmpty()) {
            throw new Exception("Maze file is empty.");
        }

        int maxLength = lines.stream().mapToInt(String::length).max().orElse(0);
        Character[][] mazeArray = new Character[lines.size()][maxLength];

        // Convert list of strings into a 2D character array
        for (int i = 0; i < lines.size(); i++) {
            String row = lines.get(i);
            for (int j = 0; j < maxLength; j++) {
                mazeArray[i][j] = j < row.length() ? row.charAt(j) : ' '; // Fill missing spaces
            }
        }

        logger.info("Maze read successfully with {} rows and {} columns.", lines.size(), maxLength);
        return mazeArray;
    }
}
