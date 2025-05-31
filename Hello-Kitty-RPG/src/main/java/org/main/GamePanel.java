package org.Main;

import org.entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //screen settings
    final int originalTileSize = 32; // 32 x 32 Tile
    final int scale = 2;

    final int tileSize = originalTileSize * scale; // 64 x 64 Tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //1024 pixels
    final int screenHeight = tileSize * maxScreenRow; // 768 pixels

    KeyHandler keyHandler = new KeyHandler();

    //fps
    int FPS = 60;

    // using thread to keep program running until you stop it :)

    Thread gameThread;
    Player player = new Player(this, keyHandler);


    //default position

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 6;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // offscreen buffer - rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true); // focused to receive key input - like action listener

    }

    public void startGameThread() {
        gameThread = new Thread(this); // THIS -> THE GAME PANEL CLASS
        gameThread.start();

    }

    @Override
    public void run() {
        //core of the game
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
//
//    public void run(){
//        double drawInterval = 1000000000/FPS;
//        double delta =0;
//        long lastTime = System.nanoTime();
//        long currentTime;
//        while(gameThread !=null){
//            currentTime = System.nanoTime();
//            delta += (currentTime-lastTime)/drawInterval;
//            lastTime = currentTime;
//            if(delta >= 1){
//                update();
//                repaint();
//                delta--;
//            }
//        }
//    }

    public void update() {
        if (keyHandler.upPressed == true) {
            playerY -= playerSpeed;
        } else if (keyHandler.downPressed == true) {
            playerY += playerSpeed;
        } else if (keyHandler.leftPressed == true) {
            playerX -= playerSpeed;
        } else if (keyHandler.rightPressed == true) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g; // converting to 2d

        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
    }

}
