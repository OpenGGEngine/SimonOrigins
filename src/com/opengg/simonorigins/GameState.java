package com.opengg.simonorigins;

import java.awt.*;

public class GameState extends State{
    public Map map;
    double posX=0; double posY=0;

    public GameState(){
        Map.TileSet tileSet = new Map.TileSet();
        tileSet.tileW = 20; tileSet.tileH = 20;
        tileSet.colTile = new Color[]{Color.BLACK,Color.RED,Color.BLACK};
        this.map = new Map(10,10);
        this.map.map[3][3] = 1;
        this.map.t = tileSet;
    }
    @Override
    public void draw(Graphics g) {
        if(this.map == null) return;
        map.draw(g,posX,posY,10,10);
    }
}
