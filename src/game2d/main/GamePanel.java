package game2d.main;

import game2d.entity.Entity;
import game2d.entity.Player;
import game2d.overlay.Background;
import game2d.overlay.HeadsUpDisplay;
import game2d.overlay.ScreenEffects;
import game2d.tile.TileManager;
import game2d.util.Audio;
import game2d.util.ToolBox;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    //screen settings
    public static final int RAW_TILE_SIZE = 16;
    public static final int SCALE_FACTOR = 5;
    public static final int SCALED_TILE_SIZE = RAW_TILE_SIZE * SCALE_FACTOR;
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 9;

    public static final int SCREEN_WIDTH = SCALED_TILE_SIZE * MAX_SCREEN_COL; //512 pixels
    public static final int SCREEN_HEIGHT = SCALED_TILE_SIZE * MAX_SCREEN_ROW; //288 pixels

    //world settings
    public static final int MAX_WORLD_COL = 100;
    public static final int MAX_WORLD_ROW = 100;
    public static final int WORLD_WIDTH = SCALED_TILE_SIZE * MAX_WORLD_COL;
    public static final int WORLD_HEIGHT = SCALED_TILE_SIZE * MAX_WORLD_ROW;

    //fullscreen stuff
    int screenWidth2 = SCREEN_WIDTH;
    int screenHeight2 = SCREEN_HEIGHT;

    final static int FPS = 60;
    public double currentFps;
    public int globalCounter; //counts number of ticks since startup

    BufferedImage tempScreen;
    Graphics2D g2;

    Thread logicThread;

    public InputHandler inputHandler = new InputHandler(this);
    public Player player;
    public Entity[] obj = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();
    public TileManager tileManager = new TileManager(this);
    public GameVars vars = new GameVars();
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    public ToolBox util = new ToolBox(this, vars);
    AssetSetter assetSetter = new AssetSetter(this);
    Audio music = new Audio(this);
    Audio sfx = new Audio(this);
    Background background = new Background(this);
    ScreenEffects screenEffects = new ScreenEffects(this);
    HeadsUpDisplay hud = new HeadsUpDisplay(this);

    // GAME STATES
    public int currentGameState;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int DIALOGUE_STATE = 3;

    public GamePanel() {

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        currentGameState = PLAY_STATE;

        playMusic(1);

        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setEnemy();
        assetSetter.setPlayer();

        tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        if (vars.fullscreen) {
            setFullScreen();
        }

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
    public void setFullScreen() {
        // get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
    }

    public void startLogicThread() {
        logicThread = new Thread(this);
        logicThread.start();
    }

    public void drawRect(Rectangle s) {
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

        while(logicThread != null) {
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

            //draw the screen
            paintScreenBuffer();
            update();
            paintScreen();

            try {
                double remainingTime = nextDrawTime - System.currentTimeMillis();

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += frameTime;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void update() {
        music.update();
        sfx.update();
        if (currentGameState == PLAY_STATE) {
            //update all entities
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null && (
                        (obj[i].worldX > tileManager.cameraWorldX - SCALED_TILE_SIZE &&
                                obj[i].worldY > tileManager.cameraWorldY - SCALED_TILE_SIZE &&
                                obj[i].worldX < tileManager.cameraWorldX + SCREEN_WIDTH + SCALED_TILE_SIZE &&
                                obj[i].worldY < tileManager.cameraWorldY + SCREEN_HEIGHT + SCALED_TILE_SIZE)
                )) obj[i].update();
            }

            player.update();
            tileManager.update();
        }
        if (currentGameState == PAUSE_STATE) {

        }
        if (currentGameState == DIALOGUE_STATE) {
            if (inputHandler.actionPressed) {
                currentGameState = PLAY_STATE;
            }
        }
    }

    public void paintScreenBuffer() {
        background.draw(g2);
        tileManager.draw(g2);

        //ADD ENTITIES TO THE DRAW LIST
        entityList.add(player);
        for (Entity value : obj) {
            if (value != null && (
                    (value.worldX > tileManager.cameraWorldX - SCALED_TILE_SIZE &&
                            value.worldY > tileManager.cameraWorldY - SCALED_TILE_SIZE &&
                            value.worldX < tileManager.cameraWorldX + SCREEN_WIDTH + SCALED_TILE_SIZE &&
                            value.worldY < tileManager.cameraWorldY + SCREEN_HEIGHT + SCALED_TILE_SIZE)

                    )) {
                entityList.add(value);
            }
        }
        // SORT THE ENTITIES BY THEIR Y VALUES
        entityList.sort(Comparator.comparingInt(e -> e.worldY));
        // DRAW ENTITIES
        for (Entity entity : entityList) {
            entity.draw(g2);
        }
        entityList.clear();


        screenEffects.draw(g2);
        hud.draw(g2);
    }
    public void paintScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int soundID) {
        music.setFile(soundID);
        music.play(true);
    }

    public void playSound(int soundID) {
        sfx.setFile(soundID);
        sfx.play(false);
    }
}