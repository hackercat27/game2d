package game2d.entity.object;

import game2d.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LockedDoor extends GameObject {
    public LockedDoor(GamePanel gp) {
        super(gp);
        name = "locked_door";

        collisionBox = new Rectangle();
        collisionBox.x = 0;
        collisionBox.y = 0;
        collisionBox.width = 16 * GamePanel.SCALE_FACTOR;
        collisionBox.height = 16 * GamePanel.SCALE_FACTOR;

        down = new BufferedImage[1];

        down[0] = setup("locked_door");
    }
}
