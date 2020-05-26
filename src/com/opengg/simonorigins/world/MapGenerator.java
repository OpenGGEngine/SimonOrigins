package com.opengg.simonorigins.world;

import com.opengg.simonorigins.AStar;
import com.opengg.simonorigins.Node;
import com.opengg.simonorigins.Vec2;

import java.util.*;
import java.util.stream.Collectors;

public class MapGenerator {
    public static MapContents generateMap(int rooms, int level){
        var initialList = new ArrayList<MapNode>();
        float currentX = 0;

        for(int i = 0; i < rooms; i++){
            currentX += 15 + new Random().nextInt(10);
            if(i == rooms-1) currentX += 20;
            initialList.add(new MapNode((int) currentX, new Random().nextInt(10), i));
        }

        initialList.sort(Comparator.comparingInt(node -> node.x));

        var roomContents = new ArrayList<List<MapNode>>();

        for(int room = 0; room < rooms; room++){
            float roomUnity = (float) (0.001 + Math.random() * 0.02);
            int count = 0;
            var roomNodes = new ArrayList<MapNode>();
            roomNodes.add(initialList.get(room));
            var bossOffset = (room == rooms - 1) ? 100 : 0;
            nodeLoop: while(count < 80 + bossOffset + new Random().nextInt(80)){
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
                .mapToInt(node -> node.x).max().getAsInt() + 4;
        int minX = roomContents.stream()
                .flatMap(Collection::stream)
                .mapToInt(node -> node.x).min().getAsInt() - 4;

        int maxY = roomContents.stream()
                .flatMap(Collection::stream)
                .mapToInt(node -> node.y).max().getAsInt() + 5;
        int minY = roomContents.stream()
                .flatMap(Collection::stream)
                .mapToInt(node -> node.y).min().getAsInt() - 4;

        int[][] finalMap = new int[maxX - minX + 1][maxY - minY + 1];

        roomContents.stream()
                .flatMap(Collection::stream)
                .forEach(node -> finalMap[node.x - minX][node.y - minY] = 1);

        roomContents.stream()
                .flatMap(Collection::stream)
                .filter(r -> Math.random() < 0.02)
                .forEach(node -> finalMap[node.x - minX][node.y - minY] = 3);

        for(int i = 0; i < initialList.size() - 1; i++){
            var node = initialList.get(i);
            var next = initialList.get(i + 1);
            var star = new AStar(maxX - minX + 1, maxY - minY + 1,
                    new Node(node.x - minX, node.y - minY),
                    new Node(next.x - minX, next.y - minY));
            star.findPath().stream().forEach(nnode -> {
                finalMap[nnode.getRow()][nnode.getCol()] = 2;
                finalMap[nnode.getRow()][nnode.getCol() + 1] = 2;
            });
        }

        var enemies = new ArrayList<Entity>();
        for(int room = 1; room < rooms; room++){
            int enemyCount = (room) + (level * 4);
            if(room == rooms - 1){
                if(level == 1){
                    var enemy = EnemyEntity.Factory.generateFromName("MasterMole");
                    enemy.position = new Vec2(initialList.get(room).x - minX, initialList.get(room).y - minY);
                    enemies.add(enemy);
                }else if(level == 2){
                    var enemy = EnemyEntity.Factory.generateFromName("Emak");
                    enemy.position = new Vec2(initialList.get(room).x - minX, initialList.get(room).y - minY);
                    enemies.add(enemy);

                    var enemy2 = EnemyEntity.Factory.generateFromName("MasterMole");
                    enemy2.position = new Vec2(initialList.get(room).x - minX + 2, initialList.get(room).y - minY);
                    enemies.add(enemy2);
                }
                continue;
            }

            for(int i = 0; i < enemyCount; i++){
                var node = roomContents.get(room).get(new Random().nextInt(roomContents.get(room).size()));
                int nextEnemy = new Random().nextInt(room + (level * 4));
                EnemyEntity enemy;
                if(nextEnemy < 6){
                    enemy = EnemyEntity.Factory.generateFromName("Normal");
                }else if(nextEnemy < 10){
                    enemy = EnemyEntity.Factory.generateFromName("Shotgun");
                }else{
                    enemy = EnemyEntity.Factory.generateFromName("Bomber");
                }
                enemy.position = new Vec2(node.x - minX, node.y - minY);
                enemies.add(enemy);
            }

        }

        return new MapContents(new Map(finalMap), enemies);
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

    public static record MapContents(Map map, List<Entity> entities){

    }
}
