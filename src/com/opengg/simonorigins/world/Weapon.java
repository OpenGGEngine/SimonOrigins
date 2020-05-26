package com.opengg.simonorigins.world;

import java.util.stream.IntStream;

public enum Weapon {
    NORMAL_MELEE(5, 5),
    NORMAL_RANGED(3, 0.5f, 3f, RangedAttackPattern.LINEAR),
    SMG(2, 0.1f, 3f, RangedAttackPattern.LINEAR),
    SHOTGUN(1, 0.5f, 2f, RangedAttackPattern.SMALL_ARC),
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

    Weapon(float damage, float frequency, float range, RangedAttackPattern attackPattern) {
        this.damage = damage;
        this.frequency = frequency;
        this.pattern = attackPattern;
        this.range = range;
        this.melee = false;
    }

    enum RangedAttackPattern {
        LINEAR,
        SPHERE(20, 360),
        DENSE_SPHERE(5, 360),
        SMALL_ARC(2, 10);

        float angleBetween;
        float arc;

        RangedAttackPattern() {

        }

        RangedAttackPattern(float angleBetween, float arc) {
            this.angleBetween = angleBetween;
            this.arc = arc;
        }

        public int[] getOutputAngles() {
            if (angleBetween == 0) return new int[]{0};
            return IntStream.range((int) (-arc / (angleBetween)), (int) (arc / (angleBetween)))
                    .map(i -> (int) (i * angleBetween))
                    .toArray();
        }

    }
}
