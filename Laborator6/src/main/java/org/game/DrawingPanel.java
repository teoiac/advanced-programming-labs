package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

import org.components.Line;
import org.components.Point;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.alg.connectivity.ConnectivityInspector;

/**
 * DrawingPanel class represents the game interface where the dots are created and the player connect the dots
 * The game ends when all dots are connected
 * This class supports the load, save, and export functions
 */

public class DrawingPanel extends JPanel {

    private final List<org.components.Point> points = new ArrayList<>();
    private final List<Line> lines = new ArrayList<>();
    private final List<Line> player1Lines = new ArrayList<>();
    private final List<Line> player2Lines = new ArrayList<>();

    private org.components.Point selectedPoint = null;
    private boolean isPlayerOneTurn = true;

    public DrawingPanel() {
        setBackground(Color.WHITE);
        addMouseListener(new DotClickListener());
    }

    public void createDots(int numberOfDots) {
        points.clear();
        lines.clear();
        player1Lines.clear();
        player2Lines.clear();
        selectedPoint = null;

        Random rand = new Random();

        for (int i = 0; i < numberOfDots; i++) {
            int x = rand.nextInt(getWidth() - 2 * 10) + 10;
            int y = rand.nextInt(getHeight() - 2 * 10) + 10;
            points.add(new org.components.Point(i, x, y));
        }
        repaint();
    }


    private class DotClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            for (org.components.Point point : points) {
                if (point.isNear(e.getX(), e.getY())) {
                    handlePointSelection(point);
                    break;
                }
            }
        }
    }

    /**
     * Handler for the logic when a point is selected
     * If two points are selected a line is drawn between them with the specific color of the player
     * and then it switches the turn of the players
     *
     * @param point represents the point that has been selected
     */

    public void handlePointSelection(org.components.Point point) {
        if (selectedPoint == null) {
            selectedPoint = point;
        } else {
            drawLineBetween(selectedPoint, point);
            selectedPoint = null;
            isPlayerOneTurn = !isPlayerOneTurn;
            repaint();

            if (areAllDotsConnected()) {
                declareWinner();
            }
        }
    }

    public void drawLineBetween(org.components.Point p1, org.components.Point p2) {
        Color color = isPlayerOneTurn ? Color.BLUE : Color.RED;
        Line line = new Line(p1, p2, color);
        lines.add(line);
        (isPlayerOneTurn ? player1Lines : player2Lines).add(line);
    }

    /**
     * Saves the current status of the game
     *
     * @param file is the file where the game is saved
     */
    public void saveGameState(File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file));
             PrintWriter writer = new PrintWriter(bw)) {

            writer.println(isPlayerOneTurn);
            writer.println(points.size());
            for (org.components.Point p : points)
                writer.printf("%d,%d,%d%n", p.getId(), p.getX(), p.getY());

            saveLines(writer, lines);
            saveLines(writer, player1Lines);
            saveLines(writer, player2Lines);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Loads the last saved game
     *
     * @param file is the file where the game is loaded from
     */

    public void loadGameState(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file));
             Scanner scanner = new Scanner(br)) {

            isPlayerOneTurn = Boolean.parseBoolean(scanner.nextLine());

            points.clear();
            int numPoints = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < numPoints; i++) {
                String[] parts = scanner.nextLine().split(",");
                points.add(new org.components.Point(Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2])));
            }

            lines.clear();
            lines.addAll(readLines(scanner, Integer.parseInt(scanner.nextLine())));

            player1Lines.clear();
            player1Lines.addAll(readLines(scanner, Integer.parseInt(scanner.nextLine())));

            player2Lines.clear();
            player2Lines.addAll(readLines(scanner, Integer.parseInt(scanner.nextLine())));

            repaint();

        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error loading game state: " + e.getMessage());
            System.err.println(e.getMessage());
        }
    }

    public void saveLines(PrintWriter writer, List<Line> lineList) {
        writer.println(lineList.size());
        for (Line l : lineList) {
            writer.printf("%d,%d,%s%n", l.getStart().getId(), l.getEnd().getId(), l.getColorName());
        }
    }

    public List<Line> readLines(Scanner scanner, int count) {
        List<Line> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String[] parts = scanner.nextLine().split(",");
            org.components.Point start = getPointById(Integer.parseInt(parts[0]));
            org.components.Point end = getPointById(Integer.parseInt(parts[1]));
            Color color = stringToColor(parts[2]);
            result.add(new Line(start, end, color));
        }
        return result;
    }

    public Color stringToColor(String color) {
        return switch (color.toUpperCase()) {
            case "BLUE" -> Color.BLUE;
            case "RED" -> Color.RED;
            default -> Color.BLACK;
        };
    }

    public org.components.Point getPointById(int id) {
        return points.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }


    public void exportCanvas(File file) {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        paint(g2d);
        g2d.dispose();

        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            System.err.println("Error writing canvas: " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Line line : lines) {
            g.setColor(line.getColor());
            g.drawLine(line.getStart().getX(), line.getStart().getY(),
                    line.getEnd().getX(), line.getEnd().getY());
        }

        g.setColor(Color.PINK);
        for (org.components.Point point : points)
            g.fillOval(point.getX() - 5, point.getY() - 5, 15, 15);
    }


    public boolean areAllDotsConnected() {
        var graph = new DefaultUndirectedGraph<Point, DefaultEdge>(DefaultEdge.class);

        points.forEach(graph::addVertex);
        lines.forEach(line -> graph.addEdge(line.getStart(), line.getEnd()));

        return new ConnectivityInspector<>(graph).isConnected();
    }

    public void declareWinner() {
        double score1 = calculateScore(player1Lines);
        double score2 = calculateScore(player2Lines);

        String message = switch (Double.compare(score1, score2)) {
            case -1 -> String.format("Player 1 - BLUE wins!\nScore: %.2f\nPlayer 2 - RED: %.2f", score1, score2);
            case 1 -> String.format("Player 2 - RED wins!\nScore: %.2f\nPlayer 1 - BLUE: %.2f", score2, score1);
            default -> String.format("It's a tie!\nBoth players scored: %.2f", score1);
        };

        JOptionPane.showMessageDialog(this, message, "Game Result", JOptionPane.INFORMATION_MESSAGE);
    }


    private double calculateScore(List<Line> lines) {
        return lines.stream().mapToDouble(Line::getLength).sum();
    }
}
