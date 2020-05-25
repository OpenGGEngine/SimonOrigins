package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Pos;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    Factory.EntityDescriptor entityData;
    Pos position;
    String name;

    public Entity(Factory.EntityDescriptor descriptor) {
        this.entityData = descriptor;
    }

    public void update(float delta){

    }

    public static class Factory{
        public record EntityDescriptor(float health, AttackType attack){}

        private Map<String, EntityDescriptor> entityDescriptorMap = Map.ofEntries(
                Map.entry("basic", new EntityDescriptor(
                        5, AttackType.NORMAL
                ))
        );

        public Entity generateFromName(String name){
            return new Entity(entityDescriptorMap.get(name));
        }
    }

    enum AttackType{
        NORMAL(5);

        float frequency;

        AttackType(float frequency){
            this.frequency = frequency;
        }
    }
}
