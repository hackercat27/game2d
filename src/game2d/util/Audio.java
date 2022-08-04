package game2d.util;

import game2d.main.GamePanel;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Audio {

    //sound ids
    public static final int GET_ITEM = 3;
    public static final int UNLOCK_DOOR = 4;


    Clip clip;
    URL[] soundURL = new URL[10];
    int[] loopStart = new int[10];
    int[] loopEnd = new int[10];

    FloatControl floatControl;
    GamePanel gp;

    boolean playing = false;
    boolean paused = false;
    long playback = 0;

    public Audio(GamePanel gp) {
        this.gp = gp;
        getSoundFiles();
    }

    public void getSoundFiles() {
        soundURL[0] = getClass().getResource("/sounds/music/overworld.wav");
        loopStart[0] = 475824;
        loopEnd[0] = -1;
        soundURL[1] = getClass().getResource("/sounds/music/boss.wav");
        loopStart[1] = 142500;
        loopEnd[1] = -1;
        soundURL[2] = getClass().getResource("/sounds/music/song2.wav");
        loopStart[2] = 307197;
        loopEnd[2] = -1;

        soundURL[GET_ITEM] = getClass().getResource("/sounds/sfx/get_item.wav");
        loopStart[GET_ITEM] = 0;
        loopEnd[GET_ITEM] = -1;

        soundURL[UNLOCK_DOOR] = getClass().getResource("/sounds/sfx/unlock_door.wav");
        loopStart[UNLOCK_DOOR] = 0;
        loopEnd[UNLOCK_DOOR] = -1;
    }

    public void setFile(int soundID) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[soundID]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setLoopPoints(loopStart[soundID], loopEnd[soundID]);
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1002);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isPaused() {
        return paused;
    }

    public void resume() {
        paused = false;
        clip.setFramePosition((int) playback);
    }

    public void pause() {
        paused = true;
        playback = clip.getMicrosecondPosition();
    }

    public void play(boolean loop) {
        playing = true;
        paused = false;
        playback = 0;
        floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            floatControl.setValue(gp.vars.musicVol);
        } else {
            floatControl.setValue(gp.vars.sfxVol);
        }
        clip.start();
    }

    public void halt() {
        playing = false;
        paused = false;
        clip.stop();
        clip.close();
    }

    public void update() {

    }
}

