package org.example;

import javax.swing.*;

/**
 * Launcher class is the entry point for the currrent application
 * It initialixes the main window of the application
 */
public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow main = new MainWindow();

            }
        });
    }
}