package com.opengg.simonorigins;

import java.awt.*;
import java.util.ArrayList;

import com.opengg.simonorigins.world.*;

public class GameState extends State{
    public Map map;
    public java.util.List<Entity> entities;
    public int camWidth = 10;
    public int camHeight = 10;

    Player player;
    double posX=0; double posY=0;

    public GameState(){
        Map.TileSet tileSet = new Map.TileSet();
        tileSet.tileW = 20; tileSet.tileH = 20;
        tileSet.colTile = new Color[]{Color.BLACK,Color.RED,Color.BLACK};
        this.map = MapGenerator.generateMap(5);
        this.map.tileSet = tileSet;

        entities = new ArrayList<>();
        entities.add(new Player(new Vec2(0,0)));
        player = (Player)entities.get(0);
    }

    @Override
    public void draw(Graphics g) {
        if(this.map == null) return;
        //player.position.x()
        int tileXIndex = (int)(player.position.x()/map.tileSet.tileW);
        int tileYIndex = (int)(player.position.y()/map.tileSet.tileH);
        //float mapOffX = player.position.x()/map.tileSet.tileW;
        //float mapOffY = player.position.y()/map.tileSet.tileH;
        System.out.println(player.position.x()+","+(camWidth * map.tileSet.tileW));
        if(player.position.x() < camWidth * map.tileSet.tileW){
            tileXIndex = 0;
        }
        if(player.position.y() < camHeight * map.tileSet.tileH){
            tileYIndex = 0;
        }
        if(player.position.x() >= (map.map.length -  camWidth) * map.tileSet.tileW){
            tileXIndex = map.map.length -  camWidth;
        }
        if(player.position.y() >= (map.map[0].length - camHeight) * map.tileSet.tileH){
            tileYIndex = map.map[0].length - camHeight;
        }

        System.out.println(tileXIndex+","+tileYIndex);
        g.setColor(Color.BLUE);
        g.fillRect((int)player.position.x(),(int)player.position.y(),20,20);
        map.draw(g,tileXIndex,tileYIndex,camWidth,camHeight,0,0);
    }

    public void update(float delta){
        for(var e : entities){
            e.update(delta);
        }
    }
}
