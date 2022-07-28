package ca.hackercat.game2d.overlay;

import ca.hackercat.game2d.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class BigChungus extends Overlay {
    GamePanel gp;

    public BigChungus(GamePanel gp) {
        this.gp = gp;

        getTextures();
    }

    public void getTextures() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/textures/overlay/big_chungus.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        }
    }

    public void draw(Graphics2D g2) {

        if (gp.inputHandler.vignetteToggled) {
            g2.drawImage(image, 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null);
        }
    }

}
