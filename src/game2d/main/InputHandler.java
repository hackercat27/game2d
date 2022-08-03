package game2d.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean jumpPressed;
    public boolean sprintPressed;
    public boolean vignetteToggled = true;
    public boolean hudToggled = false;
    public boolean cameraPanUp, cameraPanDown, cameraPanLeft, cameraPanRight;

    public boolean freeCamera, fixedCamera, playerCamera;
    GamePanel gp;

    public InputHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_U) {
            gp.player.health++;
        }
        if (code == KeyEvent.VK_J) {
            gp.player.health--;
        }
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

        if (code == KeyEvent.VK_ESCAPE) { //pause
            if (gp.currentGameState == gp.PLAY_STATE) {
                gp.currentGameState = gp.PAUSE_STATE;
            } else if (gp.currentGameState == gp.PAUSE_STATE) {
                gp.currentGameState = gp.PLAY_STATE;
            }
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
            if (gp.vars.cameraMode == 0) {
                gp.vars.cameraMode = 1;
            } else if (gp.vars.cameraMode == 1) {
                gp.vars.cameraMode = 2;
            } else if (gp.vars.cameraMode == 2) {
                gp.vars.cameraMode = 0;
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
