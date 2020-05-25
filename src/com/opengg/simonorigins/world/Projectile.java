package com.opengg.simonorigins.world;

public class Projectile extends Entity{
    public float lifeLength;
    public float life;
    public float damage;
    public boolean friendly;

    @Override
    public void update(float delta){
        super.update(delta);
        life += delta;
        if(lifeLength <= life){
            kill();
        }
    }

    @Override
    public void onCollision(Entity other) {
        if(!friendly && other instanceof Player player){
            player.damage(damage);
        }else if(friendly && other instanceof EnemyEntity enemyEntity){
            enemyEntity.damage(damage);
        }
    }
}
