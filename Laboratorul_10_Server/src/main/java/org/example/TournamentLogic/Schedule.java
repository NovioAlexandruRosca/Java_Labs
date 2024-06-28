package org.example.TournamentLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
    private final List<List<Integer>> pairings;
    private final int maxGamesPerDay;
    private final int maxDays;

    public Schedule(List<List<Integer>> pairings, int maxGamesPerDay, int maxDays) {
        this.pairings = pairings;
        this.maxGamesPerDay = maxGamesPerDay;
        this.maxDays = maxDays;
    }

    public Map<Integer, List<List<Integer>>> createSchedule() throws Exception {
        Map<Integer, List<List<Integer>>> schedule = new HashMap<>();
        Map<Integer, Integer> playerGamesCount = new HashMap<>();

        int day = 1;
        for (List<Integer> pairing : pairings) {
            int player1 = pairing.get(0);
            int player2 = pairing.get(1);

            playerGamesCount.putIfAbsent(player1, 0);
            playerGamesCount.putIfAbsent(player2, 0);

            while (playerGamesCount.get(player1) >= maxGamesPerDay ||
                    playerGamesCount.get(player2) >= maxGamesPerDay) {
                day++;
                if (day > maxDays) {
                    throw new Exception("Cannot schedule the tournament within the given constraints");
                }
            }

            schedule.putIfAbsent(day, new ArrayList<>());
            schedule.get(day).add(pairing);

            playerGamesCount.put(player1, playerGamesCount.get(player1) + 1);
            playerGamesCount.put(player2, playerGamesCount.get(player2) + 1);
        }

        return schedule;
    }
}
