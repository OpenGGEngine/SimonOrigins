package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Vec2;

public class Projectile extends Entity{
    public float lifeLength;
    public float life;
    public float damage;
    public boolean friendly;

    public Projectile(float lifeLength, float damage, boolean friendly) {
        this.lifeLength = lifeLength;
        this.damage = damage;
        this.friendly = friendly;
        width = 0.1f;
        box = new BoundingBox(new Vec2(-width,-width), new Vec2(width,width), new Vec2(0,0), this);
    }

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
        kill();
    }
}
