package game2d.overlay;

import game2d.main.GamePanel;
import game2d.tile.Tile;
import game2d.util.ToolBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ScreenEffects extends Overlay {
    GamePanel gp;

    // IMAGE IDS

    final int VIGNETTE = 0;
    final int LOW_HEALTH_VIGNETTE = 1;

    public ScreenEffects(GamePanel gp) {
        this.gp = gp;

        getTextures();
    }

    public void getTextures() {
        setup(VIGNETTE, "vignette");
        setup(LOW_HEALTH_VIGNETTE, "low_health_vignette");
    }

    public void setup(int index, String imagePath) {
        ToolBox util = new ToolBox();
        try {
            image[index] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/overlay/" + imagePath + ".png")));
            image[index] = util.scaleImage(image[index], gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        }
    }

    public void draw(Graphics2D g2) {
        if (gp.inputHandler.vignetteToggled) {
            g2.drawImage(image[VIGNETTE], 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null);
        }
        int offset = gp.player.health * gp.SCALE_FACTOR;
        //g2.drawImage(image[LOW_HEALTH_VIGNETTE], -offset, -offset, gp.SCREEN_WIDTH + (2 * offset), gp.SCREEN_HEIGHT + (2 * offset), null);
    }
}
