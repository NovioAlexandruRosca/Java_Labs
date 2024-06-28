package org.example;

public class TimekeeperDaemon extends Thread {
    private static final int TIME_LIMIT_SECONDS = 10;

    @Override
    public void run() {
        int secondsElapsed = 0;
        while (!GameLobby.checkIfGameIsOver() && secondsElapsed < TIME_LIMIT_SECONDS) {
            try {
                Thread.sleep(1000);
                secondsElapsed++;
                System.out.println("Time elapsed: " + secondsElapsed + " seconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (secondsElapsed >= TIME_LIMIT_SECONDS) {
            System.out.println("Time limit exceeded! Game over.");
            GameLobby.setExcededTimeLimit();
        }
    }
}
