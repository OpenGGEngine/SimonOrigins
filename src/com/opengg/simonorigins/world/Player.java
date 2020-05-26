package com.opengg.simonorigins.world;

import com.opengg.simonorigins.*;

import java.awt.*;

public class Player extends Entity{
    Weapon current;
    public boolean shooting;
    float attackTimer = 0;
    boolean cooldown;

    public Player(Vec2 p){
        maxHealth = 100;
        health = 100;
        position = p;
        current = Weapon.SHOTGUN;
    }

    void useWeapon(){
        var angles = current.pattern.getOutputAngles();
        for(var angle : angles){
            float realAngle = (float) Math.toRadians(angle);
            int x = MouseInfo.getPointerInfo().getLocation().x-Main.state.panel.getX();
            int y = MouseInfo.getPointerInfo().getLocation().y-Main.state.panel.getY()-10;

            var shootDir = new Vec2(x-300,y-300).normalize();
            var real = realAngle + Math.atan2(shootDir.y(), shootDir.x());
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
            if(attackTimer > current.frequency){
                attackTimer = 0;
                cooldown = false;
            }
        }

        if(shooting && !cooldown){
            useWeapon();
            cooldown = true;
        }
        sprite= Sprite.SPRITE_MAP.get("Simon");
    }

    @Override
    public void onCollision(Entity other) {

    }

    @Override
    public void kill(){
        if(dead) return;
        super.kill();
        GameSound.SOUND_MAP.get("Die").start();
    }
}
