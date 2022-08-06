package game2d.entity.player;

import game2d.entity.Player;
import game2d.main.GamePanel;
import game2d.main.InputHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Protogen extends Player {

    public Protogen(GamePanel gp, InputHandler inputHandler) {
        super(gp, inputHandler);

        collisionBox = setBox(3, 6, 10, 10);
    }

    @Override
    public void getTextures() {
        up = new BufferedImage[3];
        down = new BufferedImage[3];
        left = new BufferedImage[3];
        right = new BufferedImage[3];

        up[0] = setup("player/protogen/up0");
        up[1] = setup("player/protogen/up1");
        up[2] = setup("player/protogen/up2");

        left[0] = setup("player/protogen/left0");
        left[1] = setup("player/protogen/left1");
        left[2] = setup("player/protogen/left2");

        down[0] = setup("player/protogen/down0");
        down[1] = setup("player/protogen/down1");
        down[2] = setup("player/protogen/down2");

        right[0] = setup("player/protogen/right0");
        right[1] = setup("player/protogen/right1");
        right[2] = setup("player/protogen/right2");
    }
}
