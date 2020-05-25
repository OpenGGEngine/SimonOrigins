package com.opengg.simonorigins.world;

public class Projectile extends Entity{
    public float lifeLength;
    public float life;
    public float damage;

    @Override
    public void update(float delta){
        life += delta;
        if(lifeLength <= life){
            kill();
        }
    }

    @Override
    public void onCollision(Entity other) {
        if(other instanceof Player player){
            player.damage(damage);
        }
    }
}
