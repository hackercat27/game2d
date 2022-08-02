package ca.hackercat.game2d.entity.npc;

import ca.hackercat.game2d.entity.Entity;
import ca.hackercat.game2d.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC extends Entity {

    public NPC(GamePanel gp) {
        super(gp);

        up = new BufferedImage[4];
        down = new BufferedImage[4];
        left = new BufferedImage[4];
        right = new BufferedImage[4];

        direction = "down";
    }

    @Override
    public void setDefaultValues() {
        worldX = gp.SCALED_TILE_SIZE * 6;
        worldY = gp.SCALED_TILE_SIZE * 2;
        speed = 5;
        direction = "down";
        spriteNum = 0;
    }

    @Override
    public void getTextures() {
        up[0] = setup("/missing");
        left[0] = setup("/missing");
        down[0] = setup("/missing");
        right[0] = setup("/missing");
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> image = up[spriteNum];
            case "down" -> image = down[spriteNum];
            case "left" -> image = left[spriteNum];
            case "right" -> image = right[spriteNum];
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
            g2.setColor(Color.RED);
            collisionBox.x += worldX - gp.tileManager.cameraWorldX;
            collisionBox.y += worldY - gp.tileManager.cameraWorldY;
            g2.draw(collisionBox);
            collisionBox.x -= worldX - gp.tileManager.cameraWorldX;
            collisionBox.y -= worldY - gp.tileManager.cameraWorldY;
        }
    }
}
