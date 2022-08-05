package game2d.entity.object;

import game2d.main.GamePanel;
import game2d.util.Audio;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Door extends GameObject {

    public Door(GamePanel gp) {
        super(gp);
        name = "door";

        collisionBox = setBox(0, 0, 16,16);

        down[0] = setup("door");
        down[1] = setup("door_open");
    }

    @Override
    public void update() {
        if (open) {
            sprite = 1;
            name = "door_open";
            if (closeTimer <= 0) {
                open = false;
                gp.playSound(Audio.DOOR_CLOSE);
            } else {
                closeTimer--;
            }
        } else {
            sprite = 0;
            name = "door";
        }
    }
}
