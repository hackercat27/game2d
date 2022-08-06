package game2d.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * @version 0.0.8-alpha
 * @author hackercat
 */
public class Main {
    public static JFrame window;

    public Image getIconTexture() {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/misc/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(1000);
        return null;
    }

    //TODO: documentation hell (you will regret ignoring this later when you don't understand any of the code you wrote)
    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("game2d");

        Image icon = new Main().getIconTexture();
        window.setIconImage(icon);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        if (gamePanel.vars.fullscreen) {
            System.out.println("Starting in fullscreen mode.");
            window.setUndecorated(true);
            window.pack();
        } else {
            System.out.println("Starting in windowed mode.");
            gamePanel.screenWidth2 = 1280;
            gamePanel.screenHeight2 = 720;
            window.setSize(gamePanel.screenWidth2, gamePanel.screenHeight2);
        }

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startLogicThread();
    }
}
