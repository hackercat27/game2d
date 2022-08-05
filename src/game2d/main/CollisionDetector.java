package game2d.main;

import game2d.entity.Entity;

import java.awt.*;

public class CollisionDetector {
    GamePanel gp;
    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    public int checkObject(Entity entity, boolean isPlayer) {
        Rectangle objBox = new Rectangle();
        Rectangle entityBox = new Rectangle();

        entityBox.x = entity.collisionBox.x + entity.worldX;
        entityBox.y = entity.collisionBox.y + entity.worldY;
        entityBox.width = entity.collisionBox.width;
        entityBox.height = entity.collisionBox.height;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                objBox.x = gp.obj[i].collisionBox.x + gp.obj[i].worldX;
                objBox.y = gp.obj[i].collisionBox.y + gp.obj[i].worldY;
                objBox.width = gp.obj[i].collisionBox.width;
                objBox.height = gp.obj[i].collisionBox.height;

                if (entityBox.intersects(objBox) && !((entity.worldY == gp.obj[i].worldY) && (entity.worldX == gp.obj[i].worldX))) {
                    return i;
                }
            }
        }

        return -1;
    }

    public void handleObjectCollision(Entity entity, int slot) {
        int bonusOffset = 1;

        //TODO: determine the direction that the player needs to be pushed in some other way than just the direction they're facing, as it leads to buggy behaviour with moving entities

        switch (entity.direction) {
            case "up":
                entity.worldY += (gp.obj[slot].worldY + gp.obj[slot].collisionBox.y + gp.obj[slot].collisionBox.height) - (entity.collisionBox.y + entity.worldY) + bonusOffset;
                break;
            case "down":
                entity.worldY += (gp.obj[slot].worldY + gp.obj[slot].collisionBox.y) - ((entity.collisionBox.y + entity.worldY) + entity.collisionBox.height) - bonusOffset;
                break;
            case "left":
                entity.worldX += (gp.obj[slot].worldX + gp.obj[slot].collisionBox.x + gp.obj[slot].collisionBox.width) - (entity.collisionBox.x + entity.worldX) + bonusOffset;
                break;
            case "right":
                entity.worldX += (gp.obj[slot].worldX + gp.obj[slot].collisionBox.x) - ((entity.collisionBox.x + entity.worldX) + entity.collisionBox.width) - bonusOffset;
                break;
        }
    }


    public int checkInteraction(Entity entity) {
        Rectangle objBox = new Rectangle();
        Rectangle entityBox = new Rectangle();

        entityBox.x = entity.collisionBox.x + entity.worldX;
        entityBox.y = entity.collisionBox.y + entity.worldY;
        entityBox.width = entity.collisionBox.width;
        entityBox.height = entity.collisionBox.height;

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null && gp.obj[i].actionBox != null) {
                objBox.x = gp.obj[i].actionBox.x + gp.obj[i].worldX;
                objBox.y = gp.obj[i].actionBox.y + gp.obj[i].worldY;
                objBox.width = gp.obj[i].actionBox.width;
                objBox.height = gp.obj[i].actionBox.height;
                if (entityBox.intersects(objBox)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.collisionBox.x;
        int entityRightWorldX =  entity.worldX + entity.collisionBox.x + entity.collisionBox.width;
        int entityTopWorldY = entity.worldY + entity.collisionBox.y;
        int entityBottomWorldY = entity.worldY + entity.collisionBox.y + entity.collisionBox.height;

        int entityLeftCol = entityLeftWorldX / GamePanel.SCALED_TILE_SIZE;
        int entityRightCol = entityRightWorldX / GamePanel.SCALED_TILE_SIZE;
        int entityTopRow; //= entityTopWorldY / gp.SCALED_TILE_SIZE;
        int entityBottomRow; //= entityBottomWorldY / gp.SCALED_TILE_SIZE;

        int bonusOffset = 0;

        int tileNum1, tileNum2;

        try {

            //reset collision states
            entity.collisionAbove = false;
            entity.collisionBelow = false;
            entity.collisionLeft = false;
            entity.collisionRight = false;

            entityTopRow = (entityTopWorldY - bonusOffset) / GamePanel.SCALED_TILE_SIZE;
            tileNum1 = gp.tileManager.mapTilePos[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTilePos[entityRightCol][entityTopRow];

            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.collisionAbove = true;
            }

            entityBottomRow = (entityBottomWorldY + bonusOffset) / GamePanel.SCALED_TILE_SIZE;
            tileNum1 = gp.tileManager.mapTilePos[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileManager.mapTilePos[entityRightCol][entityBottomRow];

            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.collisionBelow = true;
            }

            entityLeftCol = (entityLeftWorldX - bonusOffset) / GamePanel.SCALED_TILE_SIZE;
            tileNum1 = gp.tileManager.mapTilePos[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTilePos[entityLeftCol][entityBottomRow];

            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.collisionLeft = true;
            }

            entityRightCol = (entityRightWorldX + bonusOffset) / GamePanel.SCALED_TILE_SIZE;
            tileNum1 = gp.tileManager.mapTilePos[entityRightCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTilePos[entityRightCol][entityBottomRow];

            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.collisionRight = true;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        // HANDLING COLLISION

        bonusOffset = 1;

        if (entity.collisionAbove && !entity.collisionBelow) {
            entity.worldY = ((entity.worldY - (entity.worldY % GamePanel.SCALED_TILE_SIZE)) + GamePanel.SCALED_TILE_SIZE) - entity.collisionBox.y + bonusOffset;
            entity.collisionAbove = false;
        }

        if (entity.collisionLeft && !entity.collisionRight) {
            entity.worldX = ((entity.worldX - (entity.worldX % GamePanel.SCALED_TILE_SIZE)) + GamePanel.SCALED_TILE_SIZE) - entity.collisionBox.x + bonusOffset;
            entity.collisionLeft = false;
        }
        if (entity.collisionRight && !entity.collisionLeft) {
            entity.worldX = (entity.worldX - (entity.worldX % GamePanel.SCALED_TILE_SIZE)) + (GamePanel.SCALED_TILE_SIZE - (entity.collisionBox.x + entity.collisionBox.width)) - bonusOffset;
            entity.collisionRight = false;
        }

        if (entity.collisionBelow && !entity.collisionAbove) {
            entity.worldY = (entity.worldY - (entity.worldY % GamePanel.SCALED_TILE_SIZE)) + (GamePanel.SCALED_TILE_SIZE - (entity.collisionBox.y + entity.collisionBox.height)) - bonusOffset;
            entity.collisionBelow = false;
        }
    }
}
