package game2d.entity.player;

import game2d.entity.Player;
import game2d.main.GamePanel;
import game2d.main.InputHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fox extends Player {

    public Fox(GamePanel gp, InputHandler inputHandler) {
        super(gp, inputHandler);

        collisionBox = new Rectangle();
        collisionBox.x = 3 * GamePanel.SCALE_FACTOR;
        collisionBox.y = 6 * GamePanel.SCALE_FACTOR;
        collisionBox.width = 10 * GamePanel.SCALE_FACTOR;
        collisionBox.height = 10 * GamePanel.SCALE_FACTOR;
    }

    @Override
    public void getTextures() {
        up = new BufferedImage[3];
        down = new BufferedImage[3];
        left = new BufferedImage[3];
        right = new BufferedImage[3];

        up[0] = setup("/player/fox/up0");
        up[1] = setup("/player/fox/up1");
        up[2] = setup("/player/fox/up2");

        left[0] = setup("/player/fox/left0");
        left[1] = setup("/player/fox/left1");
        left[2] = setup("/player/fox/left2");

        down[0] = setup("/player/fox/down0");
        down[1] = setup("/player/fox/down1");
        down[2] = setup("/player/fox/down2");

        right[0] = setup("/player/fox/right0");
        right[1] = setup("/player/fox/right1");
        right[2] = setup("/player/fox/right2");
    }
}
