package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Bag bag = new Bag();
    private final Board board = new Board();
    private final Dictionary dictionary = new Dictionary();
    private final List<Player> players = new ArrayList<>();

    public void addPlayer(Player player){
        players.add(player);
        player.setGame(this);
    }

    public void play() {
        for (Player player : players){

        }
    }

    public Bag getBag(){
        return bag;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.addPlayer(new Player("Player 1"));
        game.addPlayer(new Player("Player 1"));
        game.addPlayer(new Player("Player 1"));
        game.play();
    }
}
