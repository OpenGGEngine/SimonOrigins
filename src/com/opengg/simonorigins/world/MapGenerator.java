package com.opengg.simonorigins.world;

import com.opengg.simonorigins.Pos;

import java.util.*;
import java.util.stream.Collectors;

public class MapGenerator {
    public static Map generateMap(float rooms, MapType maps){
        var initialList = new ArrayList<MapNode>();


        for(int i = 0; i < rooms; i++){
            initialList.add(new MapNode(new Random().nextInt(50), new Random().nextInt(50), i));
        }

        var roomContents = new ArrayList<List<MapNode>>();

        for(int room = 0; room < rooms; room++){
            float roomUnity = (float) (0.005 + Math.random() * 0.08);
            int count = 0;
            var roomNodes = new ArrayList<MapNode>();
            roomNodes.add(initialList.get(room));
            nodeLoop: while(count < 80 + new Random().nextInt(40)){
                System.out.println(count);
                var emptyNodes = getEmptyNodes(roomNodes);
                for(var node : emptyNodes){
                    if(node.right == null && Math.random() < roomUnity){
                        node.right = new MapNode(node.x + 1, node.y, room);
                        populateLinks(node.right, roomNodes);
                        roomNodes.add(node.right);
                        count++;
                        continue nodeLoop;
                    }else if(node.left == null && Math.random() < roomUnity){
                        node.left = new MapNode(node.x - 1, node.y, room);
                        populateLinks(node.left, roomNodes);
                        roomNodes.add(node.left);
                        count++;
                        continue nodeLoop;
                    }else if(node.top == null && Math.random() < roomUnity){
                        node.top = new MapNode(node.x, node.y + 1, room);
                        populateLinks(node.top, roomNodes);
                        roomNodes.add(node.top);
                        count++;
                        continue nodeLoop;
                    }else if(node.bottom == null && Math.random() < roomUnity){
                        node.bottom = new MapNode(node.x, node.y - 1, room);
                        populateLinks(node.bottom, roomNodes);
                        roomNodes.add(node.bottom);
                        count++;
                        continue nodeLoop;
                    }
                }
            }
            roomContents.add(roomNodes);
        }

        int maxX = roomContents.stream()
                .flatMap(Collection::stream)
                .mapToInt(node -> node.x).max().getAsInt();
        int minX = roomContents.stream()
                .flatMap(Collection::stream)
                .mapToInt(node -> node.x).min().getAsInt();

        int maxY = roomContents.stream()
                .flatMap(Collection::stream)
                .mapToInt(node -> node.y).max().getAsInt();
        int minY = roomContents.stream()
                .flatMap(Collection::stream)
                .mapToInt(node -> node.y).min().getAsInt();

        System.out.println(maxX + " " + maxY);

        int[][] finalMap = new int[maxX - minX + 1][maxY - minY + 1];

        roomContents.stream()
                .flatMap(Collection::stream)
                .forEach(node -> finalMap[node.x - minX][node.y - minY] = 1);

        return new Map(finalMap);
    }

    static List<MapNode> getEmptyNodes(List<MapNode> nodes){
        return nodes.stream()
                .filter(n -> n.top == null || n.bottom == null || n.left == null || n.right == null)
                .collect(Collectors.toList());
    }

    static void populateLinks(MapNode mainNode, List<MapNode> nodes){
        for(var node : nodes){
            if(node.x == mainNode.x + 1 && node.y == mainNode.y){
                mainNode.right = node;
            }else if(node.x == mainNode.x - 1 && node.y == mainNode.y){
                mainNode.left = node;
            }else if(node.x == mainNode.x && node.y == mainNode.y + 1){
                mainNode.top = node;
            }else if(node.x == mainNode.x && node.y == mainNode.y - 1){
                mainNode.bottom = node;
            }
        }
    }

    public enum MapType{
        SQUARE_ROOM,
        STONE
    }

    private static class MapNode{
        int x, y;
        int roomId;
        MapNode top, bottom, left, right;

         MapNode(int x, int y, int roomId) {
            this.x = x;
            this.y = y;
            this.roomId = roomId;
        }
    }
}
