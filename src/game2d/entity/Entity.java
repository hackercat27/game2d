package game2d.entity;

import game2d.main.Assets;
import game2d.main.GamePanel;
import game2d.util.Audio;
import game2d.util.ToolBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {

    public BufferedImage[] up = new BufferedImage[3];
    public BufferedImage[] down = new BufferedImage[3];
    public BufferedImage[] left = new BufferedImage[3];
    public BufferedImage[] right = new BufferedImage[3];

    public int worldX, worldY;
    public int speed;
    public String direction;
    public String name = "generic";
    public boolean visible;
    public int flickering = 0;
    public static final int FLICKERING_TIME = 120;
    public int animationCounter = 0;
    public int sprite = 1;

    public int health;
    public int maxHealth;
    public int attackDamage = 1;

    public int actionCounter = 0;
    public String dialogue = "missingNo.";

    public Rectangle collisionBox;
    public Rectangle actionBox;
    public boolean colliding;

    public Graphics2D g2;
    public GamePanel gp;

    public boolean snapCollision;

    public boolean onPath = false;

    // DOOR
    public boolean open = true;
    public int closeTimer = 0;
    public static final int CLOSE_TIMER_START_VALUE = 2;

    public Entity(GamePanel gp) {
        this.gp = gp;
        getTextures();
    }

    public static Rectangle setBox(int x, int y, int width, int height) {
        Rectangle rect = new Rectangle();
        rect.x = x * GamePanel.SCALE_FACTOR;
        rect.y = y * GamePanel.SCALE_FACTOR;
        rect.width = width * GamePanel.SCALE_FACTOR;
        rect.height = height * GamePanel.SCALE_FACTOR;

        return rect;
    }

    public void getTextures() {}

    public BufferedImage setup(String imagePath) {
        ToolBox util = new ToolBox();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/entity/" + imagePath + ".png")));
            image = util.scaleImage(image, GamePanel.SCALED_TILE_SIZE, GamePanel.SCALED_TILE_SIZE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        } catch (NullPointerException e) {
            System.err.println("Missing texture \"/textures/entity/" + imagePath + ".png\"!");

            return Assets.MISSING_TEXTURE;
        }
        return image;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

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
        if (flickering > 0) {
            flickering--;
            visible = (gp.globalCounter % 2) == 0;
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

    public void checkCollision() {

    }

    public void update() {

    }

    public void interactObject(int slot) {
        if (slot != -1) {
            switch(gp.obj[slot].name) {
                case "key":
                    break;
                case "door":
                    gp.obj[slot].open = true;
                    gp.obj[slot].closeTimer = CLOSE_TIMER_START_VALUE;
                    gp.playSound(Audio.DOOR_OPEN);
                    break;
                case "door_open":
                    gp.obj[slot].closeTimer = CLOSE_TIMER_START_VALUE;
                    break;
                case "slime":
                case "player":
                case "door_locked":
                case "generic":
                    gp.collisionDetector.handleObjectCollision(this, slot);
                    actionCounter = 0;
            }
        }
    }



    public String getDirectionToPositionBiasX(Entity entity, int x, int y) {
        int margin = 16;
        if (entity.worldX > x + margin) {
            return "left";
        }
        else if (entity.worldX < x - margin) {
            return "right";
        }
        else if (entity.worldY > y + margin) {
            return "up";
        }
        else if (entity.worldY < y - margin) {
            return "down";
        }
        return "none";
    }
    public String getDirectionToPositionBiasY(Entity entity, int x, int y) {
        int margin = 16;
        if (entity.worldY > y + margin) {
            return "up";
        }
        else if (entity.worldY < y - margin) {
            return "down";
        }
        else if (entity.worldX > x + margin) {
            return "left";
        }
        else if (entity.worldX < x - margin) {
            return "right";
        }
        return "none";

    }

    public String getDirectionToObjectBiasX(Entity entity, Entity target) {
        return getDirectionToPositionBiasX(entity, target.worldX, target.worldY);
    }
    public String getDirectionToObjectBiasY(Entity entity, Entity target) {
        return getDirectionToPositionBiasY(entity, target.worldX, target.worldY);
    }
}
