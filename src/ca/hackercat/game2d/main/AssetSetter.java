package ca.hackercat.game2d.main;

import ca.hackercat.game2d.entity.npc.ForestVillager;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    public void setObject() {

    }
    public void setNPC() {
        gp.npc[0] = new ForestVillager(gp);
        gp.npc[0].worldX = gp.SCALED_TILE_SIZE * 9;
        gp.npc[0].worldY = gp.SCALED_TILE_SIZE * 9;
    }
}
