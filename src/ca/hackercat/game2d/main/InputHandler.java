package ca.hackercat.game2d.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    GamePanel gp;
    public InputHandler(GamePanel gp) {
        this.gp = gp;
    }
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean jumpPressed;
    public boolean sprintPressed;
    public boolean vignetteToggled = true;
    public boolean hudToggled = false;

    public boolean cameraPanUp, cameraPanDown, cameraPanLeft, cameraPanRight;
    public boolean freeCamera, fixedCamera, playerCamera;

    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) { // up
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) { // left
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) { // down
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) { // right
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SHIFT) { //sprint
            sprintPressed = true;
        }

        if (code == KeyEvent.VK_SPACE) {
            jumpPressed = true;
        }

        if (code == KeyEvent.VK_UP) {
            cameraPanUp = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            cameraPanDown = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            cameraPanLeft = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            cameraPanRight = true;
        }



        if (code == KeyEvent.VK_F2) {
            if (freeCamera) {
                freeCamera = false;
                fixedCamera = true;
            } else if (fixedCamera) {
                fixedCamera = false;
                playerCamera = true;
            } else if (playerCamera) {
                playerCamera = false;
                freeCamera = true;
            }
        }

        if (code == KeyEvent.VK_F1) {
            vignetteToggled = !vignetteToggled;
        }
        if (code == KeyEvent.VK_F3) {
            hudToggled = !hudToggled;
        }

        if (code == KeyEvent.VK_BACK_SPACE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) { // up
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) { // left
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) { // down
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) { // right
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) { //sprint
            sprintPressed = false;
        }

        if (code == KeyEvent.VK_SPACE) {
            jumpPressed = false;
        }

        if (code == KeyEvent.VK_UP) {
            cameraPanUp = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            cameraPanDown = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            cameraPanLeft = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            cameraPanRight = false;
        }
    }
}
