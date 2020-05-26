package com.opengg.simonorigins;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GameSound {
    public static Map<String, Clip> SOUND_MAP;

    static {
        try {
            var die = AudioSystem.getClip();
            die.open(AudioSystem.getAudioInputStream(new File("resource/sound/died.wav")));
            SOUND_MAP = Map.ofEntries(
                    Map.entry("Die", die)
            );
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
