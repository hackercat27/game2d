package game2d.overlay;

import game2d.util.ToolBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Overlay {
    BufferedImage[] image = new BufferedImage[10];

    int x, y;

    public BufferedImage setup(String imagePath) {
        ToolBox util = new ToolBox();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/" + imagePath + ".png")));
            //image = util.scaleImage(image, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        } catch (NullPointerException e) {
            System.err.println("Missing texture \"/textures/" + imagePath + ".png\"!");

            image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            g2.setColor(Color.MAGENTA);
            g2.fillRect(0, 0, 16, 16);
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, 8, 8);
            g2.fillRect(8, 8, 8, 8);
        }
        return image;
    }
}
