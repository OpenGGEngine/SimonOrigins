package com.opengg.simonorigins.world;

import com.opengg.simonorigins.*;

import java.awt.*;

public abstract class Entity {
    public Vec2 position;
    public Vec2 velocity = new Vec2(0,0);

    float width = 0.5f;
    public float maxHealth = 100;

    public float health;
    public Sprite sprite;
    public BoundingBox box;

     public boolean dead = false;

    public void render(Graphics g, float camX, float camY){

        if(sprite != null){
                g.drawImage(sprite.image(),(int) ((position.x() - camX - width/2) * GameState.tileWidth), (int) ((position.y() - camY - width/2) * GameState.tileWidth), (int) (GameState.tileWidth * width), (int) (GameState.tileWidth * width), null);
        }else {
            g.setColor(Color.YELLOW);
            g.fillRect((int) ((position.x() - camX - width) * GameState.tileWidth), (int) ((position.y() - camY - width) * GameState.tileWidth), (int) (GameState.tileWidth * width), (int) (GameState.tileWidth * width));
        }
    }
    public void update(float delta){
        var old = position;
        position = position.add(velocity.mult(delta));
        box = new BoundingBox(new Vec2(-width,-width), new Vec2(width,width), position, this);
        box.recreate();
        if(position.x() <= 0 || position.y() <= 0) return;
        var collided = CollisionManager.collide(this);
        if(collided){
            position = old;
            box.recreate();
        }
    }

    public abstract void onCollision(Entity other);

    public void damage(float damage){
        this.health -= damage;
        if(health < 0) kill();
        if(health > maxHealth) health = maxHealth;
    }

    public void kill(){
        dead = true;
    }
}
