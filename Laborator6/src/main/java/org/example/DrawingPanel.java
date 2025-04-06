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

import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.alg.connectivity.ConnectivityInspector;

public class DrawingPanel extends JPanel {
    private final List<Point> points = new ArrayList<>();
    private final List<Line> lines = new ArrayList<>();
    private final List<Line> player1Lines = new ArrayList<>();
    private final List<Line> player2Lines = new ArrayList<>();

    private Point selectedPoint = null;
    private boolean isPlayerOneTurn = true;

    public void createDots(int numberOfDots) {
        points.clear();
        lines.clear();
        player1Lines.clear();
        player2Lines.clear();
        selectedPoint = null;

        Random rand = new Random();
        for (int i = 0; i < numberOfDots; i++) {
            int x = rand.nextInt(getWidth() - 20) + 10;
            int y = rand.nextInt(getHeight() - 20) + 10;
            points.add(new Point(i, x, y));
        }
        repaint();
    }

    public DrawingPanel() {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (Point point : points) {
                    if (point.isNear(e.getX(), e.getY())) {
                        if (selectedPoint == null) {
                            selectedPoint = point;
                        } else {
                            Color color = isPlayerOneTurn ? Color.BLUE : Color.RED;
                            Line line = new Line(selectedPoint, point, color);
                            lines.add(line);
                            (isPlayerOneTurn ? player1Lines : player2Lines).add(line);
                            selectedPoint = null;
                            isPlayerOneTurn = !isPlayerOneTurn;
                            repaint();

                            if (areAllDotsConnected()) {
                                declareWinner();
                            }
                        }
                        break;
                    }
                }
            }
        });
    }



    public void saveGameState(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(isPlayerOneTurn);
            writer.println(points.size());
            for (Point p : points)
                writer.printf("%d,%d,%d%n", p.getId(), p.getX(), p.getY());

            writer.println(lines.size());
            writeLines(writer, lines);

            writer.println(player1Lines.size());
            writeLines(writer, player1Lines);

            writer.println(player2Lines.size());
            writeLines(writer, player2Lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadGameState(File file) {
        try (Scanner scanner = new Scanner(file)) {
            isPlayerOneTurn = Boolean.parseBoolean(scanner.nextLine());

            points.clear();
            int numPoints = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < numPoints; i++) {
                String[] parts = scanner.nextLine().split(",");
                points.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
            }

            lines.clear();
            lines.addAll(readLines(scanner, Integer.parseInt(scanner.nextLine())));

            player1Lines.clear();
            player1Lines.addAll(readLines(scanner, Integer.parseInt(scanner.nextLine())));

            player2Lines.clear();
            player2Lines.addAll(readLines(scanner, Integer.parseInt(scanner.nextLine())));

            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading game state: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exportCanvas(File file) {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        paint(g2d);
        g2d.dispose();

        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeLines(PrintWriter writer, List<Line> lines) {
        for (Line l : lines) {
            writer.printf("%d,%d,%s%n", l.getStart().getId(), l.getEnd().getId(), l.getColorName());
        }
    }


    private List<Line> readLines(Scanner scanner, int count) {
        List<Line> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String[] parts = scanner.nextLine().split(",");
            Point start = getPointById(Integer.parseInt(parts[0]));
            Point end = getPointById(Integer.parseInt(parts[1]));
            Color color = stringToColor(parts[2]);
            result.add(new Line(start, end, color));
        }
        return result;
    }

    private Color stringToColor(String color) {
        switch (color.toUpperCase()) {
            case "BLUE":
                return Color.BLUE;
            case "RED":
                return Color.RED;
            default:
                return Color.BLACK;
        }
    }

    private Point getPointById(int id) {
        for (Point p : points) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.PINK);
        for (Point point : points)
            g.fillOval(point.getX() - 5, point.getY() - 5, 15, 15);

        for (Line line : lines) {
            g.setColor(line.getColor());
            g.drawLine(line.getStart().getX(), line.getStart().getY(), line.getEnd().getX(), line.getEnd().getY());
        }
    }

    public boolean areAllDotsConnected() {
        DefaultUndirectedGraph<Point, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

        points.forEach(graph::addVertex);
        lines.forEach(line -> graph.addEdge(line.getStart(), line.getEnd()));

        return new ConnectivityInspector<>(graph).isConnected();
    }

    public void declareWinner() {
        double score1 = calculateScore(player1Lines);
        double score2 = calculateScore(player2Lines);
        String message;

        if (score1 < score2) {
            message = "Player 1 - BLUE wins!";
        } else if (score2 < score1) {
            message = "Player 2 - RED wins!";
        } else {
            message = "It's a tie!";
        }

        JOptionPane.showMessageDialog(null, message, "Game Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private double calculateScore(List<Line> lines) {
        return lines.stream().mapToDouble(Line::getLength).sum();
    }


}
