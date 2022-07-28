package ca.hackercat.game2d.tile;

import ca.hackercat.game2d.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTilePos;

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
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/textures/tile/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/textures/tile/dirt.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/textures/tile/water.png"));
            tile[2].slowTile = true;
            tile[2].waterTile = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/textures/tile/dirt_path.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/textures/tile/brick.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/textures/tile/rock.png"));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/textures/tile/tree.png"));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/textures/tile/water_deep.png"));
            tile[7].slowTile = true;
            tile[7].waterTile = true;
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

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while ((worldCol < gp.MAX_WORLD_COL) && (worldRow < gp.MAX_WORLD_ROW)) {
            int tileIndex = mapTilePos[worldCol][worldRow];

            int worldX = worldCol * gp.SCALED_TILE_SIZE;
            int worldY = worldRow * gp.SCALED_TILE_SIZE;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (gp.player.screenX > gp.player.worldX) { //left edge
                screenX = worldX;
            }
            if (gp.player.screenY > gp.player.worldY) { //top edge
                screenY = worldY;
            }
            int rightOffset = gp.SCREEN_WIDTH - gp.player.screenX;
            if (rightOffset > gp.WORLD_WIDTH - gp.player.worldX) { //right edge
                screenX = gp.SCREEN_WIDTH - (gp.WORLD_WIDTH - worldX);
            }
            int bottomOffset = gp.SCREEN_HEIGHT - gp.player.screenY;
            if (bottomOffset > gp.WORLD_HEIGHT - gp.player.worldY) { //bottom edge
                screenY = gp.SCREEN_HEIGHT - (gp.WORLD_HEIGHT - worldY);
            }

            if ((worldX + gp.SCALED_TILE_SIZE) > (gp.player.worldX - gp.player.screenX) &&
                    (worldX - gp.SCALED_TILE_SIZE)  < (gp.player.worldX + gp.player.screenX) &&
                    (worldY + gp.SCALED_TILE_SIZE)  > (gp.player.worldY - gp.player.screenY) &&
                    (worldY - gp.SCALED_TILE_SIZE)  < (gp.player.worldY + gp.player.screenY)) {

                g2.drawImage(tile[tileIndex].image, screenX, screenY, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE, null);
            } else if (gp.player.screenX > gp.player.worldX ||
                    gp.player.screenY > gp.player.worldY ||
                    rightOffset > gp.WORLD_WIDTH - gp.player.worldX ||
                    bottomOffset > gp.WORLD_HEIGHT - gp.player.worldY) {

                g2.drawImage(tile[tileIndex].image, screenX, screenY, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE, null);
            }
            worldCol++;

            if (worldCol == gp.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
