package game2d.entity;

import game2d.main.GamePanel;

import java.util.Random;

public class Enemy extends Entity{

    boolean moving = false;

    public Enemy(GamePanel gp) {
        super(gp);

        name = "slime";

        speed = 3;
        maxHealth = 10;
        health = maxHealth;

        collisionBox = setBox(2, 7, 12, 9);
    }

    @Override
    public void getTextures() {

        up[0] = setup("enemy/slime0");
        up[1] = setup("enemy/slime1");
        up[2] = setup("enemy/slime2");

        right = up;
        left = up;
        down = up;
    }

    @Override
    public void update() {
        setAction();
        updateAnimations();

        // MOVEMENT
        moving = true;
        switch (direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
        }

        // COLLISION
        interactObject(
                gp.collisionDetector.checkObject(this, false)
        );
        gp.collisionDetector.checkTile(this);
    }

    private void updateAnimations() {
        if (moving) {
            animationCounter++;
        } else {
            animationCounter = 0;
            sprite = 0;
        }
        if (animationCounter > (1.0 / (double) speed) * 35) {

            if (sprite >= 3) {
                sprite = 0;
            } else {
                sprite++;
            }

            animationCounter = 0;
        }
    }

    String bias = "y";

    @Override
    public void setAction() {
        Random rand = new Random();
        int i;

        if (colliding) {
            actionCounter = -1;
        }
        if (bias.equals("y") && !getDirectionToObjectBiasY(this, gp.player).equals(direction)) {
            actionCounter = -1;
        }
        if (bias.equals("x") && !getDirectionToObjectBiasX(this, gp.player).equals(direction)) {
            actionCounter = -1;
        }

        if (actionCounter <= 0) {

            i = rand.nextInt(100) + 1;

            if (i % 5 != 0) {
                if (i % 2 == 0) {
                    direction = getDirectionToObjectBiasX(this, gp.player);
                    bias = "x";
                } else {
                    direction = getDirectionToObjectBiasY(this, gp.player);
                    bias = "y";
                }
            } else {
                i = rand.nextInt(100) + 1;

                if (i % 4 == 0) {
                    direction = "up";
                } else if (i % 4 == 1) {
                    direction = "down";
                } else if (i % 4 == 2) {
                    direction = "left";
                } else {
                    direction = "right";
                }
            }
            actionCounter = rand.nextInt(120) + 120;
        } else {
            actionCounter--;
        }
    }
}
