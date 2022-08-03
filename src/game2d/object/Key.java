package game2d.object;

import game2d.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Key extends GameObject {
    public Key(GamePanel gp) {
        super(gp);
        name = "key";

        collisionBox = new Rectangle();
        collisionBox.x = 3 * gp.SCALE_FACTOR;
        collisionBox.y = 6 * gp.SCALE_FACTOR;
        collisionBox.width = 10 * gp.SCALE_FACTOR;
        collisionBox.height = 8 * gp.SCALE_FACTOR;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/textures/object/key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
