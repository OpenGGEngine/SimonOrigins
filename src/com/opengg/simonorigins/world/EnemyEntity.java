package com.opengg.simonorigins.world;

import com.opengg.simonorigins.GameState;
import com.opengg.simonorigins.Main;
import com.opengg.simonorigins.Sprite;
import com.opengg.simonorigins.Vec2;

import java.awt.*;
import java.util.Map;

public class EnemyEntity extends Entity{
    Factory.EntityDescriptor entityData;
    State currentState = State.IDLE;

    float attackTimer = 0;
    float deathTimer = 0;
    boolean dead2 = false;
    boolean cooldown = false;

    public EnemyEntity(Factory.EntityDescriptor descriptor) {
        sprite = Sprite.SPRITE_MAP.get(descriptor.sprite);
        this.entityData = descriptor;
        this.maxHealth = entityData.health;
        this.health = this.maxHealth;
        this.width = descriptor.width;
        this.renderWidth = descriptor.width;
        this.box = new BoundingBox(new Vec2(0.25f,0.25f), new Vec2(0.75f,0.75f), new Vec2(0,0), this);
    }

    @Override
    public void render(Graphics g, float camX, float camY) {
        if(dead2){
            int posX = (int)((position.x() - camX - renderWidth/2) * GameState.tileWidth);
            int posY = (int) ((position.y() - camY - renderWidth/2) * GameState.tileWidth);
            g.drawImage(Sprite.SPRITE_MAP.get("Explode").image(),posX, posY, posX+(int) (1.5*GameState.tileWidth * renderWidth), posY+(int) (1.5*GameState.tileWidth * renderWidth),(64*(int)deathTimer),0,(64*((int)deathTimer+1)),64,null);
        }else {
            super.render(g, camX, camY);
        }
    }

    @Override
    public void update(float delta){
        super.update(delta);
        if(deathTimer > 16.1f){
            dead = true;
            return;
        }
        if(dead2){
            deathTimer+=delta*10;
            return;
        }
        if(cooldown){
            attackTimer += delta;
            if(attackTimer > this.entityData.attack.frequency){
                attackTimer = 0;
                cooldown = false;
            }
        }

        if(Main.state.entities.get(0).position.sub(this.position).length() < this.entityData.attack.range && !cooldown){
            doRanged();
            cooldown = true;
        }

        updateState();
        switch (currentState){
            case ATTACKING -> velocity = new Vec2(0,0);
            case RUNNING -> velocity = Main.state.entities.get(0).position.sub(this.position).normalize().mult(this.entityData.movement.velocity);
            case IDLE -> velocity = velocity.add(new Vec2(((float)Math.random() - 0.5f), ((float)Math.random() - 0.5f))).normalize().mult(0.3f);
        }
    }

    void doRanged(){
        var angles = this.entityData.attack.pattern.getOutputAngles();
        for(var angle : angles){
            float realAngle = (float) Math.toRadians(angle);
            var enemyDir = Main.state.entities.get(0).position.sub(this.position).normalize();
            var real = realAngle + Math.atan2(enemyDir.y(), enemyDir.x());
            var realOutputDir = new Vec2((float)Math.cos(real), (float)Math.sin(real));
            var proj = new Projectile(this.entityData.attack.range/4f, this.entityData.attack.damage, false);
            proj.position = this.position.add(realOutputDir.mult(0.8f));
            proj.velocity = realOutputDir.mult(4f);
            Main.state.newEntities.add(proj);
        }
    }

    @Override
    public void onCollision(Entity other) {
        if(other instanceof Player player){
            if(this.entityData.attack.melee && !cooldown){
                player.damage(this.entityData.attack.damage);
                cooldown = true;
            }
        }
    }

    public void updateState(){
        switch (currentState){
            case ATTACKING -> {
                if(Main.state.entities.get(0).position.sub(this.position).length() > entityData.attack.range){
                    currentState = State.RUNNING;
                }
            }
            case RUNNING -> {
                if(Main.state.entities.get(0).position.sub(this.position).length() < entityData.attack.range){
                    currentState = State.ATTACKING;
                }
            }
            case IDLE -> {
                if(Main.state.entities.get(0).position.sub(this.position).length() < entityData.movement.farDist || health != maxHealth){
                    currentState = State.RUNNING;
                }
            }
        }
    }

    @Override
    public void kill(){
        if(Math.random() < 0.1f){
            var weapon = new GroundWeapon(this.entityData.attack);
            weapon.position = this.position;
            Main.state.newEntities.add(weapon);
            dead2 = true;
        }
        if(this.entityData.sprite.equals("MasterMole")){
            Main.setState(new GameState(2));
        }
    }

    public static class Factory{
        public record EntityDescriptor(String sprite,
                                       float width,
                                       float health,
                                       Weapon attack,
                                       MoveType movement){}

        private static final Map<String, EntityDescriptor> entityDescriptorMap = Map.ofEntries(
                Map.entry("Normal", new EntityDescriptor(
                        "Infantry", 0.8f, 5, Weapon.SMG, MoveType.APPROACH
                )),
                Map.entry("Bomber", new EntityDescriptor(
                        "Bomb", 0.8f, 2, Weapon.BOMB, MoveType.JIHADI_JOHN
                )),
                Map.entry("Shotgun", new EntityDescriptor(
                        "Cavalry", 0.8f, 8, Weapon.SHOTGUN, MoveType.APPROACH
                )),
                Map.entry("MasterMole", new EntityDescriptor(
                        "MasterMole", 1.2f, 120, Weapon.AUTOSHOTGUN, MoveType.BOSS
                )),
                Map.entry("Emak", new EntityDescriptor(
                        "Emak", 1.5f, 200, Weapon.AUTOSHOTGUN, MoveType.BOSS
                ))
        );

        public static EnemyEntity generateFromName(String name){
            return new EnemyEntity(entityDescriptorMap.get(name));
        }
    }

    enum MoveType{
        JIHADI_JOHN(7, 0, 10f),
        RUN_INTO(8,0, 5f),
        APPROACH(10,1, 5f),
        BOSS(10,1, 1.5f);


        float farDist;
        float nearDist;
        float velocity;

        MoveType(float farDist, float nearDist, float velocity) {
            this.farDist = farDist;
            this.nearDist = nearDist;
            this.velocity = velocity;
        }
    }


    enum State{
        ATTACKING,
        RUNNING,
        IDLE
    }
}
