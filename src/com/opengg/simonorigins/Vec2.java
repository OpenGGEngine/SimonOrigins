package com.opengg.simonorigins;

public record Point(float x, float y) {
    public Point add(Point other){
        return new Point(x + other.x, y + other.y);
    }

    public Point sub(Point other){
        return new Point(x - other.x, y - other.y);
    }

    public Point mult(Point other){
        return new Point(x * other.x, y * other.y);
    }

    public Point div(Point other){
        return new Point(x / other.x, y / other.y);
    }
}
