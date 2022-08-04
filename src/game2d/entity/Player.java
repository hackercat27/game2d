package game2d.entity;

import game2d.main.Assets;
import game2d.main.GamePanel;
import game2d.main.InputHandler;
import game2d.util.Audio;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    InputHandler inputHandler;
    public int stamina = 0;
    public int maxStamina = 150;

    public Player(GamePanel gp, InputHandler inputHandler) {
        super(gp);
        this.inputHandler = inputHandler;

        collisionBox = new Rectangle();
        collisionBox.x = 3 * GamePanel.SCALE_FACTOR;
        collisionBox.y = 6 * GamePanel.SCALE_FACTOR;
        collisionBox.width = 10 * GamePanel.SCALE_FACTOR;
        collisionBox.height = 10 * GamePanel.SCALE_FACTOR;

        setDefaultValues();
        getTextures();
    }

    @Override
    public void setDefaultValues() {
        maxHealth = 20;
        worldX = GamePanel.SCALED_TILE_SIZE * 6;
        worldY = GamePanel.SCALED_TILE_SIZE * 2;
        speed = 5;
        direction = "down";
        health = 60;
    }

    public void getTextures() {
        up = new BufferedImage[3];
        down = new BufferedImage[3];
        left = new BufferedImage[3];
        right = new BufferedImage[3];

        up[0] = setup("/player/up0");
        up[1] = setup("/player/up1");
        up[2] = setup("/player/up2");

        left[0] = setup("/player/left0");
        left[1] = setup("/player/left1");
        left[2] = setup("/player/left2");

        down[0] = setup("/player/down0");
        down[1] = setup("/player/down1");
        down[2] = setup("/player/down2");

        right[0] = setup("/player/right0");
        right[1] = setup("/player/right1");
        right[2] = setup("/player/right2");
    }

    public void update() {

        if (health > maxHealth) {
            health = maxHealth;
        }

        // MOVEMENT

        updateStamina();

        if (inputHandler.sprintPressed && (stamina > 0)) speed = 10;
        else if (stamina == 0) speed = 4;
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
        updateAnimations();

        // COLLISION
        interactObject(
                gp.collisionDetector.checkObject(this, true)
        );
        gp.collisionDetector.checkTile(this);
    }

    private void updateAnimations() {
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
    }

    private void updateStamina() {
        if ((inputHandler.upPressed || inputHandler.downPressed ||  inputHandler.leftPressed || inputHandler.rightPressed) && inputHandler.sprintPressed) {
            stamina--;
        } else if (!(inputHandler.upPressed || inputHandler.downPressed ||  inputHandler.leftPressed || inputHandler.rightPressed)) {
            stamina += 2;
        }

        if (stamina < 0) {
            stamina = 0;
        } else if (stamina > maxStamina) {
            stamina = maxStamina;
        }
    }

    private void interactObject(int slot) {
        if (slot != -1) {
            switch(gp.obj[slot].name) {
                case "key":
                    gp.vars.keys++;
                    gp.obj[slot] = null;

                    gp.playSound(Audio.GET_ITEM);
                    break;
                case "locked_door":
                    if (gp.vars.keys > 0) {
                        gp.vars.keys--;
                        gp.obj[slot] = null;

                        gp.playSound(Audio.UNLOCK_DOOR);
                    } else {
                        gp.collisionDetector.handleObjectCollision(this, slot);
                    }
                    break;
                case "generic":
                    gp.collisionDetector.handleObjectCollision(this, slot);
            }
        }
    }
}
