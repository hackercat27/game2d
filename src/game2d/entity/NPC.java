package game2d.entity;

import game2d.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC extends Entity {

    public int screenX, screenY;
    public int actionCounter = 0;
    boolean moving = false;


    public NPC(GamePanel gp) {
        super(gp);

        collisionBox = new Rectangle();
        collisionBox.x = 3 * GamePanel.SCALE_FACTOR;
        collisionBox.y = 6 * GamePanel.SCALE_FACTOR;
        collisionBox.width = 10 * GamePanel.SCALE_FACTOR;
        collisionBox.height = 10 * GamePanel.SCALE_FACTOR;

        actionBox = new Rectangle();
        actionBox.x = -4 * GamePanel.SCALE_FACTOR;
        actionBox.y = -4 * GamePanel.SCALE_FACTOR;
        actionBox.width = 24 * GamePanel.SCALE_FACTOR;
        actionBox.height = 24 * GamePanel.SCALE_FACTOR;

        screenX = GamePanel.SCREEN_WIDTH / 2 - GamePanel.SCALED_TILE_SIZE / 2;
        screenY = GamePanel.SCREEN_HEIGHT / 2 - GamePanel.SCALED_TILE_SIZE / 2;

        setDefaultValues();
        getTextures();
    }

    @Override
    public void setDefaultValues() {
        worldX = GamePanel.SCALED_TILE_SIZE * 8;
        worldY = GamePanel.SCALED_TILE_SIZE * 4;
        speed = 4;
        direction = "down";
    }

    public void getTextures() {
        up = new BufferedImage[3];
        down = new BufferedImage[3];
        left = new BufferedImage[3];
        right = new BufferedImage[3];

        up[0] = setup("/npc/up0");
        up[1] = setup("/npc/up1");
        up[2] = setup("/npc/up2");
        left[0] = setup("/npc/left0");
        left[1] = setup("/npc/left1");
        left[2] = setup("/npc/left2");
        down[0] = setup("/npc/down0");
        down[1] = setup("/npc/down1");
        down[2] = setup("/npc/down2");
        right[0] = setup("/npc/right0");
        right[1] = setup("/npc/right1");
        right[2] = setup("/npc/right2");
    }

    @Override
    public void setAction() {
        Random rand = new Random();
        int i = rand.nextInt(100) + 1;

        if ((collisionAbove && !collisionBelow) ||
                (collisionBelow && !collisionAbove) ||
                (collisionRight && !collisionLeft) ||
                (collisionLeft && !collisionRight)) {
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
                case "locked_door":
                case "generic":
                    gp.collisionDetector.handleObjectCollision(this, slot);
            }
        }
    }
}
