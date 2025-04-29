package org.example;

import java.util.*;

public class Bag {
    private final Map<Tile, Integer> letters = new HashMap<>();
    private final Random random = new Random();
    public List<Tile> generatedTiles = new ArrayList<>();

    public Bag() {
        for (char c = 'a'; c <= 'z'; c++) {
            Tile tile = new Tile(c);
            generatedTiles.add(tile);
            letters.put(tile, 10);
        }

    }

    public synchronized List<Tile> extractTiles(int howMany) {
        List<Tile> extracted = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            if (letters.isEmpty()) {
                break;
            }
            List<Tile> available = new ArrayList<>(letters.keySet());
            Tile pickedTile = available.get(random.nextInt(available.size()));
            extracted.add(pickedTile);
            int count = letters.get(pickedTile);
            if (count == 1) {
                letters.remove(pickedTile);
            } else
                letters.put(pickedTile, count - 1);
        }
        return extracted;
    }

    public synchronized void returnTiles(List<Tile> returnedTiles) {
        for (Tile tile : returnedTiles) {
            letters.replace(tile, letters.get(tile) + 1);
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Bag{");
        for (Map.Entry<Tile, Integer> entry : letters.entrySet()) {
            Tile tile = entry.getKey();
            int count = entry.getValue();
            sb.append("Tile{letter='").append(tile.getLetter())
                    .append("', points=").append(tile.getPoints())
                    .append("} x ").append(count).append("\n");
        }
        if (sb.length() > 4) {
            sb.setLength(sb.length() - 2);
        }
        sb.append('}');
        return sb.toString();
    }

}
