package org.example;

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

    public boolean isNear(int mx, int my) {
        return Math.abs(mx - x) <= 10 && Math.abs(my - y) <= 10;
    }
}


