package game2d.entity.object;

import game2d.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Key extends GameObject {
    public Key(GamePanel gp) {
        super(gp);
        name = "key";

        collisionBox = new Rectangle();
        collisionBox.x = 3 * GamePanel.SCALE_FACTOR;
        collisionBox.y = 6 * GamePanel.SCALE_FACTOR;
        collisionBox.width = 10 * GamePanel.SCALE_FACTOR;
        collisionBox.height = 8 * GamePanel.SCALE_FACTOR;

        down = new BufferedImage[1];

        down[0] = setup("key");
    }
}
