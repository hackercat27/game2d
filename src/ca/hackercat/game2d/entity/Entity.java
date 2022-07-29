package ca.hackercat.game2d.entity;

import ca.hackercat.game2d.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage[] up, down, left, right;
    public String direction;

    public boolean visible;
    public boolean flickering;

    public int animationCounter = 0;
    public int spriteNum = 1;
    public Rectangle collisionBox;
    public boolean collisionAbove, collisionBelow, collisionLeft, collisionRight;

    GamePanel gp;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
}
