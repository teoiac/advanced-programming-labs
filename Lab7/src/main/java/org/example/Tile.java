package org.example;

import java.util.Random;

public class Tile {
    private final char letter;
    private final int points;
    public Tile(char letter) {
        this.letter = letter;
        Random random = new Random();
        this.points= random.nextInt(10) +1;

    }

    public char getLetter() {
        return letter;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "Tile{" + "letter=" + letter + ", points=" + points + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter;
    }

    @Override
    public int hashCode() {
        return Character.hashCode(letter);
    }


}
