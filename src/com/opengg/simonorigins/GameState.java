package com.opengg.simonorigins;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.opengg.simonorigins.world.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameState extends State{
    public Map map;
    public java.util.List<Entity> entities;
    public java.util.List<Entity> newEntities;
    public java.util.List<Vec2> light = new ArrayList<>();

    public int camWidth = 12;
    public int camHeight = 12;

    public Player player;
    public boolean held;
    public JFrame panel;
    public boolean dead;

    float dieAlpha = 0;

    public GameState(int level){
        newEntities = new ArrayList<>();
        entities = new ArrayList<>();
        setNewMap(level);
        player = (Player)entities.get(0);
    }

    private void setNewMap(int level){
        Map.TileSet tileSet = new Map.TileSet();
        tileSet.tileW = 50; tileSet.tileH = 50;
        tileSet.colTile = new Color[]{Color.BLACK,Color.RED,Color.BLACK};
        var contents = MapGenerator.generateMap(12);
        this.map = contents.map();
        this.map.tileSet = tileSet;

        this.entities = contents.entities();

        try {
            switch (level){
                case 1 -> {
                    tileSet.tileset[1] = ImageIO.read(new File("resource/texture/grass.png"));
                    tileSet.tileset[0] = ImageIO.read(new File("resource/texture/wall.png"));
                    tileSet.tileset[2] = ImageIO.read(new File("resource/texture/grass.png"));
                    tileSet.tileset[3] = ImageIO.read(new File("resource/texture/lava.png"));
                }
                case 2 -> {
                    tileSet.tileset[1] = ImageIO.read(new File("resource/texture/floor.png"));
                    tileSet.tileset[0] = ImageIO.read(new File("resource/texture/wall.png"));
                    tileSet.tileset[2] = ImageIO.read(new File("resource/texture/path.png"));
                    tileSet.tileset[3] = ImageIO.read(new File("resource/texture/lava.png"));
                }
            }

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
        g.drawImage(Sprite.SPRITE_MAP.get("EmptyBar").image(),0,0,150,30,null);
        g.drawImage(Sprite.SPRITE_MAP.get("GreenBar").image(),0,0,(int)(150*(player.health/player.maxHealth)),30,null);
        g.drawImage(player.sprite.image(),(int)(pX*map.tileSet.tileW),(int)(pY*map.tileSet.tileH),map.tileSet.tileW,map.tileSet.tileH,null);
        for(int i=1;i<entities.size();i++){
            entities.get(i).render(g,tileXIndex,tileYIndex);
        }

        for(Vec2 d:light){
            g.drawImage(Sprite.SPRITE_MAP.get("Light").image(),(int)(((d.x())-2-tileXIndex)*50),(int)(((d.y())-2-tileYIndex)*50),250,250,null);
        }

        if(dead) {
            dieAlpha += 0.008;
            if (dieAlpha > 1) dieAlpha = 1;
            AlphaComposite alcom = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, dieAlpha);
            ((Graphics2D) g).setComposite(alcom);
            g.drawImage(Sprite.SPRITE_MAP.get("Dead").image(), 0, 0, 600, 600, null);
        }
    }

    public void update(float delta){
        if(dead){
            return;
        }
        player.shooting = held;
        for(var e : entities){
            e.update(delta);
        }

        entities.removeIf(e -> e.dead);
        entities.addAll(newEntities);
        newEntities = new ArrayList<>();
    }
}
