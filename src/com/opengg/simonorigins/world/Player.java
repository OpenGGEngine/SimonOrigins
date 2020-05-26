package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Sprite;
import com.opengg.simonorigins.Vec2;

public class Player extends Entity{
    public Player(Vec2 p){
        position = p;
        sprite= Sprite.SPRITE_MAP.get("Simon");
    }

    @Override
    public void onCollision(Entity other) {

    }
}
