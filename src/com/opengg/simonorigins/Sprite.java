package com.opengg.simonorigins;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public record Sprite (Image image, float width, float height){

    public Sprite(String path, float width, float height) throws IOException {
        this(ImageIO.read(new File(path)), width, height);
    }

    public static Map<String, Sprite> SPRITE_MAP;

    static {
        try {
            SPRITE_MAP = Map.ofEntries(
                        Map.entry("Simon", new Sprite("resource/texture/simonsprite.png", 0.5f, 0.5f))
                        ,Map.entry("GreenBar", new Sprite("resource/texture/GreenBar.png", 0.5f, 0.1f))
                        ,Map.entry("EmptyBar", new Sprite("resource/texture/EmptyBar.png", 0.5f, 0.1f))
                        ,Map.entry("Infantry", new Sprite("resource/texture/infantry.png", 0.5f, 0.1f))
                        ,Map.entry("Bomb", new Sprite("resource/texture/bomb.png", 0.5f, 0.1f))
                        ,Map.entry("Cavalry", new Sprite("resource/texture/Cavalry.png", 0.5f, 0.1f))
                        ,Map.entry("MasterMole", new Sprite("resource/texture/mole.png", 0.5f, 0.1f))
                        ,Map.entry("Dead", new Sprite("resource/texture/died.jpg", 0.5f, 0.5f))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
