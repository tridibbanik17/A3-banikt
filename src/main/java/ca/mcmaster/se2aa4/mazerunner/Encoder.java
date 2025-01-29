package ca.mcmaster.se2aa4.mazerunner;

/**
 * The Encoder class provides a utility method to compress a given string into a factorized representation.
 */
public class Encoder {

    /**
     * Encodes a given string into a compressed format where consecutive characters
     * are represented as their count followed by the character in uppercase.
     * 
     * For example:
     * Input: "FFF R FF L F"
     * Output: "3F 1R 2F 1L 1F"
     * 
     * @param longText The input string to be encoded. It should not be empty or null.
     * @return The encoded string in the compressed format.
     * @throws IllegalArgumentException if the input string is empty.
     */
    public static String encoder(String longText) {
        // Validate that the input string is not empty
        if (longText.length() == 0) {
            throw new IllegalArgumentException("There is no path available.");
        }

        // Initialize a StringBuilder
        StringBuilder encodedText = new StringBuilder("");

        // Remove all whitespace characters from the input string
        longText = longText.replaceAll("[\\s]", "");

        // Iterate through the string to encode consecutive characters
        for (int i = 0; i < longText.length(); i++) {
            Integer count = 1; // Initialize count for current character

            // Count consecutive occurrences of the same character
            while ((i < longText.length() - 1) && (longText.charAt(i) == longText.charAt(i + 1))) {
                count++;
                i++;
            }

            String countStr = count.toString();
            if (countStr.equals("1")) {
                countStr = "";
            }

            // Append the count and the character (in uppercase) to the encoded string
            encodedText.append(countStr + String.valueOf(longText.charAt(i)).toUpperCase() + " ");
        }

        // Return the factorized path
        return encodedText.toString();
    }
}
