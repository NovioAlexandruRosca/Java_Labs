package org.example.gameLogic;

import org.example.gameBoard.Board;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private final Board player1Board;
    private final Board player2Board;
    private boolean player1Turn;
    private boolean gameEnded;
    private int player1Id;
    private int player2Id;
    private int numberOfBoatsToBePlaced;
    private Timer timer1;
    private Timer timer2;
    private int player1Time;
    private int player2Time;

    public Game() {
        player1Board = new Board();
        player2Board = new Board();
        player1Turn = true;
        gameEnded = false;
        numberOfBoatsToBePlaced = 6;
        player1Time = 600;
        player2Time = 600;
        player2Id = 0;
    }

    public void stopTimer1(){
        if(timer1 != null) {
            timer1.cancel();
        }
    }

    public void stopTimer2(){
        if(timer2 != null) {
            timer2.cancel();
        }
    }

    public boolean isGameEnded(){
        return gameEnded;
    }

    public void setPlayer1Id(int playerId){
        this.player1Id = playerId;
    }

    public void setPlayer2Id(int playerId){
        this.player2Id = playerId;
    }

    public int getPlayer1Id(){
        return player1Id;
    }

    public int getPlayer2Id(){
        return player2Id;
    }

    public Board getBoard(int player) {
        return player == player1Id ? player1Board : player2Board;
    }

    public String makeMove(int player, int x, int y) {

        if (gameEnded) {
            return "Game has already ended. Player " + (player1Turn ? "2" : "1" + " has won");
        }
        if(numberOfBoatsToBePlaced != 0) {
            return "All 3 ships need to be placed";
        }

        if ((player == 1 && !player1Turn) || (player == 2 && player1Turn)) {
            return "It's not your turn.";
        }

        boolean hit = player == 1 ? player2Board.attack(x, y) : player1Board.attack(x, y);
        player1Turn = !player1Turn;

        if (checkWin()) {
            gameEnded = true;
            stopTimer1();
            stopTimer2();
            return player == 1 ? "Player 1 wins!" : "Player 2 wins!";
        }

        return hit ? "Hit!" : "Miss!";
    }

    public String placeShip(int player, int x, int y, int length, boolean horizontal) {
        Board board = player == 1 ? player1Board : player2Board;
        boolean wasSuccessfullyPlaced = board.placeShip(x, y, length, horizontal);

        if(wasSuccessfullyPlaced) {
            numberOfBoatsToBePlaced--;
        }

        if(numberOfBoatsToBePlaced == 0){
            startTimers();
        }

        return wasSuccessfullyPlaced?
                "Ship placed successfully!" :
                "Cannot place ship there. Try again.";
    }

    private boolean checkWin() {
        return allShipsSunk(player1Board) || allShipsSunk(player2Board);
    }

    private boolean allShipsSunk(Board board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board.getGrid().get(i).get(j) == 'S') {
                    return false;
                }
            }
        }
        return true;
    }

    public void startTimers() {
        timer1 = new Timer();
        timer2 = new Timer();

        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                decrementTimer(1);
            }
        }, 0, 1000);

        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                decrementTimer(2);
            }
        }, 0, 1000);
    }

    public void decrementTimer(int playerId) {
        if (playerId == 1) {
            if (timer1 != null) {
                timer1.cancel();
            }
            timer1 = new Timer();
            timer1.scheduleAtFixedRate(new TimerTask() {
                int remainingTime = player1Time;
                @Override
                public void run() {
                    if (player1Turn) {
                        remainingTime--;
                        player1Time--;
                        System.out.println("Player 1's remaining time: " + remainingTime + " seconds");
                        if (remainingTime == 0) {
                            endGame();
                            timer1.cancel();
                        }
                    }
                }
            }, 0, 1000);
        } else {
            if (timer2 != null) {
                timer2.cancel();
            }
            timer2 = new Timer();
            timer2.scheduleAtFixedRate(new TimerTask() {
                int remainingTime = player2Time;
                @Override
                public void run() {
                    if (!player1Turn) {
                        remainingTime--;
                        player2Time--;
                        System.out.println("Player 2's remaining time: " + remainingTime + " seconds");
                        if (remainingTime == 0) {
                            endGame();
                            timer2.cancel();
                        }
                    }
                }
            }, 0, 1000);
        }
    }


    public void endGame() {
        gameEnded = true;
        player1Turn = !player1Turn;
    }
}
