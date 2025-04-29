package org.example;


public class Timekeeper implements Runnable {
    private final Game game;
    private final int timeLimit;

    public Timekeeper(Game game, int timeLimit) {
        this.game = game;
        this.timeLimit = timeLimit;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int elapsedSeconds = 0;
        while (elapsedSeconds < timeLimit) {
            try {
                Thread.sleep(1000);
                elapsedSeconds = (int)((System.currentTimeMillis() - startTime) / 1000);
                System.out.println("-----------------------");
                System.out.println("Game time: " + elapsedSeconds + " seconds");
                System.out.println("-----------------------");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("\nTime limit reached! Game over.");
        game.stopGame();
    }
}