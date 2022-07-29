package ca.hackercat.util;

import ca.hackercat.game2d.main.GamePanel;
import ca.hackercat.game2d.main.GameVars;
import com.google.gson.Gson;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ToolBox {
    GamePanel gp;
    GameVars vars;
    Gson gson = new Gson();

    final String PATH = "game_vars.json";

    public ToolBox(GamePanel gp, GameVars vars) {
        this.gp = gp;
        this.vars = vars;
    }

    public void setGameVars() {

    }
    public void getGameVars() {

    }

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    public double[] linearInterpolate(double start, double end, int length) {
        double[] array = new double[length + 1];
        for (int i = 0; i <= length; i++) {
            array[i] = start + i * (end - start) / length;
        }
        return array;
    }

    //doesn't work correctly (f)
    public double[] polynomialInterpolate(double start, double end, int length) {
        double[] array = new double[length + 1];
        for (int i = 0; i <= length; i++) {
            array[i] = 0;
        }
        return array;
    }
}
