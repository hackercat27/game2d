package ca.hackercat.game2d.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage up0, up1, up2, down0, down1, down2, left0, left1, left2, right0,  right1, right2;
    public BufferedImage collisionTexture, hitboxTexture;
    public String direction;

    public boolean visible;
    public boolean flickering;

    public int animationCounter = 0;
    public int spriteNum = 1;
    public Rectangle collisionBox;
    public Rectangle hitBox;
    public boolean colliding = false;
    public boolean slowed = false;
    public boolean inWater = false;
}
