package ca.hackercat.game2d.main;

import ca.hackercat.game2d.entity.Entity;

public class CollisionDetector {
    GamePanel gp;
    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.collisionBox.x;
        int entityRightWorldX = entity.worldX + entity.collisionBox.x + entity.collisionBox.width;
        int entityTopWorldY = entity.worldY + entity.collisionBox.y;
        int entityBottomWorldY = entity.worldY + entity.collisionBox.y + entity.collisionBox.height;

        int entityLeftCol = entityLeftWorldX / gp.SCALED_TILE_SIZE;
        int entityRightCol = entityRightWorldX / gp.SCALED_TILE_SIZE;
        int entityTopRow = entityTopWorldY / gp.SCALED_TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / gp.SCALED_TILE_SIZE;

        int tileNum1, tileNum2;
        int entityTileOn;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.SCALED_TILE_SIZE;
                tileNum1 = gp.tileManager.mapTilePos[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTilePos[entityRightCol][entityTopRow];

                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.colliding = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.SCALED_TILE_SIZE;
                tileNum1 = gp.tileManager.mapTilePos[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTilePos[entityRightCol][entityBottomRow];

                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.colliding = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.SCALED_TILE_SIZE;
                tileNum1 = gp.tileManager.mapTilePos[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTilePos[entityLeftCol][entityBottomRow];

                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.colliding = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.SCALED_TILE_SIZE;
                tileNum1 = gp.tileManager.mapTilePos[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTilePos[entityRightCol][entityBottomRow];

                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.colliding = true;
                }
            }
        }

        entityTileOn = gp.tileManager.mapTilePos[(entity.worldX + (gp.SCALED_TILE_SIZE / 2)) / gp.SCALED_TILE_SIZE][(entity.worldY + (gp.SCALED_TILE_SIZE / 2)) / gp.SCALED_TILE_SIZE];

        if (gp.tileManager.tile[entityTileOn].slowTile) {
            entity.speed /= 2;
            entity.slowed = true;
        }
        if (gp.tileManager.tile[entityTileOn].waterTile) {
            entity.inWater = true;
        }

        //if (gp.tileManager.tile[tileNum1].slowTile || gp.tileManager.tile[tileNum2].slowTile) {
        //    entity.speed /= 2;
        //}
        //if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
        //    entity.colliding = true;
        //}
    }
}
