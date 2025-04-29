package org.example;


import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Bag bag = new Bag();
    private final Dictionary dictionary = new Dictionary();
    private final Board board = new Board(dictionary);
    private final List<Player> players = new ArrayList<>();
    private volatile boolean gameRunning = true;
    private final int timeLimit = 30;

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    public Bag getBag() {
        return bag;
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void stopGame() {
        gameRunning = false;
        for (Player player : players) {
            player.stopRunning();
        }
    }

    public void play() {
        Thread timekeeper = new Thread(new Timekeeper(this, timeLimit));
        timekeeper.setDaemon(true);
        timekeeper.start();

        // Start all player threads
        List<Thread> playerThreads = new ArrayList<>();
        for (Player player : players) {
            Thread t = new Thread(player);
            playerThreads.add(t);
            t.start();
        }

        // Wait for all player threads to finish
        for (Thread t : playerThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Determine and announce the winner
        Player winner = board.getWinner();
        if (winner != null) {
            System.out.println("\nThe winner is: " + winner.getName() +
                    " with score: " + board.getPlayerScores().get(winner));
        } else {
            System.out.println("\nGame ended with no winner.");
        }
    }
}