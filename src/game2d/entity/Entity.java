package game2d.entity;

import game2d.main.GamePanel;
import game2d.util.ToolBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public int health;

    public BufferedImage[] up, down, left, right;
    public String direction;

    public boolean visible;
    public boolean flickering;

    public int animationCounter = 0;
    public int sprite = 1;
    public Rectangle collisionBox;
    public boolean collisionAbove, collisionBelow, collisionLeft, collisionRight;

    public GamePanel gp;

    public Entity(GamePanel gp) {
        this.gp = gp;
        setDefaultValues();
        getTextures();
    }

    public void getTextures() {}

    public void setDefaultValues() {
        collisionBox = new Rectangle();
        collisionBox.x = 3 * gp.SCALE_FACTOR;
        collisionBox.y = 6 * gp.SCALE_FACTOR;
        collisionBox.width = 10 * gp.SCALE_FACTOR;
        collisionBox.height = 8 * gp.SCALE_FACTOR;
    }

    public BufferedImage setup(String imagePath) {
        ToolBox util = new ToolBox();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/entity/" + imagePath + ".png")));
            //image = util.scaleImage(image, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        } catch (NullPointerException e) {
            System.err.println("Missing texture \"/textures/entity" + imagePath + ".png\"!");

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
}
