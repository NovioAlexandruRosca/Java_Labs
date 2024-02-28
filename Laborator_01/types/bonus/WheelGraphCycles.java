package types.bonus;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import utility.Utility;

/**
 * The WheelGraphCycles class calculates and displays cycles in a Wheel Graph.
 * Extends the functionality of the WheelGraph class.
 * 
 * <p>Author: Rosca Alexandru-David</p>
 * <p>Group: A4</p>
 * <p>Year: 2nd</p>
 */
public class WheelGraphCycles extends WheelGraph {

     /** The adjacency matrix representing the graph. */
    static int[][] adjacencyMatrix;

    /** Stores the cycles found in the graph. */
    static ArrayList<ArrayList<Integer>> cycles = new ArrayList<>();

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(WheelGraphCycles.class.getName());

    /** StringBuilder used to output messages. */
    static StringBuilder outputMessage = new StringBuilder();

    /**
     * Main method for finding and displaying cycles in the Wheel Graph
     * It also validates the given arguments to make sure the searched graph exists.
     * 
     * @param args the command-line arguments containing the number of vertices
     */
    public static void main(String[] args) {

        int n = argumentValidation(args, 1);
        if(n == -1)
            return;

        adjacencyMatrix = matrixGenerator(n);

        discoverCycles();
        displayCycles(n);

    }

    /**
     * Finds cycles in the graph starting from a given node.
     * 
     * @param possibleCycle the current cycle being explored
     */
    protected static void findCycles(ArrayList<Integer> possibleCycle) {
        int startNode = possibleCycle.get(0);
    
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[startNode][i] == 1 && startNode != i) {
                exploreCycle(possibleCycle, i);
            }
        }
    }
    
    /**
     * Explores the cycle and continues searching for cycles recursively if
     * the cycle doesnt contain the next node,
     * otherwise if the size of the found "cycle" is large enough and the next value 
     * creates a cycle we process the cycle
     * 
     * @param possibleCycle the current cycle being explored
     * @param nextNode      the next node to explore
     */
    private static void exploreCycle(ArrayList<Integer> possibleCycle, int nextNode) {
        if (!possibleCycle.contains(nextNode)) {
            ArrayList<Integer> partialCycle = new ArrayList<>(possibleCycle);
            partialCycle.add(0, nextNode);
            findCycles(partialCycle);
        } else if (nextNode == possibleCycle.get(possibleCycle.size() - 1) && possibleCycle.size() > 2) {
            processCycle(possibleCycle);
        }
    }
    
    /**
     * Processes and adds the discovered cycle to the list of cycles.
     * By processing we rotate the cycle so that the smallest value in the cycle is first(and last)
     * This helps with the verification that a cycle was already found
     * We reverse the cycle as well to make sure it didnt appear before(otherwise there would be double the cycles)
     * 
     * @param possibleCycle the cycle to be processed
     */
    private static void processCycle(ArrayList<Integer> possibleCycle) {
        ArrayList<Integer> straightCycle = rotateToSmallest(possibleCycle);
        ArrayList<Integer> inverseCycle = rotateToSmallest(reverse(possibleCycle));
        if (isNewCycle(straightCycle) && isNewCycle(inverseCycle)) {
            cycles.add(straightCycle);
        }
    }

    /**
     * Rotates the cycle to the smallest node.
     * It searches for the smallest value and gets the index 
     * where it is located and then it rearranges the cycle 
     * so that the smallest value is first
     * 
     * @param possibleCycle the cycle to be rotated
     * @return the rotated cycle
     */
    protected static ArrayList<Integer> rotateToSmallest(ArrayList<Integer> possibleCycle) {
        int smallest = possibleCycle.get(0);
        int n = 0;
        int i = -1;

        for (Integer x : possibleCycle) {
            if (x < smallest) {
                smallest = x;
            }
        }
        for (Integer x : possibleCycle) {
            i++;
            if (x == smallest) {
                n = i;
            }
        }

        ArrayList<Integer> rotated = new ArrayList<>();
        rotated.addAll(possibleCycle.subList(n, possibleCycle.size()));
        rotated.addAll(possibleCycle.subList(0, n));
        return rotated;
    }

    /**
     * Reverses the order of nodes in a cycle.
     * 
     * @param possibleCycle the cycle to be reversed
     * @return the reversed cycle
     */
    protected static ArrayList<Integer> reverse(ArrayList<Integer> possibleCycle) {
        ArrayList<Integer> reversedCycle = new ArrayList<>();
        for (int i = possibleCycle.size() - 1; i >= 0; i--) {
            reversedCycle.add(possibleCycle.get(i));
        }
        return reversedCycle;
    }

    /**
     * Checks if the discovered cycle has been searched for before
     * 
     * @param possibleCycle the cycle to be checked
     * @return true if the cycle is new, false otherwise
     */
    protected static boolean isNewCycle(ArrayList<Integer> possibleCycle) {
        for (ArrayList<Integer> cycle : cycles) {
            if (cycle.equals(possibleCycle)) return false;
        }
        return true;
    }

    /**
     * Discovers all the possible cycles in the graph
     * by starting the search from each and every vertice 
     */
    protected static void discoverCycles(){
        for (int i = 0; i < adjacencyMatrix.length ; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    ArrayList<Integer> possibleCycle = new ArrayList<>();
                    possibleCycle.add(j);
                    findCycles(possibleCycle);
                }
            }
        }
    }

    /**
     * Displays the cycles found in the graph.
     * 
     * @param n the number of vertices in the graph
     */
    protected static void displayCycles(int n){
        int cycleNumber = 0;
        for (ArrayList<Integer> cycle : cycles) {
            cycleNumber++;

            StringBuilder outputCycle = new StringBuilder().append("\nCycle #" + cycleNumber + ": [");
            boolean firstSpace = false;

            for (Integer node : cycle) {

                outputCycle.append((!firstSpace ? "" : " ") + node + ",");
                firstSpace = true;
            }

            outputCycle.append(" " + cycle.get(0)).append("]");
            outputMessage.append(Utility.textColoring(outputCycle.toString(), Utility.ansiEscapeCodes.GREEN));
        }

        outputMessage.append( Utility.textColoring("\nThere are exactly: " + n + " * " + n + " - 3 * " + n + " + 3 = " + (n * n - 3 * n + 3) + " cycles in a " + n + "-Wheel Graph", Utility.ansiEscapeCodes.GREEN));
        logger.log(Level.INFO, outputMessage.toString());
    }
}