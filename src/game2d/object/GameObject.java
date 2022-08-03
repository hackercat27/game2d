package game2d.object;

import game2d.main.Colors;
import game2d.main.GamePanel;
import game2d.util.ToolBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameObject {
    GamePanel gp;
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle collisionBox;

    public GameObject(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath) {
        ToolBox util = new ToolBox();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/object/" + imagePath + ".png")));
            //image = util.scaleImage(image, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        } catch (NullPointerException e) {
            System.err.println("Missing texture \"/textures/object" + imagePath + ".png\"!");

            image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            g2.setColor(Color.MAGENTA);
            g2.fillRect(0, 0, 16, 16);
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, 8, 8);
            g2.fillRect(8, 8, 8, 8);
        }
        return image;
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(image, worldX - gp.tileManager.cameraWorldX, worldY - gp.tileManager.cameraWorldY, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE, null);

        if (gp.inputHandler.hudToggled) {
            g2.setColor(Colors.COLLISION_BOX);
            collisionBox.x += worldX - gp.tileManager.cameraWorldX;
            collisionBox.y += worldY - gp.tileManager.cameraWorldY;
            g2.fill(collisionBox);
            collisionBox.x -= worldX - gp.tileManager.cameraWorldX;
            collisionBox.y -= worldY - gp.tileManager.cameraWorldY;
        }
    }
}
