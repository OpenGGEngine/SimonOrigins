package com.opengg.simonorigins.world;

import com.opengg.simonorigins.*;

import java.awt.*;

public class Player extends Entity{
    Weapon current;
    public boolean shooting;
    float attackTimer = 0;
    public float px,py;
    boolean cooldown;

    public Player(Vec2 p){
        maxHealth = 250;
        health = maxHealth;
        position = p;
        current = Weapon.SHOTGUN;
        width = 0.5f;
        renderWidth = 0.9f;
    }

    void useWeapon(){
        var angles = current.pattern.getOutputAngles();
        for(var angle : angles){
            float realAngle = (float) Math.toRadians(angle);
            int x = MouseInfo.getPointerInfo().getLocation().x-Main.state.panel.getX();
            int y = MouseInfo.getPointerInfo().getLocation().y-Main.state.panel.getY()-10;

            var shootDir = new Vec2(x-GameState.tileWidth*px,y-GameState.tileWidth*py).normalize();
            var real = realAngle + Math.atan2(shootDir.y(), shootDir.x());
            var realOutputDir = new Vec2((float)Math.cos(real), (float)Math.sin(real));
            var proj = new Projectile(this.current.range/10f, this.current.damage, true);
            proj.position = this.position.add(realOutputDir.mult(0.6f));
            if(velocity.length() != 0)
                proj.velocity = realOutputDir.mult(10f).add(this.velocity.mult(Math.max(0, this.velocity.normalize().dot(realOutputDir.normalize()))));
            else
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
        sprite = Sprite.SPRITE_MAP.get("Simon");
    }

    @Override
    public void onCollision(Entity other) {

    }

    @Override
    public void kill(){
        if(dead) return;
        super.kill();
        GameSound.SOUND_MAP.get("Die").start();
        Main.state.dead = true;
    }
}
