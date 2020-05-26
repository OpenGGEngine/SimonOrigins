package com.opengg.simonorigins.world;

import com.opengg.simonorigins.CollisionManager;
import com.opengg.simonorigins.Main;
import com.opengg.simonorigins.Vec2;

import java.awt.*;

public abstract class Entity {
    public Vec2 position;
    public Vec2 velocity = new Vec2(0,0);

    float maxHealth = 100;
    float width = 0.3f;

    float health;
    String spriteName;
    public BoundingBox box;

     public boolean dead = false;

    public void render(Graphics g, float camX, float camY){
        g.setColor(Color.YELLOW);
        g.fillRect((int)((position.x()-camX)*50),(int)((position.y()-camY)*50), (int) (50*width*2), (int) (50*width*2));
    }
    public void update(float delta){
        var old = position;
        position = position.add(velocity.mult(delta));
        box = new BoundingBox(new Vec2(-width,-width), new Vec2(width,width), position, this);
        box.recreate();
        if(position.x() <= 0 || position.y() <= 0 || position.x() >= Main.state.map.map.length || position.y() >= Main.state.map.map[0].length) return;
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
