package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Pos;

import java.awt.*;

public class Entity {
    public Pos position;
    float health;
    String spriteName;

    public void render(Graphics g,float camX,float camY){
        g.setColor(Color.YELLOW);
        g.fillRect((int)((position.x()-camX)*50),(int)((position.y()-camY)*50),50,50);
    }
    public void update(float delta){

    }
}
