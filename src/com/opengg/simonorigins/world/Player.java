package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Main;
import com.opengg.simonorigins.Vec2;

public class Player extends Entity{
    Weapon current;
    public boolean shooting;
    float attackTimer = 0;
    boolean cooldown;

    public Player(Vec2 p){
        maxHealth = 100;
        health = 100;
        position = p;
        current = new Weapon();
    }

    void useWeapon(){
        var angles = new int[]{0};//this.entityData.attack.pattern.getOutputAngles();
        for(var angle : angles){
            var shootDir = velocity.normalize();
            var real = angle + Math.atan2(shootDir.y(), shootDir.x());
            var realOutputDir = new Vec2((float)Math.cos(real), (float)Math.sin(real));
            var proj = new Projectile(1f, this.current.damage, true);
            proj.position = this.position.add(realOutputDir);
            proj.velocity = realOutputDir.mult(10f);
            Main.state.newEntities.add(proj);
        }
    }

    @Override
    public void update(float delta){
        super.update(delta);

        if(cooldown){
            attackTimer += delta;
            if(attackTimer > current.shootFreq){
                attackTimer = 0;
                cooldown = false;
            }
        }

        if(shooting && !cooldown){
            useWeapon();
            cooldown = true;
        }
    }

    @Override
    public void onCollision(Entity other) {

    }
}
