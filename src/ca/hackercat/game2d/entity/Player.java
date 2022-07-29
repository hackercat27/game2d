package ca.hackercat.game2d.entity;

import ca.hackercat.game2d.main.GamePanel;
import ca.hackercat.game2d.main.InputHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gp;
    InputHandler inputHandler;

    public int screenX, screenY;

    public Player(GamePanel gp, InputHandler inputHandler) {
        super(gp);
        this.gp = gp;
        this.inputHandler = inputHandler;

        up = new BufferedImage[3];
        down = new BufferedImage[3];
        left = new BufferedImage[3];
        right = new BufferedImage[3];

        collisionBox = new Rectangle();
        collisionBox.x = 3 * gp.SCALE_FACTOR;
        collisionBox.y = 6 * gp.SCALE_FACTOR;
        collisionBox.width = 10 * gp.SCALE_FACTOR;
        collisionBox.height = 8 * gp.SCALE_FACTOR;

        screenX = gp.SCREEN_WIDTH / 2 - gp.SCALED_TILE_SIZE / 2;
        screenY = gp.SCREEN_HEIGHT / 2 - gp.SCALED_TILE_SIZE / 2;

        setDefaultValues();
        getTextures();
    }

    public void setDefaultValues() {
        worldX = gp.SCALED_TILE_SIZE * 6;
        worldY = gp.SCALED_TILE_SIZE * 2;
        speed = 5;
        direction = "down";
    }

    public void getTextures() {
        try {

            //walking textures
            up[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/up0.png")));
            up[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/up1.png")));
            up[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/up2.png")));
            left[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/up2.png")));
            left[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/up2.png")));
            left[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/up2.png")));
            down[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/down0.png")));
            down[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/down1.png")));
            down[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/down2.png")));
            right[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/right0.png")));
            right[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/right1.png")));
            right[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/right2.png")));

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        }
    }

    public void update() {

        // MOVEMENT

        if (inputHandler.sprintPressed) speed = 2;
        else speed = 1;

        if (inputHandler.upPressed) worldY -= speed * gp.SCALE_FACTOR;
        else if (inputHandler.downPressed) worldY += speed * gp.SCALE_FACTOR;
        else if (inputHandler.leftPressed) worldX -= speed * gp.SCALE_FACTOR;
        else if (inputHandler.rightPressed) worldX += speed * gp.SCALE_FACTOR;

        // ANIMATIONS
        if (inputHandler.upPressed) direction = "up";
        else if (inputHandler.downPressed) direction = "down";
        else if (inputHandler.leftPressed) direction = "left";
        else if (inputHandler.rightPressed) direction = "right";

        if (inputHandler.upPressed || inputHandler.downPressed || inputHandler.leftPressed || inputHandler.rightPressed) {
            animationCounter++;
        } else {
            animationCounter = 0;
            spriteNum = 0;
        }
        if ((double) animationCounter > (1.0 / (double) (speed * 3) * 20)) {

            if (spriteNum >= 3) {
                spriteNum = 0;
            } else {
                spriteNum++;
            }

            animationCounter = 0;
        }

        // COLLISION
        gp.collisionDetector.checkTile(this);
        gp.collisionDetector.handleCollisions(this);
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNum == 0) image = up[0];
                else if (spriteNum == 1) image = up[1];
                else if (spriteNum == 2) image = up[0];
                else if (spriteNum == 3) image = up[2];
            }
            case "down" -> {
                if (spriteNum == 0) image = down[0];
                else if (spriteNum == 1) image = down[1];
                else if (spriteNum == 2) image = down[0];
                else if (spriteNum == 3) image = down[2];
            }
            case "left" -> {
                if (spriteNum == 0) image = left[0];
                else if (spriteNum == 1) image = left[1];
                else if (spriteNum == 2) image = left[0];
                else if (spriteNum == 3) image = left[2];
            }
            case "right" -> {
                if (spriteNum == 0) image = right[0];
                else if (spriteNum == 1) image = right[1];
                else if (spriteNum == 2) image = right[0];
                else if (spriteNum == 3) image = right[2];
            }
        }
        if (flickering) {
            visible = ((gp.globalCounter / 2) % 2) == 0;
        } else {
            visible = true;
        }

        if (visible) {
            g2.drawImage(image, worldX - gp.tileManager.cameraWorldX, worldY - gp.tileManager.cameraWorldY, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE, null);
        }
        if (inputHandler.hudToggled) {
            g2.setColor(Color.RED);
            collisionBox.x += worldX - gp.tileManager.cameraWorldX;
            collisionBox.y += worldY - gp.tileManager.cameraWorldY;
            g2.draw(collisionBox);
            collisionBox.x -= worldX - gp.tileManager.cameraWorldX;
            collisionBox.y -= worldY - gp.tileManager.cameraWorldY;
        }
    }
}
