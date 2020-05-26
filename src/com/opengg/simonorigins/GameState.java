package com.opengg.simonorigins;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.opengg.simonorigins.world.*;

import javax.imageio.ImageIO;

public class GameState extends State{
    public Map map;
    public java.util.List<Entity> entities;
    public java.util.List<Entity> newEntities;

    public int camWidth = 12;
    public int camHeight = 12;

    Player player;

    public GameState(){
        newEntities = new ArrayList<>();
        entities = new ArrayList<>();

        Map.TileSet tileSet = new Map.TileSet();
        tileSet.tileW = 50; tileSet.tileH = 50;
        tileSet.colTile = new Color[]{Color.BLACK,Color.RED,Color.BLACK};
        var contents = MapGenerator.generateMap(12);
        this.map = contents.map();
        this.map.tileSet = tileSet;

        this.entities = contents.entities();

        try {
            tileSet.tileset[1] = ImageIO.read(new File("resource/texture/floor.png"));
            tileSet.tileset[0] = ImageIO.read(new File("resource/texture/wall.png"));
            tileSet.tileset[2] = ImageIO.read(new File("resource/texture/floor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        var firstClear = new Vec2(10000, 10000);

        for(int i = 0; i < map.map.length; i++){
            for(int j = 0; j < map.map[i].length; j++){
                if(map.map[i][j] != 0){
                    var pos = new Vec2(i, j);
                    if(pos.length() < firstClear.length()) firstClear = pos;
                }
            }
        }


        entities.add(0, new Player(firstClear));
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
        int tileW = camWidth;
        int tileH = camHeight;

        float pX = player.position.x();
        float pY = player.position.y();

        if(player.position.x() < (camWidth)/2.0f){
            tileXIndex = 0;
        }else if(player.position.x() >= (map.map.length -  camWidth/2.0f)){
            tileXIndex = map.map.length -  camWidth;
            pX = camWidth -( map.map.length-player.position.x());
        }else{
            tileXIndex = tileXIndex-camWidth/2.0f;
            pX = (camWidth)/2.0f;
            mapOffX = (int)(player.position.x()*map.tileSet.tileW %map.tileSet.tileW);
            tileW++;
        }
        if(player.position.y() < (camHeight)/2.0f){
            tileYIndex = 0;
        }else if(player.position.y() >= (map.map[0].length - camHeight/2.0f)){
            tileYIndex = map.map[0].length - camHeight;
            pY = camHeight-( map.map[0].length-player.position.y());
        }else{
            tileYIndex = tileYIndex-camHeight/2.0f;
            pY = (camHeight)/2.0f;
            mapOffY = (int)(player.position.y()*map.tileSet.tileH %map.tileSet.tileH);
            tileH++;
        }


        map.draw(g,(int)tileXIndex,(int)tileYIndex,tileW,tileH,-mapOffX,-mapOffY);
        g.drawImage(player.sprite.image(),(int)(pX*map.tileSet.tileW),(int)(pY*map.tileSet.tileH),map.tileSet.tileW,map.tileSet.tileH,null);
        for(int i=1;i<entities.size();i++){
            entities.get(i).render(g,tileXIndex,tileYIndex);
        }
    }

    public void update(float delta){
        for(var e : entities){
            e.update(delta);
        }

        entities.removeIf(e -> e.dead);
        entities.addAll(newEntities);
        newEntities = new ArrayList<>();
    }
}
