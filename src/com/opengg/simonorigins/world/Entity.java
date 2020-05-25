package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Pos;

import java.awt.*;

public class Entity {
    public Pos position;
    float health;
    String spriteName;

    public void render(Graphics g,float camX,float camY){
        g.setColor(Color.YELLOW);
        g.fillRect((int)((camX-position.x())*40),(int)((camY - position.y())*40),40,40);
    }
    public void update(float delta){

    }
}
