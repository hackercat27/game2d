package game2d.util;

import game2d.main.GamePanel;
import game2d.main.GameVars;
import com.google.gson.Gson;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ToolBox {
    GamePanel gp;
    GameVars vars;
    Gson gson;

    final String PATH = "game_vars.json";

    public ToolBox(GamePanel gp, GameVars vars) {
        this.gp = gp;
        this.vars = vars;
        this.gson = new Gson();
    }

    public ToolBox() {}

    public void setGameVars() {

    }
    public void getGameVars() {

    }

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);

        return scaledImage;
    }

    public double[] linearInterpolate(double start, double end, int length) {
        double[] array = new double[length + 1];
        for (int i = 0; i <= length; i++) {
            array[i] = start + i * (end - start) / length;
        }
        return array;
    }

    //https://www.desmos.com/calculator/gimzbap5eb
    public double[] cosineInterpolate(double start, double end, int length) {
        double[] array = new double[length + 1];
        int y;
        double h;
        for (int i = 0; i <= length; i++) {
            h = (end / 2) - (start / 2);
            y = (int) Math.round(((-h * Math.cos((Math.PI / length) * i)) + h) + start);
            array[i] = y;
        }
        return array;
    }
}
