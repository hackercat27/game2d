package game2d.main;


import game2d.entity.Enemy;
import game2d.entity.Entity;
import game2d.entity.NPC;
import game2d.entity.object.Door;
import game2d.entity.object.Key;
import game2d.entity.object.DoorLocked;
import game2d.entity.player.Fox;
import game2d.entity.player.Protogen;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    private void load(Entity obj, int x, int y) {
        load(obj, x, y, "down");
    }

    private void load(Entity obj, int x, int y, String direction) {
        for (int slot = 0; slot < gp.obj.length; slot++) {
            if (gp.obj[slot] == null) {
                gp.obj[slot] = obj;
                gp.obj[slot].worldX = x * GamePanel.SCALED_TILE_SIZE;
                gp.obj[slot].worldY = y * GamePanel.SCALED_TILE_SIZE;
                gp.obj[slot].direction = direction;
                break;
            }
        }
        System.err.println(obj.name + " tried to load, but ran out of object slots");
    }

    public void setObject() {
        load(new Key(gp), 7, 10);
        load(new Key(gp), 24, 3);
        load(new DoorLocked(gp), 15, 5);
        load(new DoorLocked(gp), 15, 3);
        load(new DoorLocked(gp), 24, 4);
        load(new Door(gp), 10, 9);
    }

    public void setEnemy() {
        load(new Enemy(gp), 26, 7);
        load(new Enemy(gp), 25, 7);
        load(new Enemy(gp), 24, 7);
    }

    public void setNPC() {
        load(new NPC(gp), 15, 7);
    }

    public void setPlayer() {
        gp.player = new Protogen(gp, gp.inputHandler);
        gp.player.worldX = 6 * GamePanel.SCALED_TILE_SIZE;
        gp.player.worldY = 2 * GamePanel.SCALED_TILE_SIZE;
        gp.player.direction = "up";
    }
}
