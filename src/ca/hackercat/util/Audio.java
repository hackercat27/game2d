package ca.hackercat.util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Audio {

    Clip clip;
    URL[] soundURL = new URL[10];
    int[] loopStart = new int[10];
    int[] loopEnd = new int[10];

    public Audio() {
        getSoundFiles();
    }

    public void getSoundFiles() {
        soundURL[0] = getClass().getResource("/sounds/music/overworld.wav");
        loopStart[0] = 475824;
        loopEnd[0] = -1;
    }



    public void setFile(int soundID) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[soundID]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1002);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        }
    }

    public void play(boolean loop) {
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        clip.start();
    }

    public void halt() {
        clip.stop();
        clip.close();
    }
}

