package game2d.entity.player;

import game2d.entity.Player;
import game2d.main.GamePanel;
import game2d.main.InputHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Protogen extends Player {

    public Protogen(GamePanel gp, InputHandler inputHandler) {
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

        up[0] = setup("/player/protogen/up0");
        up[1] = setup("/player/protogen/up1");
        up[2] = setup("/player/protogen/up2");

        left[0] = setup("/player/protogen/left0");
        left[1] = setup("/player/protogen/left1");
        left[2] = setup("/player/protogen/left2");

        down[0] = setup("/player/protogen/down0");
        down[1] = setup("/player/protogen/down1");
        down[2] = setup("/player/protogen/down2");

        right[0] = setup("/player/protogen/right0");
        right[1] = setup("/player/protogen/right1");
        right[2] = setup("/player/protogen/right2");
    }
}
