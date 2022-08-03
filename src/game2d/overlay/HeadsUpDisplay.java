package game2d.overlay;

import game2d.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class HeadsUpDisplay extends Overlay {
    GamePanel gp;
    Font font;

    // IMAGE IDS

    final int PAUSE_SCREEN = 0;

    public HeadsUpDisplay(GamePanel gp) {
        this.gp = gp;

        getResources();
    }

    public void getResources() {
        font = new Font("Arial", Font.PLAIN, 20);

        try {
            image[PAUSE_SCREEN] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/gui/pause_screen.png")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        }
    }

    public void draw(Graphics2D g2) {
        if (gp.currentGameState == gp.PLAY_STATE) {

            // DEBUG HUD
            if (gp.inputHandler.hudToggled) {
                drawDebugHud(g2);
            }

        } else if (gp.currentGameState == gp.PAUSE_STATE) {
            drawPauseScreen(g2);
        }
    }

    // DRAWING STUFF

    public void drawPauseScreen(Graphics2D g2) {
        g2.drawImage(image[PAUSE_SCREEN], 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null);
    }

    public void drawDebugHud(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(this.font);
        x = 15;
        y = 25;
        g2.drawString((gp.currentFps + " FPS"), x, y);
        y += 25;
        g2.drawString(("Camera x y: " + gp.tileManager.cameraWorldX + " " + gp.tileManager.cameraWorldY), x, y);
        y += 25;
        g2.drawString(("Player x y: " + gp.player.worldX + " " + gp.player.worldY), x, y);
        y += 25;
        g2.drawString(("collision udlr: " + gp.player.collisionAbove + " " + gp.player.collisionBelow + " " + gp.player.collisionLeft + " " + gp.player.collisionRight), x, y);
    }
}