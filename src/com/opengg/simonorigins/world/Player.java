package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Vec2;

public class Player extends Entity{
    Weapon current;

    public Player(Vec2 p){
        position = p;
    }

    @Override
    public void update(float delta){
        super.update(delta);
    }

    @Override
    public void onCollision(Entity other) {

    }
}
