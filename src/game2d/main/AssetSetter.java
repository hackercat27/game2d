package game2d.main;


import game2d.entity.NPC;
import game2d.object.Key;

import java.awt.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new Key(gp);
        gp.obj[0].worldX = 10 * gp.SCALED_TILE_SIZE;
        gp.obj[0].worldY = 11 * gp.SCALED_TILE_SIZE;
    }

    public void setNPC() {
        gp.npc[0] = new NPC(gp);
    }
}
