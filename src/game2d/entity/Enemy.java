package game2d.entity;

import game2d.main.GamePanel;
import game2d.util.Audio;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Entity{

    boolean moving = false;

    public Enemy(GamePanel gp) {
        super(gp);

        name = "slime";

        speed = 3;
        maxHealth = 10;
        health = maxHealth;

        collisionBox = setBox(2, 7, 12, 9);
    }

    @Override
    public void getTextures() {

        up[0] = setup("enemy/slime0");
        up[1] = setup("enemy/slime1");
        up[2] = setup("enemy/slime2");

        right = up;
        left = up;
        down = up;
    }

    @Override
    public void setAction() {
        Random rand = new Random();
        int i = rand.nextInt(100) + 1;

        if (collisionAbove || collisionBelow || collisionLeft || collisionRight) {
            actionCounter = 0;
        }

        if (actionCounter <= 0) {

            if (i % 4 == 0) {
                direction = "up";
            } else if (i % 4 == 1) {
                direction = "down";
            } else if (i % 4 == 2) {
                direction = "left";
            } else {
                direction = "right";
            }
            actionCounter = rand.nextInt(120) + 120;
        } else {
            actionCounter--;
        }
    }

    @Override
    public void update() {
        setAction();
        updateAnimations();

        // MOVEMENT
        moving = true;
        switch (direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
        }

        // COLLISION
        interactObject(
                gp.collisionDetector.checkObject(this, false)
        );
        gp.collisionDetector.checkTile(this);
    }

    private void updateAnimations() {
        if (moving) {
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

    private void interactObject(int slot) {
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
}
