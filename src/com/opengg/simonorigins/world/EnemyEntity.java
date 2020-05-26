package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Main;
import com.opengg.simonorigins.Vec2;

import java.util.Map;
import java.util.stream.IntStream;

public class EnemyEntity extends Entity{
    Factory.EntityDescriptor entityData;
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
            case IDLE -> velocity = velocity.add(new Vec2(((float)Math.random())*0.2f, ((float)Math.random())*0.2f)).normalize().mult(0.3f);
        }
    }

    void doRanged(){
        var angles = this.entityData.attack.pattern.getOutputAngles();
        for(var angle : angles){
            var enemyDir = Main.state.entities.get(0).position.sub(this.position).normalize();
            var real = angle + Math.atan2(enemyDir.y(), enemyDir.x());
            var realOutputDir = new Vec2((float)Math.cos(real), (float)Math.sin(real));
            var proj = new Projectile(0.5f, this.entityData.attack.damage, false);
            proj.position = this.position.add(realOutputDir);
            proj.velocity = realOutputDir.mult(2f);
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
                if(Main.state.entities.get(0).position.sub(this.position).length() < entityData.movement.farDist){
                    currentState = State.RUNNING;
                }
            }
        }
    }

    public static class Factory{
        public record EntityDescriptor(String sprite,
                                       float health,
                                       AttackType attack,
                                       MoveType movement){}

        private static final Map<String, EntityDescriptor> entityDescriptorMap = Map.ofEntries(
                Map.entry("Normal", new EntityDescriptor(
                        "bomb.png", 10, AttackType.NORMAL_RANGED, MoveType.APPROACH
                )),
                Map.entry("Bomber", new EntityDescriptor(
                        "bomb.png", 5, AttackType.BOMB, MoveType.JIHADI_JOHN
                )),
                Map.entry("Shotgun", new EntityDescriptor(
                        "bomb.png", 12, AttackType.SHOTGUN_RANGED, MoveType.APPROACH
                ))
        );

        public static EnemyEntity generateFromName(String name){
            return new EnemyEntity(entityDescriptorMap.get(name));
        }
    }

    enum MoveType{
        JIHADI_JOHN(4, 0, 10f),
        RUN_INTO(8,0, 5f),
        APPROACH(8,1, 5f);

        float farDist;
        float nearDist;
        float velocity;

        MoveType(float farDist, float nearDist, float velocity) {
            this.farDist = farDist;
            this.nearDist = nearDist;
            this.velocity = velocity;
        }
    }

    enum AttackType{
        NORMAL_MELEE(5, 5),
        NORMAL_RANGED(3, 0.5f, 3f, RangedAttackPattern.LINEAR),
        SHOTGUN_RANGED(3, 0.5f, 2f, RangedAttackPattern.SMALL_ARC),
        BOMB(20, 100f, 0.5f, RangedAttackPattern.DENSE_SPHERE);

        float frequency;
        float damage;
        float range;
        boolean melee;
        RangedAttackPattern pattern;

        AttackType(float damage, float frequency){
            this.frequency = frequency;
            this.damage = damage;
            this.melee = true;
            this.range = 0;
        }

        AttackType(float damage, float frequency, float range, RangedAttackPattern attackPattern){
            this.damage = damage;
            this.frequency = frequency;
            this.pattern = attackPattern;
            this.range = range;
            this.melee = false;
        }

        enum RangedAttackPattern{
            LINEAR,
            SPHERE(20,360),
            DENSE_SPHERE(5,360),
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
                if(angleBetween == 0) return new int[]{0};
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
