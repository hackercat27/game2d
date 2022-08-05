package game2d.entity.object;

import game2d.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DoorLocked extends GameObject {
    public DoorLocked(GamePanel gp) {
        super(gp);
        name = "door_locked";

        collisionBox = new Rectangle();
        collisionBox.x = 0;
        collisionBox.y = 0;
        collisionBox.width = 16 * GamePanel.SCALE_FACTOR;
        collisionBox.height = 16 * GamePanel.SCALE_FACTOR;

        down = new BufferedImage[1];

        down[0] = setup("door_locked");
    }
}
