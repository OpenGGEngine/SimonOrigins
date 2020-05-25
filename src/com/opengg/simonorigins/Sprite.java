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
                        Map.entry("Simon", new Sprite("resource/texture/simon.jpg", 0.5f, 0.5f))
                );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
