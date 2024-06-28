package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import utility.Utility;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;

public class SmartConcurrentPlayer extends ConcurrentPlayer {

    public SmartConcurrentPlayer(Integer id, String name, BlockingQueue<Token> bag) {
        super(id, name, bag);
    }


    /**
     * each player takes a token from the bag per turn and adds them to their collection
     */
    @Override
    public void run() {
        while (true) {
            try {
                GameLobby.waitForTurn(id);
                if (!GameLobby.checkIfGameIsOver()) {
                    Token token = GameLobby.getToken();
                    tokens.add(token);


                    Graph<Integer, DefaultEdge> graph = createGraphFromTokens(tokens);

                    int maxSequenceValue;

                    if ( checkOresCondition(graph)) {
                        int possibleSize = CrissCrossHeuristic.crissCrossAlgorithm(graph).size();
                        maxSequenceValue = possibleSize == 0 ? calculateMaxSequenceValue() : possibleSize;

                    } else {
                        maxSequenceValue = calculateMaxSequenceValue();
                    }
                    GameLobby.updateScore(name, maxSequenceValue);
                    logger.log(Level.INFO, Utility.textColoring(name + " max value is: " + maxSequenceValue, Utility.ansiEscapeCodes.GREEN));
                } else {
                    break;
                }
            } catch (InterruptedException e) {
                logger.log(Level.INFO, Utility.textColoring("There was an interruption error", Utility.ansiEscapeCodes.RED));
            }
            GameLobby.nextPlayerTurn();
        }
    }

    /**
     *
     * @param tokens the list of tokens that the player has
     * @return a graph represented by the tokens
     */
    private static Graph<Integer, DefaultEdge> createGraphFromTokens(List<Token> tokens) {
        Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        for (Token token : tokens) {
            int x = token.number1;
            int y = token.number2;
            graph.addVertex(x);
            graph.addVertex(y);
            graph.addEdge(x, y);
        }

        return graph;
    }

    /**
     * Checks to see if the Ores Condition applies
     * @param graph the given Graph
     * @return yes if the ores condition applies and no otherwise
     */
    private static boolean checkOresCondition(Graph<Integer, DefaultEdge> graph) {
        int numVertices = graph.vertexSet().size();

        for (Integer u : graph.vertexSet()) {
            for (Integer v : graph.vertexSet()) {
                if (!u.equals(v) && !graph.containsEdge(u, v)) {
                    int degreeSum = graph.degreeOf(u) + graph.degreeOf(v);
                    if (degreeSum < numVertices) {
                        return false;
                    }
                }
            }
        }

        return true;
    }


}
