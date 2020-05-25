package com.opengg.simonorigins;

import java.awt.*;

public class Map {

    public static class TileSet{
        public Image[] tilset = new Image[3];
        public int tileW;
        public int tileH;
        public Color[] colTile = new Color[3];
    }

    public TileSet t;
    public int[][] map;

    public Map(int width,int height){
        map = new int[height][width];
    }

    public void draw(Graphics g,double xCam,double yCam,int camWidth,int camHeight){

        for(int x = (int)xCam;x<camWidth;x++){
            for(int y = (int)yCam;y<camHeight;y++){
                g.setColor(t.colTile[map[y][x]]);
                g.fillRect((int)((x-xCam)*t.tileW),(int)((y-yCam)*t.tileH),t.tileW,t.tileH);
            }
        }
    }
}
