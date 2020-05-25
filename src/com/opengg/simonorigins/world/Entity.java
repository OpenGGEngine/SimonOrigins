package com.opengg.simonorigins.world;

import com.opengg.simonorigins.CollisionManager;
import com.opengg.simonorigins.Vec2;

public abstract class Entity {
    public Vec2 position;
    public Vec2 velocity;

    float maxHealth = 100;
    float health;
    String spriteName;
    public BoundingBox box;

    public void update(float delta){
        var old = position;
        position = position.add(velocity.mult(delta));
        box = new BoundingBox(new Vec2(0.25f,0.25f), new Vec2(0.75f,0.75f), position, this);
        box.recreate();
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

    }
}
