package game2d.overlay;

import game2d.main.GamePanel;

import java.awt.*;

public class Background extends Overlay {
    GamePanel gp;

    // IMAGE IDS

    final static int SKY = 0;

    public Background(GamePanel gp) {
        this.gp = gp;

        getTextures();
    }

    public void getTextures() {
        image[SKY] = setup("background/sky");
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(image[SKY], 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, null);
    }
}
