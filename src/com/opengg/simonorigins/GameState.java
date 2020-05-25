package com.opengg.simonorigins;

import java.awt.*;
import java.util.ArrayList;

import com.opengg.simonorigins.world.*;

public class GameState extends State{
    public Map map;
    public java.util.List<Entity> entities;
    double posX=0; double posY=0;

    public GameState(){
        Map.TileSet tileSet = new Map.TileSet();
        tileSet.tileW = 20; tileSet.tileH = 20;
        tileSet.colTile = new Color[]{Color.BLACK,Color.RED,Color.BLACK};
        this.map = MapGenerator.generateMap(5);
        this.map.tileSet = tileSet;

        entities = new ArrayList<>();
    }
    @Override
    public void draw(Graphics g) {
        if(this.map == null) return;
        map.draw(g,posX,posY,10,10);
    }
}
