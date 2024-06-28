package org.example;

import utility.Utility;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Token {

    private static final Logger logger = Logger.getLogger(Token.class.getName());
    int number1;
    int number2;

    public Token(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    /**
     * Checks if this token can be the next token in a sequence, given another token.
     *
     * @param otherToken the other token
     * @return true if this token can be the next token, false otherwise
     */
    public boolean canBeNext(Token otherToken) {
        return this.number2 == otherToken.number1;
    }
    @Override
    public String toString() {
        return "(" + number1 + ", " + number2 + ")";
    }
}