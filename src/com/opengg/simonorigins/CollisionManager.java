package com.opengg.simonorigins;

import com.opengg.simonorigins.world.BoundingBox;
import com.opengg.simonorigins.world.Entity;

import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class CollisionManager {
    public static boolean collide(Entity object){
        for(Entity t : Main.state.entities){
            if(t == object) continue;
            if (object.box.collide(t.box)){
                object.onCollision(t);
                t.onCollision(object);
                //return true;
            }
        }
        if(Math.round(object.position.x()) >= Main.state.map.map.length || Math.round(object.position.y()) >= Main.state.map.map[0].length) return true;
        return Main.state.map.map[Math.round(object.position.x())][Math.round(object.position.y())] == 0;
    }
}