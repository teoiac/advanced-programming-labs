package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board {
    private final Set<String> words = new HashSet<>();
    private final Map<Player, Integer> playerScores = new HashMap<>();
    private final Dictionary dictionary;
    private int currentPlayerIndex = 0;

    public Board(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public synchronized void addWord(Player player, String word) {
        if (!dictionary.isWord(word)) {
            System.out.println(player.getName() + " submitted invalid word: " + word);
            return;
        }

        if (words.contains(word)) {
            System.out.println(player.getName() + " submitted already used word: " + word);
            return;
        }

        words.add(word);
        int score = calculateScore(word, player.getLastExtracted());
        playerScores.put(player, playerScores.getOrDefault(player, 0) + score);
        System.out.println(player.getName() + " added word: " + word + " - Score: " + score + " - Total: " + playerScores.get(player));
    }

    private int calculateScore(String word, List<Tile> tiles) {
        int score = 0;
        for (Tile tile : tiles) {
            if (word.indexOf(tile.getLetter()) >= 0) {
                score += tile.getPoints();
            }
        }
        return score;
    }


    public synchronized void waitForTurn(Player player, List<Player> players) {
        while (players.indexOf(player) != currentPlayerIndex) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public synchronized void nextTurn(List<Player> players) {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        notifyAll();
    }

    public Map<Player, Integer> getPlayerScores() {

        return new HashMap<>(playerScores);

    }

    public Player getWinner() {
        Player winner = null;
        int maxScore = -1;

        for (Map.Entry<Player, Integer> entry : playerScores.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                winner = entry.getKey();
            }
        }
        return winner;
    }
}