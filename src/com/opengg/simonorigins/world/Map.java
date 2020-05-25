package com.opengg.simonorigins.world;

import java.awt.*;

public class Map {
    public int[][] map;
    public TileSet tileSet;

    public Map(int[][] map) {
        this.map = map;
    }

    public void draw(Graphics g,double xCam,double yCam,int camWidth,int camHeight){

        for(int x = (int) xCam; x < camWidth; x++){
            for(int y = (int)yCam; y < camHeight; y++){
                g.setColor(tileSet.colTile[map[y][x]]);
                g.fillRect((int)((x-xCam)*tileSet.tileW), (int)((y-yCam)*tileSet.tileH), tileSet.tileW, tileSet.tileH);
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
