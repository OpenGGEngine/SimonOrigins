package com.opengg.simonorigins.world;

import java.util.Map;

public class EnemyEntity extends Entity{
    Factory.EntityDescriptor entityData;
    String name;
    State currentState;

    public EnemyEntity(Factory.EntityDescriptor descriptor) {
        this.entityData = descriptor;
    }

    @Override
    public void update(float delta){
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
        public record EntityDescriptor(float health, AttackType attack){}

        private final Map<String, EntityDescriptor> entityDescriptorMap = Map.ofEntries(
                Map.entry("basic", new EntityDescriptor(
                        5, AttackType.NORMAL
                ))
        );

        public EnemyEntity generateFromName(String name){
            return new EnemyEntity(entityDescriptorMap.get(name));
        }
    }

    enum AttackType{
        NORMAL(5);

        float frequency;

        AttackType(float frequency){
            this.frequency = frequency;
        }
    }

    enum State{
        ATTACKING,
        RUNNING,
        IDLE
    }
}
