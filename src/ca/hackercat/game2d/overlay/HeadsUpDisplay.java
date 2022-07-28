package ca.hackercat.game2d.overlay;

import ca.hackercat.game2d.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class HeadsUpDisplay extends Overlay {
    GamePanel gp;
    Font font;

    public HeadsUpDisplay(GamePanel gp) {
        this.gp = gp;

        getResources();
    }

    public void getResources() {
        //try {
        //    font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/misc/font.ttf")).deriveFont(5f);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //    System.exit(1000);
        //} catch (FontFormatException e) {
        //    e.printStackTrace();
        //    System.exit(1001);
        //}
        font = new Font("Arial", Font.PLAIN, 10);
    }

    public void draw(Graphics2D g2) {
        if (gp.inputHandler.hudToggled) {
            g2.setColor(Color.WHITE);
            g2.setFont(this.font);
            int x = 3;
            int y = 10;
            g2.drawString((gp.currentFps + " FPS"), x, y);
            y += 10;
            g2.drawString(("Frame draw time (ms): " + ((double) gp.drawTime[4] / 1E+6)), x, y);
            y += 10;
            g2.drawString(("Tile draw time (ms): " + ((double) gp.drawTime[0] / 1E+6)), x, y);
            y += 10;
            g2.drawString(("Sprite draw time (ms): " + ((double) gp.drawTime[1] / 1E+6)), x, y);
            y += 10;
            g2.drawString(("Overlay draw time (ms): " + ((double) gp.drawTime[2] / 1E+6)), x, y);
            y += 10;
            g2.drawString(("HUD draw time (ms): " + ((double) gp.drawTime[3] / 1E+6)), x, y);
            y += 10;
            g2.drawString(("Screen x y: " + gp.player.screenX + " " + gp.player.screenY), x, y);
            y += 10;
            g2.drawString(("World x y: " + gp.player.worldX + " " + gp.player.worldY), x, y);
            y += 10;
            g2.drawString(("Colliding: " + gp.player.colliding), x, y);
            y += 10;
            g2.drawString(("Slow: " + gp.player.slowed), x, y);
            y += 10;
            g2.drawString(("Flickering: " + gp.player.flickering), x, y);
            y += 10;
            g2.drawString(("Sprite index: " + gp.player.spriteNum), x, y);

            //System.out.println("Tile draw time (ms): " + ((double) gp.drawTime[0] / 1E+6));
            //System.out.println("Sprite draw time (ms): " + ((double) gp.drawTime[1] / 1E+6));
            //System.out.println("Overlay draw time (ms): " + ((double) gp.drawTime[2] / 1E+6));
            //System.out.println("HUD draw time (ms): " + ((double) gp.drawTime[3] / 1E+6));
        }
    }
}