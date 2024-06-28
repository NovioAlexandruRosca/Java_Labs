package org.example.TournamentLogic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OutcomeGenerator {
    private final Map<Integer, List<List<Integer>>> schedule;

    public OutcomeGenerator(Map<Integer, List<List<Integer>>> schedule) {
        this.schedule = schedule;
    }

    public Map<String, Integer> generateOutcomes() {
        Map<String, Integer> outcomes = new HashMap<>();
        Random random = new Random();

        for (List<List<Integer>> dailyGames : schedule.values()) {
            for (List<Integer> game : dailyGames) {
                int player1 = game.get(0);
                int player2 = game.get(1);
                int winner = random.nextBoolean() ? player1 : player2;
                outcomes.put(player1 + " vs " + player2, winner);
            }
        }

        return outcomes;
    }
}
