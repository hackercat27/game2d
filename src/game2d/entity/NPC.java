package game2d.entity;

import game2d.main.Colors;
import game2d.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC extends Entity {

    public int screenX, screenY;

    public NPC(GamePanel gp) {
        super(gp);

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
        worldX = gp.SCALED_TILE_SIZE * 8;
        worldY = gp.SCALED_TILE_SIZE * 4;
        speed = 5;
        direction = "down";
    }

    public void getTextures() {
        up = new BufferedImage[3];
        down = new BufferedImage[3];
        left = new BufferedImage[2];
        right = new BufferedImage[2];

        up[0] = setup("/npc/up0");
        up[1] = setup("/npc/up1");
        up[2] = setup("/npc/up2");
        left[0] = setup("/npc/left0");
        left[1] = setup("/npc/left1");
        down[0] = setup("/npc/down0");
        down[1] = setup("/npc/down1");
        down[2] = setup("/npc/down2");
        right[0] = setup("/npc/right0");
        right[1] = setup("/npc/right1");
    }

    public void update() {
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
        if (gp.inputHandler.hudToggled) {
            g2.setColor(Colors.COLLISION_BOX);
            collisionBox.x += worldX - gp.tileManager.cameraWorldX;
            collisionBox.y += worldY - gp.tileManager.cameraWorldY;
            g2.fill(collisionBox);
            collisionBox.x -= worldX - gp.tileManager.cameraWorldX;
            collisionBox.y -= worldY - gp.tileManager.cameraWorldY;
        }
    }
}
