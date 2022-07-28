package ca.hackercat.util;

import ca.hackercat.game2d.main.GamePanel;
import ca.hackercat.game2d.main.GameVars;

import com.google.gson.Gson;

import java.io.File;
import java.util.Properties;

public class DataSave {
    GamePanel gp;
    GameVars vars;
    Gson gson = new Gson();

    final String PATH = "game_vars.json";

    public DataSave(GamePanel gp, GameVars vars) {
        this.gp = gp;
        this.vars = vars;
    }

    public void setGameVars() {

    }
    public void getGameVars() {

    }
}
