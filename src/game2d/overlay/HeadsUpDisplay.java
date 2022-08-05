package game2d.overlay;

import game2d.main.Assets;
import game2d.main.GamePanel;

import java.awt.*;

public class HeadsUpDisplay extends Overlay {
    GamePanel gp;
    Font font;

    // IMAGE IDS

    static final int PAUSE_SCREEN = 0;
    static final int STAMINA_BAR = 1;
    static final int STAMINA_BAR_BACKGROUND = 2;
    static final int HEALTH_BAR = 3;
    static final int HEALTH_BAR_BACKGROUND = 4;
    static final int KEY_COUNT_ICON = 5;
    static final int DIALOGUE_BOX = 6;

    public HeadsUpDisplay(GamePanel gp) {
        this.gp = gp;

        getResources();
    }

    public void getResources() {
        font = new Font("Arial", Font.PLAIN, 20);

        image[PAUSE_SCREEN] = setup("gui/pause_screen");
        image[STAMINA_BAR] = setup("gui/stamina_bar");
        image[STAMINA_BAR_BACKGROUND] = setup("gui/stamina_bar_background");
        image[HEALTH_BAR] = setup("gui/health_bar");
        image[HEALTH_BAR_BACKGROUND] = setup("gui/health_bar_background");
        image[KEY_COUNT_ICON] = setup("gui/key");

        image[DIALOGUE_BOX] = setup("gui/dialogue_box");
    }

    public void draw(Graphics2D g2) {
        drawNormalHud(g2);
        if (gp.currentGameState == GamePanel.PLAY_STATE) {
            // DEBUG HUD
            if (gp.inputHandler.hudToggled) {
                drawDebugHud(g2);
            }
        }
        if (gp.currentGameState == GamePanel.PAUSE_STATE) {
            drawPauseScreen(g2);
        }
        if (gp.currentGameState == GamePanel.DIALOGUE_STATE) {
            drawDialogue(g2);
        }
    }

    // DRAWING STUFF
    private void drawDialogue(Graphics2D g2) {
        if (gp.player.talking != -1) {
            g2.drawImage(image[DIALOGUE_BOX], 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, null);
            g2.setColor(Assets.COLOR_TEXT);
            g2.drawString(gp.obj[gp.player.talking].dialogue, 10, -10);
        } else {
            gp.currentGameState = GamePanel.PLAY_STATE;
        }
    }

    private void drawNormalHud(Graphics2D g2) {
        int x = 40;
        int y = 40;

        int barWidth = 300;
        int barHeight = 20;

        g2.drawImage(image[HEALTH_BAR_BACKGROUND], x - 10, y - 10, barWidth + 20, barHeight + 20, null);
        g2.drawImage(image[HEALTH_BAR], x, y, (int) ((double) gp.player.health / ((double) gp.player.maxHealth / barWidth)), 20, null);
        y += 40;
        g2.drawImage(image[STAMINA_BAR_BACKGROUND], x - 10, y - 10, barWidth + 20, barHeight + 20, null);
        g2.drawImage(image[STAMINA_BAR], x, y,(int) ((double) gp.player.stamina / ((double) gp.player.maxStamina / barWidth)), 20, null);
        y -= 40;

        x += barWidth;
        x += 50;

        g2.setFont(this.font);
        g2.setColor(Assets.COLOR_TEXT);
        g2.drawImage(image[KEY_COUNT_ICON], x, y, GamePanel.SCALED_TILE_SIZE, GamePanel.SCALED_TILE_SIZE, null);
        g2.drawString("x" + gp.vars.keys, x + (GamePanel.SCALED_TILE_SIZE / 2) + 10, y + 30);
    }

    private void drawPauseScreen(Graphics2D g2) {
        g2.drawImage(image[PAUSE_SCREEN], 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, null);
    }

    private void drawDebugHud(Graphics2D g2) {
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