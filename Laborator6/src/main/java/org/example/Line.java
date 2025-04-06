package org.example;

import java.awt.*;
import java.util.Map;

public class Line {
    private final Point start;
    private final Point end;
    private final String color;

    public final Map<Color, String> colorNameMap = Map.of(
            Color.BLUE, "BLUE",
            Color.RED, "RED",
            Color.BLACK, "BLACK"
    );

    public final Map<String, Color> stringColorMap = Map.of(
            "BLUE", Color.BLUE,
            "RED", Color.RED,
            "BLACK", Color.BLACK
    );


    public Line(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = colorToString(color);
    }

    public Point getStart() {
        return start;
    }


    public Point getEnd() {
        return end;
    }


    public Color getColor() {
        return stringToColor(color);
    }


    public String getColorName() {
        return color;
    }

    public String colorToString(Color color) {
        return colorNameMap.getOrDefault(color, "BLACK");
    }

    public Color stringToColor(String name) {
        return stringColorMap.getOrDefault(name.toUpperCase(), Color.BLACK);
    }

    public double getLength() {
        return Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));
    }
}
