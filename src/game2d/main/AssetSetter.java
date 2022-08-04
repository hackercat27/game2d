package game2d.main;


import game2d.entity.NPC;
import game2d.entity.object.Key;
import game2d.entity.object.LockedDoor;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new Key(gp);
        gp.obj[0].worldX = 10 * GamePanel.SCALED_TILE_SIZE;
        gp.obj[0].worldY = 11 * GamePanel.SCALED_TILE_SIZE;

        gp.obj[1] = new LockedDoor(gp);
        gp.obj[1].worldX = 15 * GamePanel.SCALED_TILE_SIZE;
        gp.obj[1].worldY = 5 * GamePanel.SCALED_TILE_SIZE;
    }

    public void setNPC() {
        gp.obj[2] = new NPC(gp);
        gp.obj[2].worldX = 15 * GamePanel.SCALED_TILE_SIZE;
        gp.obj[2].worldY = 7 * GamePanel.SCALED_TILE_SIZE;
    }
}
