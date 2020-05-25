package com.opengg.simonorigins;

public record Pos(float x, float y) {
    Pos add(Pos other){
        return new Pos(x + other.x, y + other.y);
    }

    Pos sub(Pos other){
        return new Pos(x - other.x, y - other.y);
    }

    Pos mult(Pos other){
        return new Pos(x * other.x, y * other.y);
    }

    Pos div(Pos other){
        return new Pos(x / other.x, y / other.y);
    }
}
