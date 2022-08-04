package game2d.entity;

import game2d.main.Assets;
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
    public int maxHealth;

    public BufferedImage[] up, down, left, right;
    public String direction;

    public String name = "generic";

    public boolean visible;
    public boolean flickering;

    public int animationCounter = 0;
    public int sprite = 1;
    public Rectangle collisionBox;
    public Rectangle actionBox;
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
        collisionBox.x = 3 * GamePanel.SCALE_FACTOR;
        collisionBox.y = 6 * GamePanel.SCALE_FACTOR;
        collisionBox.width = 10 * GamePanel.SCALE_FACTOR;
        collisionBox.height = 8 * GamePanel.SCALE_FACTOR;
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

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (sprite == 0) image = up[0];
                else if (sprite == 1) image = up[1];
                else if (sprite == 2) image = up[0];
                else if (sprite == 3) image = up[2];
            }
            case "down" -> {
                if (sprite == 0) image = down[0];
                else if (sprite == 1) image = down[1];
                else if (sprite == 2) image = down[0];
                else if (sprite == 3) image = down[2];
            }
            case "left" -> {
                if (sprite == 0) image = left[0];
                else if (sprite == 1) image = left[1];
                else if (sprite == 2) image = left[0];
                else if (sprite == 3) image = left[2];
            }
            case "right" -> {
                if (sprite == 0) image = right[0];
                else if (sprite == 1) image = right[1];
                else if (sprite == 2) image = right[0];
                else if (sprite == 3) image = right[2];
            }
        }
        if (flickering) {
            visible = ((gp.globalCounter / 2) % 2) == 0;
        } else {
            visible = true;
        }

        if (visible) {
            g2.drawImage(image, worldX - gp.tileManager.cameraWorldX, worldY - gp.tileManager.cameraWorldY, GamePanel.SCALED_TILE_SIZE, GamePanel.SCALED_TILE_SIZE, null);
        }
        if (gp.inputHandler.hudToggled) {
            g2.setColor(Assets.COLOR_COLLISION_BOX);
            collisionBox.x += worldX - gp.tileManager.cameraWorldX;
            collisionBox.y += worldY - gp.tileManager.cameraWorldY;
            g2.fill(collisionBox);
            collisionBox.x -= worldX - gp.tileManager.cameraWorldX;
            collisionBox.y -= worldY - gp.tileManager.cameraWorldY;

            if (actionBox != null) {
                g2.setColor(Assets.COLOR_ACTION_BOX);
                actionBox.x += worldX - gp.tileManager.cameraWorldX;
                actionBox.y += worldY - gp.tileManager.cameraWorldY;
                g2.fill(actionBox);
                actionBox.x -= worldX - gp.tileManager.cameraWorldX;
                actionBox.y -= worldY - gp.tileManager.cameraWorldY;
            }
        }
    }

    public void setAction() {

    }

    public void update() {

    }
}
