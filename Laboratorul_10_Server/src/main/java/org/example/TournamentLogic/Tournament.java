package org.example.TournamentLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tournament {
    private final List<Integer> playersId;

    public Tournament() {
        playersId = new ArrayList<>();
    }

    public void addPlayer(Integer player) {
        playersId.add(player);
    }

    public List<Integer> getPlayersId() {
        return Collections.unmodifiableList(playersId);
    }

    public List<List<Integer>> generatePairings() {
        List<List<Integer>> pairings = new ArrayList<>();

        for (int i = 0; i < playersId.size(); i++) {
            for (int j = i + 1; j < playersId.size(); j++) {
                List<Integer> pairing = new ArrayList<>();
                pairing.add(playersId.get(i));
                pairing.add(playersId.get(j));
                pairings.add(pairing);
            }
        }

        Collections.shuffle(pairings);

        return pairings;
    }

}
