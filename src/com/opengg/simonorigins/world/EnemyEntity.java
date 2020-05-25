package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Vec2;

import java.util.Map;
import java.util.stream.IntStream;

public class EnemyEntity extends Entity{
    Factory.EntityDescriptor entityData;
    String name;
    State currentState = State.IDLE;

    float attackTimer = 0;
    boolean cooldown = false;

    public EnemyEntity(Factory.EntityDescriptor descriptor) {
        this.entityData = descriptor;
        this.maxHealth = entityData.health;
        this.box = new BoundingBox(new Vec2(0.25f,0.25f), new Vec2(0.75f,0.75f), new Vec2(0,0), this);
    }

    @Override
    public void update(float delta){
        super.update(delta);

        if(cooldown){
            attackTimer += delta;
            if(attackTimer > 1.0f/this.entityData.attack.frequency){
                attackTimer = 0;
                cooldown = false;
            }
        }

        updateState();
        switch (currentState){
            case ATTACKING -> {

            }
            case RUNNING -> {

            }
            case IDLE -> {

            }
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

            }
            case RUNNING -> {

            }
            case IDLE -> {

            }
        }
    }

    public static class Factory{
        public record EntityDescriptor(String sprite, float health, AttackType attack){}

        private static final Map<String, EntityDescriptor> entityDescriptorMap = Map.ofEntries(
                Map.entry("basic", new EntityDescriptor(
                        "bomb.png", 5, AttackType.NORMAL_RANGED
                ))
        );

        public static EnemyEntity generateFromName(String name){
            return new EnemyEntity(entityDescriptorMap.get(name));
        }
    }

    enum AttackType{
        NORMAL_RANGED(5, 5),
        NORMAL_MELEE(3, 5, RangedAttackPattern.LINEAR);


        float frequency;
        float damage;
        boolean melee;
        RangedAttackPattern pattern;

        AttackType(float damage, float frequency){
            this.frequency = frequency;
            this.damage = damage;
        }

        AttackType(float damage, float frequency, RangedAttackPattern attackPattern){
            this.frequency = frequency;
            this.pattern = attackPattern;
        }

        enum RangedAttackPattern{
            LINEAR,
            SPHERE(20,360),
            SMALL_ARC(10,30);

            float angleBetween;
            float arc;

            RangedAttackPattern(){

            }

            RangedAttackPattern(float angleBetween, float arc){
                this.angleBetween = angleBetween;
                this.arc = arc;
            }

            public int[] getOutputAngles(){
                return IntStream.range((int)(-arc/(angleBetween/2)), (int)(arc/(angleBetween/2)))
                        .map(i -> (int) (i * angleBetween))
                        .toArray();
            }
        }
    }

    enum State{
        ATTACKING,
        RUNNING,
        IDLE
    }
}
