package ca.hackercat.game2d.entity;

import ca.hackercat.game2d.main.GamePanel;
import ca.hackercat.game2d.main.InputHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    InputHandler inputHandler;

    public int screenX, screenY;

    public Player(GamePanel gp, InputHandler inputHandler) {
        super(gp);
        this.inputHandler = inputHandler;

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

    @Override
    public void setDefaultValues() {
        worldX = gp.SCALED_TILE_SIZE * 6;
        worldY = gp.SCALED_TILE_SIZE * 2;
        speed = 5;
        direction = "down";
        health = 60;
    }

    public void getTextures() {
        up = new BufferedImage[3];
        down = new BufferedImage[3];
        left = new BufferedImage[2];
        right = new BufferedImage[2];

        up[0] = setup("/player/up0");
        up[1] = setup("/player/up1");
        up[2] = setup("/player/up2");
        left[0] = setup("/player/left0");
        left[1] = setup("/player/left1");
        down[0] = setup("/player/down0");
        down[1] = setup("/player/down1");
        down[2] = setup("/player/down2");
        right[0] = setup("/player/right0");
        right[1] = setup("/player/right1");
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
                else if (spriteNum == 3) image = left[1];
            }
            case "right" -> {
                if (spriteNum == 0) image = right[0];
                else if (spriteNum == 1) image = right[1];
                else if (spriteNum == 2) image = right[0];
                else if (spriteNum == 3) image = right[1];
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
