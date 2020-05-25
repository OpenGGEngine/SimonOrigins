package com.opengg.simonorigins.world;

import java.awt.*;

public class Map {
    public int[][] map;
    public TileSet tileSet;

    public Map(int[][] map) {
        this.map = map;
    }

    public void draw(Graphics g,int xCam,int yCam,int camWidth,int camHeight,int mapOffX,int mapOffY){
        for(int x =  xCam; x < camWidth; x++){
            for(int y = yCam; y < camHeight; y++){
                g.setColor(tileSet.colTile[map[x][y]]);
                g.fillRect(((x-xCam)*tileSet.tileW + mapOffX), ((y-yCam)*tileSet.tileH + mapOffY), tileSet.tileW, tileSet.tileH);
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
        public Image[] tilset = new Image[3];
        public int tileW;
        public int tileH;
        public Color[] colTile = new Color[3];
    }

}
