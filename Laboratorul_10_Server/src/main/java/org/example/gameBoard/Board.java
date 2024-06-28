package org.example.gameBoard;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int size = 10;
    private final List<List<Character>> grid;

    public Board() {
        grid = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add('-');
            }
            grid.add(row);
        }
    }

    public boolean placeShip(int x, int y, int length, boolean horizontal) {
        if (horizontal) {
            if (y + length > size) return false;
            for (int i = 0; i < length; i++) {
                if (grid.get(x).get(y + i) != '-') return false;
            }
            for (int i = 0; i < length; i++) {
                grid.get(x).set(y + i, 'S');
            }
        } else {
            if (x + length > size) return false;
            for (int i = 0; i < length; i++) {
                if (grid.get(x + i).get(y) != '-') return false;
            }
            for (int i = 0; i < length; i++) {
                grid.get(x + i).set(y, 'S');
            }
        }
        return true;
    }

    public boolean attack(int x, int y) {
        if (grid.get(x).get(y) == 'S') {
            grid.get(x).set(y, 'H');
            return true;
        } else if (grid.get(x).get(y) == '-') {
            grid.get(x).set(y, 'M');
            return false;
        }
        return false;
    }

    public List<List<Character>> getGrid() {
        return grid;
    }
}
