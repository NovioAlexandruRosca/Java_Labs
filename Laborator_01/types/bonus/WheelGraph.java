package types.bonus;

import types.homework.CommonException;
import utility.Utility;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The WheelGraph class generates and displays a wheel graph based on user input.
 * It validates the input arguments, generates the adjacency matrix for the wheel graph,
 * and displays the matrix.
 *
 * <p> Author: Rosca Alexandru-David </p>
 * <p> Group: A4 </p>
 * <p> Year: 2nd </p>
 */
public class WheelGraph {

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(WheelGraph.class.getName());

    /**
     * The main method of the WheelGraph application.
     * It validates the command-line arguments, generates the adjacency matrix,
     * and displays the matrix.
     *
     * @param args the command-line arguments containing the number of vertices
     */
    public static void main(String[] args){

        int n = argumentValidation(args, 1);
        if(n == -1)
            return;

        int[][] adjacencyMatrix = matrixGenerator(n);

        displayMatrix(adjacencyMatrix);
    }

    /**
     * Validates the command-line arguments and returns the number of vertices.
     * It checks if the correct number of arguments is provided and if the number of vertices is valid.
     *
     * @param args the command-line arguments containing the number of vertices
     * @param numberOfArguments the expected number of arguments
     * @return the number of vertices provided in the argument
     */
    protected static int argumentValidation(String[] args, int numberOfArguments)
    {
        int number = -1;

        try {

            if (args.length < numberOfArguments) {
                throw new CommonException("You haven't provided enough arguments!");
            }
            else if(args.length > numberOfArguments) {
                throw new CommonException("You have provided too many arguments!");
            }

            number = Integer.parseInt(args[0]);

            if(number < 4)
                throw new CommonException("You can't have a wheel graph with less than 4 vertices");

            String message = Utility.textColoring("The number of vertices you have provided is: " + number, Utility.ansiEscapeCodes.GREEN);
            logger.log(Level.INFO, message);

            return number;

        } catch (NumberFormatException e) {
            String message = Utility.textColoring("One of the argument isn't a number!", Utility.ansiEscapeCodes.RED);
            logger.log(Level.SEVERE, message);
        } catch (CommonException e) {
            String message = Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED);
            logger.log(Level.SEVERE, message);
        }

        return -1;
    }

    /**
     * Generates the adjacency matrix for the wheel graph based on the number of vertices.
     *
     * @param n the number of vertices in the wheel graph
     * @return the adjacency matrix of the wheel graph
     */
    protected static int[][] matrixGenerator(int n){
        int[][] adjacencyMatrix = new int[n][n];
        adjacencyMatrix[0][n - 2] = 1;
        adjacencyMatrix[n - 2][0] = 1; // Connect the first land last vertice

        for(int i = 0 ; i < n - 1 ; i++)
            for(int j = 0 ; j < n - 1 ; j++)
                {
                    if(i + 1 == j){
                        adjacencyMatrix[i][j] = 1;
                        adjacencyMatrix[j][i] = 1;
                    }
                }                      // Connect the vertices in a circular pattern

        for(int i = 0 ; i < n - 1 ; i++){
            adjacencyMatrix[n - 1][i] = 1;
            adjacencyMatrix[i][n - 1] = 1;
        }

        return adjacencyMatrix;
    }

    /**
     * Displays the adjacency matrix of the wheel graph.
     *
     * @param matrix the adjacency matrix of the wheel graph
     */
    protected static void displayMatrix(int[][] matrix){

        StringBuilder matrixDisplay = new StringBuilder().append("\n");

        for(int i = 0 ; i < matrix[0].length ; i++){
            for(int j = 0 ; j < matrix[0].length ; j++){
                matrixDisplay.append(matrix[i][j]).append(" ");
            }
            matrixDisplay.append("\n");
        }

        logger.log(Level.INFO, Utility.textColoring(matrixDisplay.toString(), Utility.ansiEscapeCodes.GREEN));

    }
}
