package org.example;

import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class CrissCrossHeuristic {
    /**
     * the crisscross algorithm
     * @param graph  the graph that contains the tokens
     * @return a list of pairs who are the sequences
     */
    public static List<Pair> crissCrossAlgorithm(Graph<Integer, DefaultEdge> graph) {
        List<Pair> spanningCycle = new ArrayList<>();
        List<Integer> vertices = new ArrayList<>(graph.vertexSet());
        int n = vertices.size();

        List<Integer> circularOrder = new ArrayList<>(vertices);

        boolean foundGap;
        do {
            foundGap = false;
            for (int i = 0; i < n; i++) {
                int u = circularOrder.get(i);
                int v = circularOrder.get((i + 1) % n);

                if (!areAdjacent(graph, u, v)) {
                    foundGap = true;

                    for (int j = 0; j < n; j++) {
                        int x = circularOrder.get(j);
                        int y = circularOrder.get((j + 1) % n);
                        if (x != u && y != v && areAdjacent(graph, u, y) && areAdjacent(graph, v, x)) {

                            List<Integer> newOrder = new ArrayList<>();
                            for (int k = 0; k <= i; k++) {
                                newOrder.add(circularOrder.get(k));
                            }
                            newOrder.add(y);
                            newOrder.add(v);
                            for (int k = j; k > i; k--) {
                                newOrder.add(circularOrder.get(k));
                            }
                            for (int k = (j + 1) % n; k < n; k++) {
                                newOrder.add(circularOrder.get(k));
                            }
                            circularOrder = newOrder;
                            spanningCycle.add(new Pair(u, v));
                            break;
                        }
                    }
                }
            }
        } while (foundGap);

        return spanningCycle;
    }

    /**
     * function to check if 2 edges are adjacent in the graph
     * @param graph the graph
     * @param u the first vertice
     * @param v the second vertice
     * @return true if they are adjacent false otherwise
     */
    private static boolean areAdjacent(Graph<Integer, DefaultEdge> graph, int u, int v) {
        return graph.containsEdge(u, v) || graph.containsEdge(v, u);
    }
}

/**
 * A pair of consecutive vertices
 */
class Pair {
    int u;
    int v;

    Pair(int u, int v) {
        this.u = u;
        this.v = v;
    }
}