package com.opengg.simonorigins.world;

import java.util.stream.IntStream;

public enum Weapon {
    NORMAL_MELEE(5, 5),
    SMG(1, 0.05f, 5f, RangedAttackPattern.LINEAR),
    AUTOSHOTGUN(2, 0.2f, 4f, RangedAttackPattern.SMALL_ARC),
    SHOTGUN(1, 0.5f, 3f, RangedAttackPattern.SMALL_ARC),
    BOMB(20, 100f, 0.5f, RangedAttackPattern.DENSE_SPHERE);

    float frequency;
    float damage;
    float range;
    boolean melee;
    RangedAttackPattern pattern;

    Weapon(float damage, float frequency) {
        this.frequency = frequency;
        this.damage = damage;
        this.melee = true;
        this.range = 0;
    }

    Weapon(float damage, float frequency,  float range, RangedAttackPattern attackPattern) {
        this.damage = damage;
        this.frequency = frequency;
        this.pattern = attackPattern;
        this.range = range;
        this.melee = false;
    }

    enum RangedAttackPattern {
        LINEAR(4),
        SPHERE(20, 360, 0),
        DENSE_SPHERE(5, 360, 0),
        SMALL_ARC(3, 20, 0),
        BIG_ARC(2, 30, 0);


        float angleBetween;
        float arc;
        float deviation;


        RangedAttackPattern(float deviation) {
            this.deviation = deviation;
        }

        RangedAttackPattern(float angleBetween, float arc, float deviation) {
            this.angleBetween = angleBetween;
            this.arc = arc;
            this.deviation = deviation;
        }

        public int[] getOutputAngles() {
            if (angleBetween == 0) return new int[]{(int) ((Math.random()*2-0/5f)*deviation)};
            return IntStream.range((int) (-arc / (angleBetween)), (int) (arc / (angleBetween)))
                    .map(i -> (int) (i * angleBetween + (Math.random()*2-0/5f)*deviation))
                    .toArray();
        }

    }
}
