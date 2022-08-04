package game2d.main;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Assets {
    public static final Color COLOR_COLLISION_BOX = new Color(1.0f, 0.0f, 0.0f, 0.5f);
    public static final Color COLOR_ACTION_BOX = new Color(1.0f, 1.0f, 0.0f, 0.5f);
    public static final Color COLOR_TEXT = new Color(1.0f, 1.0f, 1.0f);
    public static final Font GAME_FONT;
    static Font font;

    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("/misc/font.ttf")).deriveFont(Font.PLAIN, 20);
        } catch (NullPointerException | FontFormatException | IOException e) {
            e.printStackTrace();
            font = new Font("Arial", Font.PLAIN, 20);
        }
        GAME_FONT = font;
    }
}