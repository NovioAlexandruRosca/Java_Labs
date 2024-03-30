package org.example.utility;

/**
 * The Utility class provides utility methods for text coloring using ANSI escape codes.
 * 
 * <p> Author: Rosca Alexandru-David /p>
 * <p> Group: A4 </p>
 * <p> Year: 2nd </p>
 */
public final class Utility {

    public enum ansiEscapeCodes{
        GREEN,
        YELLOW,
        RED
    }

    // Private constructor to prevent instantiation of Utility class
    private Utility(){}

    /**
     * Applies text coloring to the given message based on the provided ANSI escape code type.
     *
     * @param message the message to be colored
     * @param type the ANSI escape code type specifying the color (GREEN, YELLOW, or RED)
     * @return the colored message
     */
    public static String textColoring(String message, ansiEscapeCodes type){

        StringBuilder newText = new StringBuilder();
        switch(type){
            case GREEN: newText.append("\u001B[32m"); break;      // ANSI escape code to change the color to Green
            case YELLOW: newText.append("\u001B[33m"); break;     // ANSI escape code to change the color to Yellow
            case RED: newText.append("\u001B[31m"); break;        // ANSI escape code to change the color to Red
        }

        newText.append(message);
        newText.append("\u001B[0m"); // Reset the color to default after the message

        return newText.toString();
    }
    
}