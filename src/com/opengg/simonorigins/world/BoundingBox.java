package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Vec2;

public class BoundingBox implements Cloneable{
    boolean test = false;
    Entity parent;

    Vec2 center = new Vec2(0,0);
    Vec2 botleftf;
    Vec2 botleft;

    Vec2 toprightf;
    Vec2 topright;

    public BoundingBox(Vec2 botleftf, Vec2 toprightf, Vec2 center, Entity parent) {
        this(botleftf, toprightf, center, parent,  true);
    }

    public BoundingBox(Vec2 botleftf, Vec2 toprightf, Vec2 center, Entity parent, boolean test) {
        this.botleftf = botleftf;
        this.toprightf = toprightf;
        this.center = center;
        this.parent = parent;
        this.test = test;
        recreate();
    }

    public void recreate(){
        botleft = center.add(botleftf);
        topright = center.add(toprightf);
    }

    public boolean collide(BoundingBox other){
        return !((this.botleft.x() > other.topright.x() || this.topright.x() < other.botleft.x() ||
                this.topright.y() < other.botleft.y() || this.botleft.y() > other.topright.y())) ;
    }

    @Override
    public BoundingBox clone(){
        return new BoundingBox(botleftf, toprightf, center, parent, test);
    }
}