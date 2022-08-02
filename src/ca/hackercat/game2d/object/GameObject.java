package ca.hackercat.game2d.object;

import ca.hackercat.game2d.main.GamePanel;

import java.awt.image.BufferedImage;

public class GameObject {
    GamePanel gp;
    public BufferedImage image;
    public boolean collision = false;
    public int worldX, worldY;
    public String name;
}
