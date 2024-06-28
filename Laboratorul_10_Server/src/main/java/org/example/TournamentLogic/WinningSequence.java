package org.example.TournamentLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinningSequence {
    private final Map<String, Integer> outcomes;

    public WinningSequence(Map<String, Integer> outcomes) {
        this.outcomes = outcomes;
    }

    public List<Integer> findWinningSequence(List<Integer> players) {
        Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
        for (Integer player : players) {
            adjacencyList.put(player, new ArrayList<>());
        }

        for (Map.Entry<String, Integer> entry : outcomes.entrySet()) {
            String[] match = entry.getKey().split(" vs ");
            int winner = entry.getValue();
            int loser = Integer.parseInt(match[0]) == winner ? Integer.parseInt(match[1]) : Integer.parseInt(match[0]);
            adjacencyList.get(winner).add(loser);
        }

        List<Integer> sequence = new ArrayList<>();
        boolean[] visited = new boolean[players.size()];

        for (Integer player : players) {
            if (!visited[players.indexOf(player)]) {
                findSequenceUtil(player, visited, sequence, adjacencyList);
            }
        }

        return sequence;
    }

    private void findSequenceUtil(Integer player, boolean[] visited, List<Integer> sequence, Map<Integer, List<Integer>> adjacencyList) {
        visited[player] = true;
        for (Integer neighbor : adjacencyList.get(player)) {
            if (!visited[neighbor]) {
                findSequenceUtil(neighbor, visited, sequence, adjacencyList);
            }
        }
        sequence.addFirst(player);
    }
}
