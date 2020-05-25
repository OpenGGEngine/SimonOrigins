package com.opengg.simonorigins;

public record Vec2(float x, float y) {
    public Vec2 add(Vec2 other){
        return new Vec2(x + other.x, y + other.y);
    }

    public Vec2 sub(Vec2 other){
        return new Vec2(x - other.x, y - other.y);
    }

    public Vec2 mult(Vec2 other){
        return new Vec2(x * other.x, y * other.y);
    }

    public Vec2 mult(float other){
        return new Vec2(x * other, y * other);
    }

    public Vec2 div(Vec2 other){
        return new Vec2(x / other.x, y / other.y);
    }

    public Vec2 div(float other){
        return new Vec2(x / other, y / other);
    }

    public float length(){
        return (float) Math.sqrt(x*x + y*y);
    }

    public Vec2 normalize(){
        return div(length());
    }
}
