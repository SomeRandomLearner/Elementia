package logic;

import javax.sound.sampled.*;
import java.net.URL;

public class SoundPlayer {
    public static void playSound(String filePath){
        URL soundURL = SoundPlayer.class.getResource(filePath);

        if (soundURL == null) {
            System.err.println("Could not find sound file: " + filePath);
            return;
        }
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}