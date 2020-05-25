package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Vec2;

public class Player extends Entity{
    public Player(Vec2 p){
        position = p;
    }

    @Override
    public void onCollision(Entity other) {

    }
}
