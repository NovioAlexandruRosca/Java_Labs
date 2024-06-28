package org.example;

import utility.Utility;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * a player that s part of the tokens game
 */
public class ConcurrentPlayer implements Runnable {

    /**
     * Logger for logging information
     * */
    protected static final Logger logger = Logger.getLogger(ConcurrentPlayer.class.getName());

    /**
     * an id of the player
     */
    protected final Integer id;

    /**
     * the name of the player
     */
    protected final String name;

    /**
     * the bag of tokens
     */
    protected final BlockingQueue<Token> bag;

    /**
     * the list of tokens a player has
     */
    protected final List<Token> tokens;

    public ConcurrentPlayer(Integer id, String name, BlockingQueue<Token> bag) {
        this.id = id;
        this.name = name;
        this.bag = bag;
        this.tokens = new ArrayList<>();
    }

    /**
     * calculates the maximum sequence a player can make uisng a greedy method
     * @return  the maximum sequence of tokens
     */
    protected int calculateMaxSequenceValue() {
        int maxSequenceValue = 0;
        Queue<Token> maxSequenceValues = new LinkedList<>();

        for (int i = 0; i < tokens.size(); i++) {
            int currentValue = 0;
            int currentIndex = i;

            boolean isGoodSequence = false;

            List<Boolean> visited = new ArrayList<>();
            for (int k = 0; k < tokens.size(); k++) {
                visited.add(false);
            }

            List<Token> currentSequenceValues = new ArrayList<>();

            outerLoop: while (!visited.get(currentIndex)){
                visited.add(currentIndex, true);

                currentValue++;
                int nextIndex = -1;
                Token currentToken = tokens.get(currentIndex);

                currentSequenceValues.add(currentToken);

                for (int j = 0; j < tokens.size(); j++) {

                    if (!visited.get(j) && tokens.get(j).canBeNext(currentToken)) {

                        if(currentSequenceValues.size() >= 2 && currentSequenceValues.getFirst().number1 == tokens.get(j).number2) {
                            currentSequenceValues.add(tokens.get(j));
                            isGoodSequence = true;
                            break outerLoop;
                        }
                        else {
                            nextIndex = j;
                            break;
                        }
                    }
                }

                if (nextIndex == -1) break;
                currentIndex = nextIndex;
            }

            if(currentValue > maxSequenceValue && isGoodSequence){
                maxSequenceValue = currentValue;
                maxSequenceValues.clear();
                for (int j = currentSequenceValues.size() - 1; j >=0 ; j--) {
                    maxSequenceValues.add(currentSequenceValues.get(j));
                }
            }
        }

        StringBuilder sequenceOfTokens = new StringBuilder();

        while(!maxSequenceValues.isEmpty()) {
            sequenceOfTokens.append(maxSequenceValues.poll()).append(" ");
        }

        logger.log(Level.INFO, Utility.textColoring(sequenceOfTokens.toString(), Utility.ansiEscapeCodes.GREEN));

        return maxSequenceValue;
    }

    /**
     * each player takes a token from the bag per turn and adds them to their collection
     */
    @Override
    public void run() {
        while (true) {
            try {
                GameLobby.waitForTurn(id);
                if (GameLobby.checkIfGameIsOver()) {
                        GameLobby.notifyAllPlayers();
                        break;
                }

                Token token = GameLobby.getToken();
                tokens.add(token);
                int maxSequenceValue = calculateMaxSequenceValue();
                GameLobby.updateScore(name, maxSequenceValue);

                logger.log(Level.INFO,
                        Utility.textColoring(name + " max value is: " + maxSequenceValue,
                                Utility.ansiEscapeCodes.GREEN));
            } catch (InterruptedException e) {
                logger.log(Level.INFO,
                        Utility.textColoring("There was an interruption error",
                                Utility.ansiEscapeCodes.RED));
            }

            GameLobby.nextPlayerTurn();
        }

    }
}


