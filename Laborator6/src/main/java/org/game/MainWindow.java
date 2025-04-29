package org.example;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * MainWndow class represents the main GUI of our application
 *
 */

public class MainWindow {
    private JFrame window;
    private DrawingPanel drawingPanel;

    public MainWindow() {
        initialize();
    }

    /**
     * Initializes the main components of the window
     * Configures the drawing panel, configuration panel and control panel
     */
    public void initialize() {
        window = new JFrame();
        window.setLayout(new BorderLayout(10, 5));
        window.setTitle("Connect the Dots");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);

        // drawing panel
        drawingPanel = new DrawingPanel();
        window.add(drawingPanel, BorderLayout.CENTER);

        // configuration panel
        JPanel configPanel = new JPanel();
        configPanel.setBackground(Color.PINK);
        JTextField dotInputField = new JTextField(5);
        JButton createGameButton = new JButton("Create New Game");

        createGameButton.addActionListener(e -> {
            try {
                int numDots = Integer.parseInt(dotInputField.getText());
                drawingPanel.createDots(numDots);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(window, "Please enter a valid number of dots.");
            }
        });


        configPanel.add(new JLabel("Number of Dots:"));
        configPanel.add(dotInputField);
        configPanel.add(createGameButton);
        window.add(configPanel, BorderLayout.NORTH);

        // control panel
        JPanel controlPanel = new JPanel();
        JButton exitButton = new JButton("Exit");
        JButton exportButton = new JButton("Export");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        exitButton.addActionListener(e -> window.dispose());
        controlPanel.add(exitButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(exportButton);
        window.add(controlPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            File saveFile = new File("gameState.txt");
            drawingPanel.saveGameState(saveFile);
            JOptionPane.showMessageDialog(window, "Game saved successfully!");
        });

        loadButton.addActionListener(e -> {
            File loadFile = new File("gameState.txt");
            if (loadFile.exists()) {
                drawingPanel.loadGameState(loadFile);
                JOptionPane.showMessageDialog(window, "Game loaded successfully!");
            } else {
                JOptionPane.showMessageDialog(window, "Save file not found!");
            }
        });

        exportButton.addActionListener(e -> {
            File exportFile = new File("canvas.png");
            drawingPanel.exportCanvas(exportFile);
            JOptionPane.showMessageDialog(window, "Canvas exported as canvas.png!");
        });

        window.setVisible(true);
    }
}
