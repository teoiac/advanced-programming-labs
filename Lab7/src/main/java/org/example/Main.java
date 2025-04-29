package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String args[]) {
        System.out.println("**** Word Game ****");

        // Create game with default settings
        Game game = new Game();


        // Add players
        game.addPlayer(new Player("teo    "));
        game.addPlayer(new Player("rebecca"));
        game.addPlayer(new Player("maria  "));

        System.out.println("Starting game with 3 players");
        System.out.println("---------------------------------------------");
        System.out.println(game.getBag().toString());
        System.out.println("---------------------------------------------");


        // Play the game
        game.play();
    }
}