package org.example;

/**
 * Point Class represents a point with an ID and 2D coordinates
 */
public class Point {
    private int id;
    private int x, y;

    public Point(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * CHecks ig the given coordinates are near the point - helpful when the click is not perffectly pn the dot
     *
     * @param mx
     * @param my
     * @return
     */
    public boolean isNear(int mx, int my) {
        return Math.abs(mx - x) <= 10 && Math.abs(my - y) <= 10;
    }
}


