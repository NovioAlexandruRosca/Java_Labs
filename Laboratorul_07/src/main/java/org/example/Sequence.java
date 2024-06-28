package org.example;

import java.util.ArrayList;
import java.util.List;

public class Sequence {
    private final List<Token> tokens;

    public Sequence(Token token) {
        tokens = new ArrayList<>();
        tokens.add(token);
    }

    public boolean canExtend(Token token) {
        int lastNumber = tokens.get(tokens.size() - 1).number2;
        return token.number1 == lastNumber;
    }

    public void extend(Token token) {
        tokens.add(token);
    }

    public int getValue() {
        return tokens.size();
    }
}