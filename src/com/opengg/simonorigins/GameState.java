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

    public GameState(){
        Map.TileSet tileSet = new Map.TileSet();
        tileSet.tileW = 50; tileSet.tileH = 50;
        tileSet.colTile = new Color[]{Color.BLACK,Color.RED,Color.BLACK};
        this.map = MapGenerator.generateMap(5, MapGenerator.MapType.SQUARE_ROOM);
        this.map.tileSet = tileSet;

        entities = new ArrayList<>();
        entities.add(new Player(new Pos(0,0)));
        Entity e = new Entity();
        e.position = new Pos(1,1);
        Entity e1 = new Entity();
        e1.position = new Pos(4,4);
        entities.add(e);
        entities.add(e1);
        player = (Player)entities.get(0);
    }
    @Override
    public void draw(Graphics g) {
        g.clearRect(0,0,600,600);
        if(this.map == null) return;
        //player.position.x()
        float tileXIndex = (player.position.x());
        float tileYIndex = (player.position.y());
        int mapOffX = 0;
        int mapOffY = 0;

        float pX = player.position.x();
        float pY = player.position.y();
        float enX = player.position.x();
        float enY = player.position.y();
        if(player.position.x() < (camWidth)/2.0f){
            tileXIndex = 0;
            enX = 0;
        }else if(player.position.x() >= (map.map.length -  camWidth/2.0f)){
            tileXIndex = map.map.length -  camWidth;
            pX = camWidth -( map.map.length-player.position.x());
        }else{
            tileXIndex = tileXIndex-camWidth/2.0f;
            pX = (camWidth)/2.0f;
            mapOffX = (int)(player.position.x()*map.tileSet.tileW %map.tileSet.tileW);
        }
        if(player.position.y() < (camHeight)/2.0f){
            tileYIndex = 0;
            enY = 0;
        }else if(player.position.y() >= (map.map[0].length - camHeight/2.0f)){
            tileYIndex = map.map[0].length - camHeight;
            pY = camHeight-( map.map[0].length-player.position.y());
        }else{
            tileYIndex = tileYIndex-camHeight/2.0f;
            pY = (camHeight)/2.0f;
            mapOffY = (int)(player.position.y()*map.tileSet.tileH %map.tileSet.tileH);
        }


        map.draw(g,(int)tileXIndex,(int)tileYIndex,camWidth,camHeight,-mapOffX,-mapOffY);
        g.setColor(Color.BLUE);
        g.fillRect((int)(pX*map.tileSet.tileW),(int)(pY*map.tileSet.tileH),map.tileSet.tileW,map.tileSet.tileH);
        for(int i=1;i<entities.size();i++){
            entities.get(i).render(g,tileXIndex,tileYIndex);
        }
    }
}
