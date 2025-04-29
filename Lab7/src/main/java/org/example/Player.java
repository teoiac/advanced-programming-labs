package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player implements Runnable {
    private String name;
    private Game game;
    private Dictionary dictionary = new Dictionary();
    private boolean running = true;
    private List<Tile> lastExtracted = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public List<Tile> getLastExtracted() {
        return lastExtracted;
    }


    private void permute(String prefix, String letters, int targetLength, List<String> results, boolean[] used) {
        if (prefix.length() == targetLength) {
            results.add(prefix);
            return;
        }
        for (int i = 0; i < letters.length(); i++) {
            if (used[i]) continue;
            used[i] = true;
            permute(prefix + letters.charAt(i), letters, targetLength, results, used);
            used[i] = false;
        }
    }

    private List<String> generatePermutations(String letters, int length) {
        List<String> permutations = new ArrayList<>();
        permute("", letters, length, permutations, new boolean[letters.length()]);
        return permutations;
    }

    public String makeWord(List<Tile> tiles) {
        StringBuilder lettersBuilder = new StringBuilder();
        for (Tile tile : tiles) {
            lettersBuilder.append(tile.getLetter());
        }
        String letters = lettersBuilder.toString();

        for (int len = letters.length(); len >= 2; len--) {
            List<String> permutations = generatePermutations(letters, len);
            for (String word : permutations) {
                if (dictionary.isWord(word)) {
                    return word;
                }
            }
        }
        return null;
    }


    private boolean submitWord() {
        game.getBoard().waitForTurn(this, game.getPlayers());
        lastExtracted = game.getBag().extractTiles(7);
        System.out.println("Extracted tiles: " + lastExtracted);
        if (lastExtracted.isEmpty()) {
            game.getBoard().nextTurn(game.getPlayers());
            return false;
        }

        String word = makeWord(lastExtracted);

        if (word == null) {
            System.out.println(name + " couldn't make a word. Discarding tiles and trying again.");
            game.getBag().returnTiles(lastExtracted);
            lastExtracted = game.getBag().extractTiles(7);
            if (lastExtracted.isEmpty()) {
                game.getBoard().nextTurn(game.getPlayers());
                return false;
            }

            word = makeWord(lastExtracted);
            if (word == null) {
                System.out.println(name + " still couldn't form a word. Skipping turn.");
                game.getBoard().nextTurn(game.getPlayers());
                return true;
            }
        }

        game.getBoard().addWord(this, word);
        game.getBoard().nextTurn(game.getPlayers());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return true;
    }


    public void stopRunning() {
        this.running = false;
    }

    @Override
    public void run() {
        System.out.println(name + " started playing");
        while (running) {
            if (!submitWord()) {
                break;
            }
        }
        System.out.println(name + " finished playing");
    }
}