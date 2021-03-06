package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Main;
import com.opengg.simonorigins.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Map {
    public int[][] map;
    public TileSet tileSet;

    public Map(int[][] map) {
        System.out.println("Map Size: " + map.length + ","+map[0].length);
        this.map = map;
    }

    public void draw(Graphics g, int xCam, int yCam, int camWidth, int camHeight, int mapOffX, int mapOffY){
        Main.state.light.clear();
        for(int x =  xCam; x < xCam + camWidth + 2; x++){
            for(int y = yCam; y < yCam + camHeight + 1; y++){
                if(map[x][y] == 3){
                    Main.state.light.add(new Vec2(x,y));
                }
                g.drawImage(tileSet.tileset[map[x][y]], (int)((x - xCam - 0.5f) * tileSet.tileW + mapOffX),
                        (int)((y - yCam - 0.5f) * tileSet.tileH + mapOffY),
                        tileSet.tileW,
                        tileSet.tileH,null);
                //g.setColor(tileSet.colTile[map[x][y]]);
                //g.fillRect(((x-xCam)*tileSet.tileW + mapOffX), ((y-yCam)*tileSet.tileH + mapOffY), tileSet.tileW, tileSet.tileH);
            }
        }
    }

    public void printMap(){
        for (int[] row : map) {
            for (int i : row) {
                System.out.print(i);
            }
            System.out.println();
        }
    }


    public static class TileSet{
        public Image[] tileset = new Image[4];
        public int tileW;
        public int tileH;
        public Color[] colTile = new Color[3];
    }

}
