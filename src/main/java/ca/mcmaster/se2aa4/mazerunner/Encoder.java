package ca.mcmaster.se2aa4.mazerunner;

/**
 * The Encoder class provides a utility method to compress a given string into a factorized representation.
 */
public class Encoder {

    public static String encode(String longText) {
        // Validate that the input string is not empty
        if (longText.length() == 0) {
            throw new IllegalArgumentException("There is no path available.");
        }

        StringBuilder encodedText = new StringBuilder("");

        longText = longText.replaceAll("[\\s]", "");

        for (int i = 0; i < longText.length(); i++) {
            Integer count = 1; 

            while ((i < longText.length() - 1) && (longText.charAt(i) == longText.charAt(i + 1))) {
                count++;
                i++;
            }

            String countStr = count.toString();
            if (countStr.equals("1")) {
                countStr = "";
            }

            encodedText.append(countStr).append(String.valueOf(longText.charAt(i)).toUpperCase()).append(" ");
        }

        // Return the factorized path
        return encodedText.toString().trim();
    }
}