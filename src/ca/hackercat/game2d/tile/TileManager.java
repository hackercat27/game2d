package ca.hackercat.game2d.tile;

import ca.hackercat.game2d.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
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

    public int speed = 5;

    final int INTERPOLATION_LENGTH = 30;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        mapTilePos = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

        tile = new Tile[10];
        getTextures();
        getMapData("test_map");
    }

    public void getTextures() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/tile/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/tile/dirt.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/tile/water.png")));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/tile/dirt_path.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/tile/brick.png")));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/tile/rock.png")));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/tile/tree.png")));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/tile/water_deep.png")));
            tile[7].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
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

    public void update() {

        if (gp.inputHandler.fixedCamera) { //camera mode is set to "fixed"
            if ((targetCameraWorldX == cameraWorldX) && (targetCameraWorldY == cameraWorldY)) {

                targetCameraWorldX = (gp.player.worldX - (gp.SCALED_TILE_SIZE / 2)) - (gp.player.worldX % gp.SCREEN_WIDTH);
                targetCameraWorldY = (gp.player.worldY - (gp.SCALED_TILE_SIZE / 2)) - (gp.player.worldY % gp.SCREEN_HEIGHT);

                targetCameraWorldX += (gp.SCALED_TILE_SIZE / 2);
                targetCameraWorldY += (gp.SCALED_TILE_SIZE / 2);

                interpolationIndex = 0;
                generatedInterpolation = false;
            } else {

                if (!generatedInterpolation) {
                    cameraXInterpolation = gp.util.linearInterpolate(cameraWorldX, targetCameraWorldX, INTERPOLATION_LENGTH);
                    cameraYInterpolation = gp.util.linearInterpolate(cameraWorldY, targetCameraWorldY, INTERPOLATION_LENGTH);
                    interpolationIndex = 0;
                    generatedInterpolation = true;
                }
                try {
                    cameraWorldX = (int) cameraXInterpolation[interpolationIndex];
                    cameraWorldY = (int) cameraYInterpolation[interpolationIndex];
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    interpolationIndex = 0;
                }
                if (interpolationIndex == INTERPOLATION_LENGTH) {
                    cameraWorldY = targetCameraWorldY;
                    cameraWorldX = targetCameraWorldX;
                } else {
                    interpolationIndex++;
                }

            }

        } else { // camera mode is not set to "fixed"

            if (gp.inputHandler.playerCamera) {
                cameraWorldX = (gp.player.worldX + (gp.SCALED_TILE_SIZE / 2)) - (gp.SCREEN_WIDTH / 2);
                cameraWorldY = (gp.player.worldY + (gp.SCALED_TILE_SIZE / 2)) - (gp.SCREEN_HEIGHT / 2);
            } else if (gp.inputHandler.freeCamera) {
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
            } else {
                gp.inputHandler.playerCamera = true;
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

                g2.drawImage(tile[mapTilePos[worldCol][worldRow]].image, worldX - cameraWorldX, worldY - cameraWorldY, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE, null);
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
