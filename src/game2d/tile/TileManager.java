package game2d.tile;

import game2d.main.GamePanel;
import game2d.util.ToolBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTilePos;

    int targetCameraWorldX = 0;
    int targetCameraWorldY = 0;
    public int cameraWorldX = 0;
    public int cameraWorldY = 0;

    public int speed;

    final int INTERPOLATION_LENGTH = 30;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        mapTilePos = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

        speed = 5 * gp.SCALE_FACTOR;
        tile = new Tile[10];
        getTextures();
        getMapData("test_map");
    }

    public void getTextures() {
        setup(0, "grass", false);
        setup(1, "dirt", true);
        setup(2, "water", false);
        setup(3, "dirt_path", false);
        setup(4, "brick", true);
        setup(5, "rock", true);
        setup(6, "tree", true);
        setup(7, "water_deep", true);
    }

    public void setup(int index, String imagePath, boolean collision) {
        ToolBox util = new ToolBox();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/tile/" + imagePath + ".png")));
            tile[index].image = util.scaleImage(tile[index].image, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        } catch (NullPointerException e) {
            System.err.println("Missing texture \"/textures/tile" + imagePath + ".png\"!");

            tile[index].image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = tile[index].image.createGraphics();
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, 16, 16);
            g2.setColor(Color.MAGENTA);
            g2.fillRect(0, 0, 8, 8);
            g2.fillRect(8, 8, 8, 8);
        }
    }

    public void getMapData(String path) {

        path = "/maps/" + path + ".txt";

        try {
            InputStream is = getClass().getResourceAsStream(path);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while ((col < gp.MAX_WORLD_COL) && (row < gp.MAX_WORLD_ROW)) {
                String line = br.readLine();

                while (col < gp.MAX_WORLD_COL) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTilePos[col][row] = num;
                    col++;
                }
                if (col == gp.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean generatedInterpolation = false;
    double[] cameraXInterpolation = new double[INTERPOLATION_LENGTH];
    double[] cameraYInterpolation = new double[INTERPOLATION_LENGTH];
    int interpolationIndex = 0;

    int cameraXVelocity;
    int cameraYVelocity;
    int targetCameraXVelocity;
    int targetCameraYVelocity;

    final int CAMERA_MODE_FIXED = 0;
    final int CAMERA_MODE_PLAYER = 1;
    final int CAMERA_MODE_FREE = 2;

    public void update() {

        if (gp.vars.cameraMode == CAMERA_MODE_FIXED) { //camera mode is set to "fixed"
            updateFixedCamera();
        } else if (gp.vars.cameraMode == CAMERA_MODE_PLAYER) {
            updatePlayerCamera();
        } else if (gp.vars.cameraMode == CAMERA_MODE_FREE) {
            updateFreeCamera();
        } else {
            gp.vars.cameraMode = gp.vars.defaultCamera;
        }

        //if the camera is pointing out of bounds, move it back to the closest in bounds area of the map
        if (cameraWorldX < 0) { //left edge
            cameraWorldX = 0;
        } else if (cameraWorldX > gp.WORLD_WIDTH - gp.SCREEN_WIDTH) { //right edge
            cameraWorldX = gp.WORLD_WIDTH - gp.SCREEN_WIDTH;
        }
        if (cameraWorldY < 0) { //top edge
            cameraWorldY = 0;
        } else if (cameraWorldY > gp.WORLD_HEIGHT - gp.SCREEN_HEIGHT) { //bottom edge
            cameraWorldY = gp.WORLD_HEIGHT - gp.SCREEN_HEIGHT;
        }
    }

    public void updateFixedCamera() {
        if ((targetCameraWorldX == cameraWorldX) && (targetCameraWorldY == cameraWorldY)) {

            targetCameraWorldX = (gp.player.worldX - (gp.SCALED_TILE_SIZE / 2)) - (gp.player.worldX % gp.SCREEN_WIDTH);
            targetCameraWorldY = (gp.player.worldY - (gp.SCALED_TILE_SIZE / 2)) - (gp.player.worldY % gp.SCREEN_HEIGHT);

            //targetCameraWorldX += (gp.SCALED_TILE_SIZE / 2);
            //targetCameraWorldY += (gp.SCALED_TILE_SIZE / 2);

            interpolationIndex = 0;
            generatedInterpolation = false;
        } else {
            if (!generatedInterpolation) {
                cameraXInterpolation = gp.util.cosineInterpolate(cameraWorldX, targetCameraWorldX, INTERPOLATION_LENGTH);
                cameraYInterpolation = gp.util.cosineInterpolate(cameraWorldY, targetCameraWorldY, INTERPOLATION_LENGTH);
                interpolationIndex = 0;
                generatedInterpolation = true;
            }

            // move the camera to the interpolated coordinates (rounded, since camera coordinates are integers)
            cameraWorldX = (int) Math.round(cameraXInterpolation[interpolationIndex]);
            cameraWorldY = (int) Math.round(cameraYInterpolation[interpolationIndex]);

            if (interpolationIndex == INTERPOLATION_LENGTH) {
                cameraWorldY = targetCameraWorldY;
                cameraWorldX = targetCameraWorldX;
            } else {
                interpolationIndex++;
            }
        }
    }

    public void updatePlayerCamera() {
        cameraWorldX = (gp.player.worldX + (gp.SCALED_TILE_SIZE / 2)) - (gp.SCREEN_WIDTH / 2);
        cameraWorldY = (gp.player.worldY + (gp.SCALED_TILE_SIZE / 2)) - (gp.SCREEN_HEIGHT / 2);
    }

    public void updateFreeCamera() {
        if (gp.inputHandler.cameraPanUp) {
            cameraWorldY -= speed;
        }
        if (gp.inputHandler.cameraPanDown) {
            cameraWorldY += speed;
        }
        if (gp.inputHandler.cameraPanLeft) {
            cameraWorldX -= speed;
        }
        if (gp.inputHandler.cameraPanRight) {
            cameraWorldX += speed;
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;
        int worldX = 0;
        int worldY = 0;

        while (worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW) {

            //if the tile we're trying to render isn't on the screen, don't render it
            if (worldX > cameraWorldX ||
                worldY > cameraWorldY ||
                worldX < cameraWorldX + gp.SCREEN_WIDTH ||
                worldY < cameraWorldY + gp.SCREEN_HEIGHT) {

                g2.drawImage(tile[mapTilePos[worldCol][worldRow]].image, worldX - cameraWorldX, worldY - cameraWorldY,null);
            }
            worldCol++;
            worldX = worldCol * gp.SCALED_TILE_SIZE;

            if (worldCol == gp.MAX_WORLD_COL) {
                worldCol = 0;
                worldX = worldCol;

                worldRow++;
                worldY = worldRow * gp.SCALED_TILE_SIZE;
            }

        }

    }
}
