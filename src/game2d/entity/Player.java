package game2d.entity;

import game2d.entity.object.Door;
import game2d.main.GamePanel;
import game2d.main.InputHandler;
import game2d.util.Audio;

public abstract class Player extends Entity {

    InputHandler inputHandler;
    public int maxStamina = 150;
    public int stamina = maxStamina;

    public int talking = -1;

    public Player(GamePanel gp, InputHandler inputHandler) {
        super(gp);
        this.inputHandler = inputHandler;

        name = "player";

        collisionBox = setBox(3, 6, 10, 10);
        maxHealth = 20;
        health = maxHealth;
        speed = 5;
    }

    // UPDATING

    public void update() {

        if (health > maxHealth) {
            health = maxHealth;
        }

        // MOVEMENT

        updateStamina();

        if (inputHandler.sprintPressed && (stamina > 0)) speed = 10;
        else if (stamina == 0) speed = 4;
        else speed = 6;

        double xVelocity = 0;
        double yVelocity = 0;

        if (inputHandler.upPressed) yVelocity -= speed;
        else if (inputHandler.downPressed) yVelocity += speed;
        if (inputHandler.leftPressed) xVelocity -= speed;
        else if (inputHandler.rightPressed) xVelocity += speed;

        boolean updateDirection = false;

        if (yVelocity != 0 && xVelocity != 0) {
            worldX += (int) Math.round(xVelocity * 0.75);
            worldY += (int) Math.round(yVelocity * 0.75);
        } else {
            worldX += xVelocity;
            worldY += yVelocity;
            updateDirection = true;
        }
        if (updateDirection) {
            if (inputHandler.upPressed) direction = "up";
            else if (inputHandler.downPressed) direction = "down";
            else if (inputHandler.leftPressed) direction = "left";
            else if (inputHandler.rightPressed) direction = "right";
        }

        // ANIMATION COUNTER
        updateAnimations();

        // COLLISION
        updateDialogue(
                gp.collisionDetector.checkInteraction(this)
        );
        interactObject(
                gp.collisionDetector.checkObject(this, true)
        );
        gp.collisionDetector.checkTile(this);
    }

    public void updateAnimations() {
        if (inputHandler.upPressed || inputHandler.downPressed || inputHandler.leftPressed || inputHandler.rightPressed) {
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

    public void updateStamina() {
        if ((inputHandler.upPressed || inputHandler.downPressed ||  inputHandler.leftPressed || inputHandler.rightPressed) && inputHandler.sprintPressed) {
            stamina--;
        } else if (!(inputHandler.upPressed || inputHandler.downPressed ||  inputHandler.leftPressed || inputHandler.rightPressed)) {
            stamina += 2;
        }

        if (stamina < 0) {
            stamina = 0;
        } else if (stamina > maxStamina) {
            stamina = maxStamina;
        }
    }

    public void updateDialogue(int slot) {
        if (slot != -1 && gp.inputHandler.actionPressed) {
            talking = slot;
            gp.currentGameState = GamePanel.DIALOGUE_STATE;
        } else {
            talking = -1;
        }
    }

    public void interactObject(int slot) {
        if (slot != -1) {
            switch(gp.obj[slot].name) {
                case "key":
                    gp.vars.keys++;
                    gp.obj[slot] = null;

                    gp.playSound(Audio.GET_ITEM_UNIMPORTANT);
                    break;
                case "door_locked":
                    if (gp.vars.keys > 0) {
                        gp.vars.keys--;

                        int x = gp.obj[slot].worldX;
                        int y = gp.obj[slot].worldY;
                        String direction = gp.obj[slot].direction;

                        gp.obj[slot] = new Door(gp);
                        gp.obj[slot].worldX = x;
                        gp.obj[slot].worldY = y;
                        gp.obj[slot].direction = direction;
                        gp.obj[slot].open = true;

                        gp.playSound(Audio.DOOR_UNLOCK);
                    } else {
                        gp.collisionDetector.handleObjectCollision(this, slot);
                    }
                    break;
                case "door":
                    gp.obj[slot].open = true;
                    gp.obj[slot].closeTimer = CLOSE_TIMER_START_VALUE;
                    gp.playSound(Audio.DOOR_OPEN);
                    break;
                case "door_open":
                    gp.obj[slot].closeTimer = CLOSE_TIMER_START_VALUE;
                    break;
                case "slime":
                    if (flickering <= 0) {
                        flickering = FLICKERING_TIME;
                        health -= gp.obj[slot].attackDamage;
                    }
                case "generic":
                    gp.collisionDetector.handleObjectCollision(this, slot);
            }
        }
    }
}
