package org.example;

import utility.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing the simulation of the token bag game, it generates tokens players and starts the threads
 */
public class GameLobby {

    /**
     * Logger for logging information
     * */
    private static final Logger logger = Logger.getLogger(GameLobby.class.getName());

    /**
     * theMaxLength
     */
    private static Integer n;

    private static volatile boolean timeLimitExceded = false;

    /**
     * the number of players
     */
    private static int numPlayers = 4;

    /**
     * the number of generated tokens
     */
    private static int numTokens = 100;

    /**
     * the bag of tokens from which players will take tokens
     */
    private static BlockingQueue<Token> bag;

    /**
     * and indicator to tell the players which turn it is
     */
    private static Integer playersTurn = 1;

    /**
     * a list of all the players
     */
    static ConcurrentPlayer[] concurrentPlayers = new ConcurrentPlayer[numPlayers];

    private static final Object lock = new Object();

    private static Map<String, Integer> score = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        playersTurn = 1;
        n = 10;
        bag = new ArrayBlockingQueue<>(numTokens);
        generateTokens();
        generatePlayers();
        run();
        displayMessage();
    }

    private static void displayMessage() {
        while(!checkIfGameIsOver());
        logger.log(Level.INFO, "Game has ended!",Utility.ansiEscapeCodes.GREEN);

        Integer max = Integer.MIN_VALUE;
        String nameOfWinner = "";

        for (Map.Entry<String, Integer> entry : score.entrySet()) {
            if(entry.getValue() > max){
                max = entry.getValue();
                nameOfWinner = entry.getKey();
            }
        }

        logger.log(Level.INFO, Utility.textColoring(nameOfWinner + " has won the game", Utility.ansiEscapeCodes.GREEN));

    }

    /**
     * Generate tokens and add them to the bag.
     */
    private static void generateTokens(){
        int countOfTokens = 0;
        List<Token> tokens = new ArrayList<>();
        for (int i = 1; i <= numTokens; i++) {
            for (int j = 1; j <= Math.sqrt(numTokens) && countOfTokens < numTokens; j++) {
                if(i != j) {
                    Token token = new Token(i, j);
                    tokens.add(token);
                    countOfTokens++;
                }
            }
        }

        Collections.shuffle(tokens);

        bag.addAll(tokens);
    }

    /**
     * generetates players
     */
    private static void generatePlayers(){
        for (int i = 0; i < numPlayers - 1; i++) {
            concurrentPlayers[i] = new ConcurrentPlayer(i + 1,"Player " + (i + 1), bag);
        }
        concurrentPlayers[numPlayers - 1] = new SmartConcurrentPlayer(numPlayers, "Smart Player", bag);
    }

    /**
     * starts the threads of each player
     */
    private static void run(){
        TimekeeperDaemon timekeeperDaemon = new TimekeeperDaemon();
        timekeeperDaemon.setDaemon(true);
        timekeeperDaemon.start();
        for (ConcurrentPlayer concurrentPlayer : concurrentPlayers) {
            Thread thread = new Thread(concurrentPlayer);
            thread.start();
        }
    }

    /**
     * it increments the players turn
     */
    public static void nextPlayerTurn(){
        if(playersTurn == numPlayers)
            playersTurn = 1;
        else
            playersTurn++;

        synchronized (lock) {
            lock.notifyAll();
        }
    }

    /**
     * Method to wait for the player's turn
     */
    public static void waitForTurn(Integer playerId) throws InterruptedException {
        synchronized (lock) {
            while (!playersTurn.equals(playerId) && !checkIfGameIsOver()) {
                lock.wait();
            }
        }
    }

    /**
     * Updates the score of a player in the gamesimulation
     * @param name the name of the player
     * @param length the score of the player
     */
    public static void updateScore(String name, Integer length){
        score.put(name, length);
    }

    public static Token getToken() throws InterruptedException {
        return bag.take();
    }

    /**
     * variable to let know the main thread that the game is over
     */
    public static void setExcededTimeLimit(){
        timeLimitExceded = true;
    }

    protected static boolean checkIfGameIsOver() {
        boolean gameOver = bag.isEmpty() || timeLimitExceded;

        if (!gameOver) {
            for (Integer value : score.values()) {
                if (value >= n) {
                    gameOver = true;
                    break;
                }
            }
        }

        return gameOver;
    }

    protected static void notifyAllPlayers() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }

}