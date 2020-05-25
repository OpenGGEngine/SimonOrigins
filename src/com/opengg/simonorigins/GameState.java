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

        var firstClear = new Vec2(10000, 10000);

        for(int i = 0; i < map.map.length; i++){
            for(int j = 0; j < map.map[i].length; j++){
                if(map.map[i][j] != 0){
                    var pos = new Vec2(i, j);
                    if(pos.length() < firstClear.length()) firstClear = pos;
                }
            }
        }

        entities = new ArrayList<>();
        entities.add(new Player(firstClear));
        player = (Player)entities.get(0);
    }

    @Override
    public void draw(Graphics g) {
        g.clearRect(0,0,600,600);
        if(this.map == null) return;
        //player.position.x()
        int tileXIndex = (int)(player.position.x()/map.tileSet.tileW);
        int tileYIndex = (int)(player.position.y()/map.tileSet.tileH);
        //float mapOffX = player.position.x()/map.tileSet.tileW;
        //float mapOffY = player.position.y()/map.tileSet.tileH;
        float pX = player.position.x();
        float pY = player.position.y();
        if(player.position.x() < (camWidth * map.tileSet.tileW)/2.0f){
            tileXIndex = 0;
        }else if(player.position.x() >= (map.map.length -  camWidth) * map.tileSet.tileW){
            tileXIndex = map.map.length -  camWidth;
        }else{
            pX = (camWidth * map.tileSet.tileW)/2.0f;
        }
        if(player.position.y() < (camHeight * map.tileSet.tileH)/2.0f){
            tileYIndex = 0;
        }else if(player.position.y() >= (map.map[0].length - camHeight) * map.tileSet.tileH){
            tileYIndex = map.map[0].length - camHeight;
        }else{
            pY = (camHeight * map.tileSet.tileH)/2.0f;
        }

        map.draw(g,tileXIndex,tileYIndex,camWidth,camHeight,0,0);
        g.setColor(Color.BLUE);
        g.fillRect((int)pX,(int)pY,20,20);
    }

    public void update(float delta){
        for(var e : entities){
            e.update(delta);
        }
    }
}
