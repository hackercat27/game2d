package game2d.util;

import game2d.main.GamePanel;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Audio {

    //sound ids
    public static final int GET_ITEM_UNIMPORTANT = 3;
    public static final int GET_ITEM = 4;
    public static final int DOOR_UNLOCK = 5;
    public static final int DOOR_OPEN = 6;
    public static final int DOOR_CLOSE = 7;


    Clip clip;
    URL[] soundURL = new URL[10];
    int[] loopStart = new int[soundURL.length];
    int[] loopEnd = new int[soundURL.length];

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

        setup(0, 383997, -1, "music/dungeon");
        setup(1, 307197, -1, "music/song2");

        setup(GET_ITEM, "sfx/get_item");
        setup(GET_ITEM_UNIMPORTANT, "sfx/get_item_unimportant");
        setup(DOOR_UNLOCK, "sfx/door_unlock");
        setup(DOOR_CLOSE, "sfx/door_close");
        setup(DOOR_OPEN, "sfx/door_open");
    }

    private void setup(int soundID, String soundPath) {
        setup(soundID, 0, -1, soundPath);
    }

    private void setup(int soundID, int loopStart, int loopEnd, String soundPath) {
        soundURL[soundID] = getClass().getResource("/sounds/" + soundPath + ".wav");
        this.loopStart[soundID] = loopStart;
        this.loopEnd[soundID] = loopEnd;
    }

    public void setFile(int soundID) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[soundID]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setLoopPoints(loopStart[soundID], loopEnd[soundID]);
        } catch (NullPointerException | UnsupportedAudioFileException | LineUnavailableException e) {
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

