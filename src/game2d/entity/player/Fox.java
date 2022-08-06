package game2d.entity.player;

import game2d.entity.Player;
import game2d.main.GamePanel;
import game2d.main.InputHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fox extends Player {
    public Fox(GamePanel gp, InputHandler inputHandler) {
        super(gp, inputHandler);

        collisionBox = setBox(3, 6, 10, 10);
    }

    @Override
    public void getTextures() {
        up[0] = setup("player/fox/up0");
        up[1] = setup("player/fox/up1");
        up[2] = setup("player/fox/up2");

        left[0] = setup("player/fox/left0");
        left[1] = setup("player/fox/left1");
        left[2] = setup("player/fox/left2");

        down[0] = setup("player/fox/down0");
        down[1] = setup("player/fox/down1");
        down[2] = setup("player/fox/down2");

        right[0] = setup("player/fox/right0");
        right[1] = setup("player/fox/right1");
        right[2] = setup("player/fox/right2");
    }
}
