package ca.hackercat.game2d.main;

import ca.hackercat.game2d.entity.Entity;

public class CollisionDetector {
    GamePanel gp;
    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.collisionBox.x;
        int entityRightWorldX =  entity.worldX + entity.collisionBox.x + entity.collisionBox.width;
        int entityTopWorldY = entity.worldY + entity.collisionBox.y;
        int entityBottomWorldY = entity.worldY + entity.collisionBox.y + entity.collisionBox.height;

        int entityLeftCol = entityLeftWorldX / gp.SCALED_TILE_SIZE;
        int entityRightCol = entityRightWorldX / gp.SCALED_TILE_SIZE;
        int entityTopRow; //= entityTopWorldY / gp.SCALED_TILE_SIZE;
        int entityBottomRow; //= entityBottomWorldY / gp.SCALED_TILE_SIZE;

        int tileNum1, tileNum2;

        try {

            //reset collision states
            entity.collisionAbove = false;
            entity.collisionBelow = false;
            entity.collisionLeft = false;
            entity.collisionRight = false;

            entityTopRow = (entityTopWorldY - entity.speed) / gp.SCALED_TILE_SIZE;
            tileNum1 = gp.tileManager.mapTilePos[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTilePos[entityRightCol][entityTopRow];

            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.collisionAbove = true;
            }

            entityBottomRow = (entityBottomWorldY + entity.speed) / gp.SCALED_TILE_SIZE;
            tileNum1 = gp.tileManager.mapTilePos[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileManager.mapTilePos[entityRightCol][entityBottomRow];

            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.collisionBelow = true;
            }

            entityLeftCol = (entityLeftWorldX - entity.speed) / gp.SCALED_TILE_SIZE;
            tileNum1 = gp.tileManager.mapTilePos[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTilePos[entityLeftCol][entityBottomRow];

            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.collisionLeft = true;
            }

            entityRightCol = (entityRightWorldX + entity.speed) / gp.SCALED_TILE_SIZE;
            tileNum1 = gp.tileManager.mapTilePos[entityRightCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTilePos[entityRightCol][entityBottomRow];

            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.collisionRight = true;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.exit(1003);
        }
    }

    public void handleCollisions(Entity entity) {
        if (entity.collisionAbove && !entity.collisionBelow) {
            entity.worldY = ((entity.worldY - (entity.worldY % gp.SCALED_TILE_SIZE)) + gp.SCALED_TILE_SIZE) - entity.collisionBox.y + 2;
            entity.collisionAbove = false;
            //System.out.println("Handled up collision.");
        }

        if (entity.collisionLeft && !entity.collisionRight) {
            entity.worldX = ((entity.worldX - (entity.worldX % gp.SCALED_TILE_SIZE)) + gp.SCALED_TILE_SIZE) - entity.collisionBox.x + 2;
            entity.collisionLeft = false;
            //System.out.println("Handled leftwards collision.");
        }
        if (entity.collisionRight && !entity.collisionLeft) {
            entity.worldX = (entity.worldX - (entity.worldX % gp.SCALED_TILE_SIZE)) + (gp.SCALED_TILE_SIZE - (entity.collisionBox.x + entity.collisionBox.width)) - 2;
            entity.collisionRight = false;
            //System.out.println("Handled rightwards collision.");
        }

        if (entity.collisionBelow && !entity.collisionAbove) {
            entity.worldY = (entity.worldY - (entity.worldY % gp.SCALED_TILE_SIZE)) + (gp.SCALED_TILE_SIZE - (entity.collisionBox.y + entity.collisionBox.height)) - 2;
            entity.collisionBelow = false;
            //System.out.println("Handled down collision.");
        }
    }
}
