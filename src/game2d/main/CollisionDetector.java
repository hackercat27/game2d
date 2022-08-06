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

        Rectangle objBox = new Rectangle(gp.obj[slot].collisionBox.x + gp.obj[slot].worldX, gp.obj[slot].collisionBox.y + gp.obj[slot].worldY, gp.obj[slot].collisionBox.width, gp.obj[slot].collisionBox.height);
        Rectangle entityBox = new Rectangle(entity.collisionBox.x + entity.worldX, entity.collisionBox.y + entity.worldY, entity.collisionBox.width, entity.collisionBox.height);

        int xDiff = (int) (objBox.getCenterX() - entityBox.getCenterX());
        int yDiff = (int) (objBox.getCenterY() - entityBox.getCenterY());

        int xDiffUnsigned = (int) Math.copySign(xDiff, 1.0);
        int yDiffUnsigned = (int) Math.copySign(yDiff, 1.0);

        if (yDiffUnsigned > xDiffUnsigned) { //if true, then the point is closer to the y intercept than the x intercept, so the change happens on the y-axis
            if (yDiff > 0) { //if true, the number is positive therefor the entity is below the object
                if (gp.obj[slot].snapCollision) {
                    entity.worldY += (gp.obj[slot].worldY + gp.obj[slot].collisionBox.y) - ((entity.collisionBox.y + entity.worldY) + entity.collisionBox.height) - bonusOffset;
                } else {
                    entity.worldY -= entity.speed;
                }
            } else { //else, the number is negative (or 0) therefor the entity is above the object
                if (gp.obj[slot].snapCollision) {
                    entity.worldY += (gp.obj[slot].worldY + gp.obj[slot].collisionBox.y + gp.obj[slot].collisionBox.height) - (entity.collisionBox.y + entity.worldY) + bonusOffset;
                } else {
                    entity.worldY += entity.speed;
                }
            }
        } else { //else, the point is closer to the x intercept than the y intercept, so the change happens on the x-axis
            if (xDiff > 0) { //if true, the number is positive therefor the entity is to the right of the object
                if (gp.obj[slot].snapCollision) {
                    entity.worldX += (gp.obj[slot].worldX + gp.obj[slot].collisionBox.x) - ((entity.collisionBox.x + entity.worldX) + entity.collisionBox.width) - bonusOffset;
                } else {
                    entity.worldX -= entity.speed;
                }
            } else { //else, the number is negative (or 0) therefor the entity is to the left of the object
                if (gp.obj[slot].snapCollision) {
                    entity.worldX += (gp.obj[slot].worldX + gp.obj[slot].collisionBox.x + gp.obj[slot].collisionBox.width) - (entity.collisionBox.x + entity.worldX) + bonusOffset;
                } else {
                    entity.worldX += entity.speed;
                }
            }
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

    private void handleTileCollision(Entity entity, Rectangle entityBox, Rectangle tile, int col, int row) {
        tile.x = col * GamePanel.SCALED_TILE_SIZE;
        tile.y = row * GamePanel.SCALED_TILE_SIZE;

        int bonusOffset = 1;

        int tileIndex = gp.tileManager.mapTilePos[col][row];

        if (gp.tileManager.tile[tileIndex].collision && gp.inputHandler.hudToggled) {
            gp.g2.setColor(Assets.COLOR_TILE_COLLISION);
            gp.g2.fillRect(tile.x - gp.tileManager.cameraWorldX, tile.y - gp.tileManager.cameraWorldY, tile.width, tile.height);
        }
        if (entityBox.intersects(tile) && gp.tileManager.tile[tileIndex].collision) {
            entity.colliding = true;


            int xDiff = (int) (tile.getCenterX() - entityBox.getCenterX());
            int yDiff = (int) (tile.getCenterY() - entityBox.getCenterY());

            int xDiffUnsigned = (int) Math.copySign(xDiff, 1.0);
            int yDiffUnsigned = (int) Math.copySign(yDiff, 1.0);


            if (yDiffUnsigned > xDiffUnsigned) { //if true, then the point is closer to the y intercept than the x intercept, so the change happens on the y-axis
                if (yDiff > 0) { //if true, the number is positive therefor the entity is below the tile
                    entity.worldY += tile.y - ((entity.collisionBox.y + entity.worldY) + entity.collisionBox.height) - bonusOffset;
                } else { //else, the number is negative (or 0) therefor the entity is above the tile
                    entity.worldY += (tile.y + tile.height) - (entity.collisionBox.y + entity.worldY) + bonusOffset;
                }
            } else { //else, the point is closer to the x intercept than the y intercept, so the change happens on the x-axis
                if (xDiff > 0) { //if true, the number is positive therefor the entity is to the right of the tile
                    entity.worldX += tile.x - ((entity.collisionBox.x + entity.worldX) + entity.collisionBox.width) - bonusOffset;
                } else { //else, the number is negative (or 0) therefor the entity is to the left of the tile
                    entity.worldX += (tile.x + tile.width) - (entity.collisionBox.x + entity.worldX) + bonusOffset;
                }
            }
        }
    }

    public void checkTile(Entity entity) {
        Rectangle tile = new Rectangle(0, 0, GamePanel.SCALED_TILE_SIZE, GamePanel.SCALED_TILE_SIZE);
        Rectangle entityBox = new Rectangle(entity.collisionBox.x + entity.worldX, entity.collisionBox.y + entity.worldY, entity.collisionBox.width, entity.collisionBox.height);

        int entityWorldCol = entity.worldX / GamePanel.SCALED_TILE_SIZE;
        int entityWorldRow = entity.worldY / GamePanel.SCALED_TILE_SIZE;

        int range = 1;

        int worldCol = entityWorldCol - range + 1;
        int worldRow = entityWorldRow - range + 1;


        entity.colliding = false;

        while (worldRow <= entityWorldRow + range + 1) {
            try {
                handleTileCollision(entity, entityBox, tile, worldCol, worldRow);
            } catch (Exception e) {
                //empty catch block yayyy
            }
            worldCol++;
            if (worldCol > entityWorldCol + range + 1) {
                worldCol = entityWorldCol - range + 1;
                worldRow++;
            }
        }
    }
}
