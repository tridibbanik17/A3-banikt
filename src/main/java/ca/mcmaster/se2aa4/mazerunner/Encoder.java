package ca.mcmaster.se2aa4.mazerunner;

public class Encoder {
    public static String encoder(String longText) {
        if (longText.length() == 0) {
            throw new IllegalArgumentException("There is no path available.");
        }

        StringBuilder encodedText = new StringBuilder("");
        longText = longText.replaceAll("[\\s]", "");
        for (int i = 0; i < longText.length(); i++) {
            int count = 1;
            while ((i < longText.length() - 1) && (longText.charAt(i) == longText.charAt(i+1))) {
                count++;
                i++;
            }
            encodedText.append(count + String.valueOf(longText.charAt(i)).toUpperCase() + " ");
        }
        return encodedText.toString();
    }
}