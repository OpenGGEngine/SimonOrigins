package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Vec2;

public class GroundWeapon extends Entity{
    Weapon weapon;

    public GroundWeapon(Weapon weapon){
        this.weapon = weapon;
        this.box = new BoundingBox(new Vec2(0.25f,0.25f), new Vec2(0.75f,0.75f), new Vec2(0,0), this);
        this.width = 0.6f;
    }

    @Override
    public void onCollision(Entity other) {
        if(other instanceof Player player ){
            player.current = weapon;
            this.kill();
        }
    }
}
