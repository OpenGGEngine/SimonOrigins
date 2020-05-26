package com.opengg.simonorigins.world;

import com.opengg.simonorigins.CollisionManager;
import com.opengg.simonorigins.Sprite;
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
        this.sprite = Sprite.SPRITE_MAP.get("Bullet");
        width = 0.1f;
        box = new BoundingBox(new Vec2(-width,-width), new Vec2(width,width), new Vec2(0,0), this);
    }

    @Override
    public void update(float delta){
        position = position.add(velocity.mult(delta));
        box = new BoundingBox(new Vec2(-width,-width), new Vec2(width,width), position, this);
        box.recreate();
        if(position.x() <= 0 || position.y() <= 0) return;
        CollisionManager.collide(this);
        life += delta;
        if(lifeLength <= life){
            kill();
        }
    }

    @Override
    public void onCollision(Entity other) {
        if(other instanceof Projectile){

        }else if(other == null){
            kill();
        }else if(!friendly && other instanceof Player player){
            player.damage(damage);
            kill();
        }else if(friendly && other instanceof EnemyEntity enemyEntity){
            enemyEntity.damage(damage);
            kill();
        }
    }
}
