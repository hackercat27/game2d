package game2d.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Assets {
    public static final Color COLOR_COLLISION_BOX = new Color(1.0f, 0.0f, 0.0f, 0.5f);
    public static final Color COLOR_ACTION_BOX = new Color(1.0f, 1.0f, 0.0f, 0.5f);
    public static final Color COLOR_TILE_COLLISION = new Color(0.0f, 1.0f, 1.0f, 0.5f);
    public static final Color COLOR_TEXT = new Color(1.0f, 1.0f, 1.0f);
    public static final Color MAGENTA_50 = new Color(1.0f, 0.0f, 1.0f, 0.5f);
    public static final Color BLACK_50 = new Color(1.0f, 0.0f, 1.0f, 0.5f);
    public static final Font GAME_FONT;
    public static final BufferedImage MISSING_TEXTURE;
    public static final BufferedImage MISSING_TEXTURE_TRANSPARENT;
    static Font font;

    static {

        MISSING_TEXTURE = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        MISSING_TEXTURE_TRANSPARENT = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = MISSING_TEXTURE.createGraphics();
        g2.setColor(Color.MAGENTA);
        g2.fillRect(0, 0, 16, 16);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 8, 8);
        g2.fillRect(8, 8, 8, 8);

        g2 = MISSING_TEXTURE_TRANSPARENT.createGraphics();
        g2.setColor(MAGENTA_50);
        g2.fillRect(0, 8, 8, 8);
        g2.fillRect(8, 0, 8, 8);
        g2.setColor(BLACK_50);
        g2.fillRect(0, 0, 8, 8);
        g2.fillRect(8, 8, 8, 8);

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("/misc/font.ttf")).deriveFont(Font.PLAIN, 20);
        } catch (NullPointerException | FontFormatException | IOException e) {
            e.printStackTrace();
            String fontName = "Arial";
            System.err.println("\nUsing font \"" + fontName + "\" instead.");
            font = new Font(fontName, Font.PLAIN, 20);
        } finally {
            GAME_FONT = font;
        }
    }
}