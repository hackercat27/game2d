package game2d.entity;

import game2d.main.Colors;
import game2d.main.GamePanel;
import game2d.main.InputHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    InputHandler inputHandler;

    public Player(GamePanel gp, InputHandler inputHandler) {
        super(gp);
        this.inputHandler = inputHandler;

        collisionBox = new Rectangle();
        collisionBox.x = 3 * gp.SCALE_FACTOR;
        collisionBox.y = 6 * gp.SCALE_FACTOR;
        collisionBox.width = 10 * gp.SCALE_FACTOR;
        collisionBox.height = 10 * gp.SCALE_FACTOR;

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

        if (inputHandler.sprintPressed) speed = 10;
        else speed = 6;

        if (inputHandler.upPressed) worldY -= speed;
        else if (inputHandler.downPressed) worldY += speed;
        else if (inputHandler.leftPressed) worldX -= speed;
        else if (inputHandler.rightPressed) worldX += speed;

        // UPDATE DIRECTION
        if (inputHandler.upPressed) direction = "up";
        else if (inputHandler.downPressed) direction = "down";
        else if (inputHandler.leftPressed) direction = "left";
        else if (inputHandler.rightPressed) direction = "right";

        // ANIMATION COUNTER
        if (inputHandler.upPressed || inputHandler.downPressed || inputHandler.leftPressed || inputHandler.rightPressed) {
            animationCounter++;
        } else {
            animationCounter = 0;
            sprite = 0;
        }
        if (animationCounter > (1.0 / (double) speed) * 35) {

            if (sprite >= 3) {
                sprite = 0;
            } else {
                sprite++;
            }

            animationCounter = 0;
        }

        // COLLISION
        interactObject(
                gp.collisionDetector.checkObject(this, true)
        );
        gp.collisionDetector.checkTile(this);
    }

    public void interactObject(int slot) {
        if (slot != -1) {
            switch(gp.obj[slot].name) {
                case "key":
                    gp.vars.keys++;
                    gp.obj[slot] = null;
                    System.out.println(gp.vars.keys);
                    break;
            }
        }
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
                else if (sprite == 3) image = left[1];
            }
            case "right" -> {
                if (sprite == 0) image = right[0];
                else if (sprite == 1) image = right[1];
                else if (sprite == 2) image = right[0];
                else if (sprite == 3) image = right[1];
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
            g2.setColor(Colors.COLLISION_BOX);
            collisionBox.x += worldX - gp.tileManager.cameraWorldX;
            collisionBox.y += worldY - gp.tileManager.cameraWorldY;
            g2.fill(collisionBox);
            collisionBox.x -= worldX - gp.tileManager.cameraWorldX;
            collisionBox.y -= worldY - gp.tileManager.cameraWorldY;
        }
    }
}
