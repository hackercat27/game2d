package ca.hackercat.game2d.main;

import ca.hackercat.game2d.entity.Player;
import ca.hackercat.game2d.overlay.BigChungus;
import ca.hackercat.game2d.overlay.HeadsUpDisplay;
import ca.hackercat.game2d.overlay.Vignette;
import ca.hackercat.game2d.tile.TileManager;
import ca.hackercat.util.Audio;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {

    //screen settings
    public final int RAW_TILE_SIZE = 16;
    public final int SCALE_FACTOR = 2;
    public final int SCALED_TILE_SIZE = RAW_TILE_SIZE * SCALE_FACTOR;
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROW = 9;

    public final int SCREEN_WIDTH = SCALED_TILE_SIZE * MAX_SCREEN_COL; //1280 pixels
    public final int SCREEN_HEIGHT = SCALED_TILE_SIZE * MAX_SCREEN_ROW; //720 pixels

    //world settings
    public final int MAX_WORLD_COL = 100;
    public final int MAX_WORLD_ROW = 100;
    public final int WORLD_WIDTH = SCALED_TILE_SIZE * MAX_WORLD_COL;
    public final int WORLD_HEIGHT = SCALED_TILE_SIZE * MAX_WORLD_ROW;

    //fullscreen stuff
    int screenWidth2 = SCREEN_WIDTH;
    int screenHeight2 = SCREEN_HEIGHT;

    final int FPS = 60;
    public double currentFps;
    public int globalCounter;

    BufferedImage tempScreen;
    Graphics2D g2;

    Thread gameThread;

    public InputHandler inputHandler = new InputHandler(this);
    public Player player = new Player(this, inputHandler);
    TileManager tileManager = new TileManager(this);
    public GameVars vars = new GameVars();
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    Audio music = new Audio();
    Audio sfx = new Audio();
    Vignette vignette = new Vignette(this);
    HeadsUpDisplay hud = new HeadsUpDisplay(this);

    public GamePanel() {

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.setFocusable(true);
    }
    public void setupGame() {
        tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        setFullScreen();
    }
    public void setFullScreen() {
        if (vars.fullscreen) {
            // get local screen device
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            gd.setFullScreenWindow(Main.window);

            screenWidth2 = Main.window.getWidth();
            screenHeight2 = Main.window.getHeight();
        }
    }
    public void startGameThread() {


        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        setupGame();
        double frameTime = 1000 / (double) FPS;
        double nextDrawTime = System.currentTimeMillis() + frameTime;

        double delta;
        long lastTime = System.currentTimeMillis();
        long currentTime;

        long lastFrameUpdateTime = System.currentTimeMillis();

        double framerate;

        int framesRendered = 0;

        while(gameThread != null) {
            //calculate framerate
            currentTime = System.currentTimeMillis();
            delta = currentTime - lastTime;
            framerate = 1 / (delta * 1000) * 1E6 * 10;
            framerate = Math.round(framerate) / 10.0;
            lastTime = currentTime;

            //increase the global counter, which counts how many frames have been rendered since startup
            globalCounter++;

            //only update the framerate every 500 milliseconds, to make the number more readable
            if (currentTime - lastFrameUpdateTime >= 500) {
                this.currentFps = framerate;
                lastFrameUpdateTime = currentTime;
            }

            //update game logic
            update();

            //draw the screen
            paintScreenBuffer();
            paintScreen();
            framesRendered++;

            try {
                double remainingTime = nextDrawTime - System.currentTimeMillis();

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                drawTime[5] = (long) remainingTime;

                nextDrawTime += frameTime;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void update() {
        player.update();
    }
    public void paintScreenBuffer() {
        long drawStartTime;
        long frameStartTime = System.nanoTime();

        drawStartTime = System.nanoTime();
        tileManager.draw(g2);
        drawTime[0] = System.nanoTime() - drawStartTime;

        drawStartTime = System.nanoTime();
        player.draw(g2);
        drawTime[1] = System.nanoTime() - drawStartTime;

        drawStartTime = System.nanoTime();
        vignette.draw(g2);
        drawTime[2] = System.nanoTime() - drawStartTime;

        drawStartTime = System.nanoTime();
        hud.draw(g2);
        drawTime[3] = System.nanoTime() - drawStartTime;
        drawTime[4] = System.nanoTime() - frameStartTime;
    }
    public void paintScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public long[] drawTime = new long[6];

    public void playMusic(int soundID) {
        music.setFile(soundID);
        music.play(true);
    }

    public void playSound(int soundID) {
        sfx.setFile(soundID);
        sfx.play(false);
    }
}
