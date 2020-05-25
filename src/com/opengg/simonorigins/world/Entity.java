package com.opengg.simonorigins.world;

import com.opengg.simonorigins.CollisionManager;
import com.opengg.simonorigins.Vec2;

public abstract class Entity {
    public Vec2 position;
    public Vec2 velocity;
    float health;
    String spriteName;
    public BoundingBox box;

    public void update(float delta){
        var old = position;
        position = position.add(velocity);
        box.center = position;
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
    }

    public void kill(){

    }
}
