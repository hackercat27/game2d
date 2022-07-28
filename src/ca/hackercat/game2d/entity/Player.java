package ca.hackercat.game2d.entity;

import ca.hackercat.game2d.main.GamePanel;
import ca.hackercat.game2d.main.InputHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    InputHandler inputHandler;

    public BufferedImage up3, up4, down3, down4, left3, left4, right3, right4;

    public int screenX, screenY;

    public Player(GamePanel gp, InputHandler inputHandler) {
        this.gp = gp;
        this.inputHandler = inputHandler;

        collisionBox = new Rectangle();
        collisionBox.x = 3 * gp.SCALE_FACTOR;
        collisionBox.y = 6 * gp.SCALE_FACTOR;
        collisionBox.width = 10 * gp.SCALE_FACTOR;
        collisionBox.height = 10 * gp.SCALE_FACTOR;

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
            up0 = ImageIO.read(getClass().getResourceAsStream("/textures/player/up0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/textures/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/textures/player/up2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/misc/missing_texture.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/misc/missing_texture.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/misc/missing_texture.png"));
            down0 = ImageIO.read(getClass().getResourceAsStream("/textures/player/down0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/textures/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/textures/player/down2.png"));
            right0 = ImageIO.read(getClass().getResourceAsStream("/misc/missing_texture.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/misc/missing_texture.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/misc/missing_texture.png"));

            collisionTexture = ImageIO.read(getClass().getResourceAsStream("/textures/player/collision_box.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        }
    }

    public void update() {

        if (inputHandler.upPressed) direction = "up";
        else if (inputHandler.downPressed) direction = "down";
        else if (inputHandler.leftPressed) direction = "left";
        else if (inputHandler.rightPressed) direction = "right";

        if (inputHandler.sprintPressed) speed = 2;
        else speed = 1;

        colliding = false;
        inWater = false;
        slowed = false;
        gp.collisionDetector.checkTile(this);

        if (!colliding) {
            if (inputHandler.upPressed) worldY -= speed * gp.SCALE_FACTOR;
            else if (inputHandler.downPressed) worldY += speed * gp.SCALE_FACTOR;
            else if (inputHandler.leftPressed) worldX -= speed * gp.SCALE_FACTOR;
            else  if (inputHandler.rightPressed) worldX += speed * gp.SCALE_FACTOR;

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
        }
        //TODO: make sprite numbering suck less, and don't hardcode the values
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNum == 0) image = up0;
                else if (spriteNum == 1) image = up1;
                else if (spriteNum == 2) image = up0;
                else if (spriteNum == 3) image = up2;
            }
            case "left" -> {
                if (spriteNum == 0) image = left0;
                else if (spriteNum == 1) image = left1;
                else if (spriteNum == 2) image = left0;
                else if (spriteNum == 3) image = left2;
            }
            case "down" -> {
                if (spriteNum == 0) image = down0;
                else if (spriteNum == 1) image = down1;
                else if (spriteNum == 2) image = down0;
                else if (spriteNum == 3) image = down2;
            }
            case "right" -> {
                if (spriteNum == 0) image = right0;
                else if (spriteNum == 1) image = right1;
                else if (spriteNum == 2) image = right0;
                else if (spriteNum == 3) image = right2;
            }
        }
        if (flickering) {
            visible = ((gp.globalCounter / 2) % 2) == 0;
        } else {
            visible = true;
        }

        int x = screenX;
        int y = screenY;

        if (screenX > worldX) {
            x = worldX;
        }
        if (screenY > worldY) {
            y = worldY;
        }
        int rightOffset = gp.SCREEN_WIDTH - screenX;
        if (rightOffset > gp.WORLD_WIDTH - worldX) { //right edge
            x = gp.SCREEN_WIDTH - (gp.WORLD_WIDTH - worldX);
            screenX = x;
        }
        int bottomOffset = gp.SCREEN_HEIGHT - screenY;
        if (bottomOffset > gp.WORLD_HEIGHT - worldY) { //bottom edge
            y = gp.SCREEN_HEIGHT - (gp.WORLD_HEIGHT - worldY);
            screenY = y;
        }

        if (visible) {
            g2.drawImage(image, x, y, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE, null);
        }
        if (inputHandler.hudToggled) {
            g2.drawImage(collisionTexture, x, y, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE, null);
        }
    }
}
