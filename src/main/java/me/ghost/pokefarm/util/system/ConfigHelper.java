package me.ghost.pokefarm.util.system;

import me.ghost.pokefarm.Main;
import me.ghost.pokefarm.util.pogo.Positions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Objects;

public class ConfigHelper {

    public static int FRIENDS = 0;


    public static String BASE_CONFIG = """
            {
                "friends" : "79",
                "trainer_menu_button" : "150,2535",
                "friend_tab_button" : "1018,230",
                "first_friend" : "814,1117",
                "send_gift" : "280,2190",
                "accept_gift" : "730,1930",
                "swipe_start" : "1325,2665",
                "swipe_end" : "7,2665",
                "game_lock" : "1391,2892",
                "unlock_start" : "778,1518",
                "unlock_end" : "778,950"
            }""";


    public static boolean init() {
        File configFile = new File(FileHelper.getBaseFolder(), "config.json");
        if (!configFile.exists()) {
            if (Main.debug) Main.debug("config.json doesn't exist, writing now.");
            try { // write default config if it doesn't exist
                FileWriter fw = new FileWriter(configFile);
                fw.write(BASE_CONFIG);
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        try { // try to read config.json
            FileInputStream fis = new FileInputStream(configFile);
            String configData = FileHelper.streamToString(fis);
            if (configData == null || configData.isEmpty() || configData.isBlank()) {
                Main.debug("config.json is null or empty?");
                return false;
            }
            if (Main.debug) Main.debug("setting Positions from config.json...");
            setPosFromConfig(configData); // set Positions from config.json
            if (Main.debug) Main.debug("Trainer Menu | X: " + Positions.TRAINER_MENU.getX() + " | Y: " + Positions.TRAINER_MENU.getY());
            int fr = JsonUtil.getInt(configData, "friends");
            if (fr != -1) FRIENDS = fr;
            else {
                Main.log("Invalid friends number in config.json...");
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setPosFromConfig(String jsonData) {
        Positions.TRAINER_MENU.setXY(Objects.requireNonNull(JsonUtil.getKey(jsonData, "trainer_menu_button")));
        Positions.FRIEND_TAB.setXY(Objects.requireNonNull(JsonUtil.getKey(jsonData, "friend_tab_button")));
        Positions.FIRST_FRIEND.setXY(Objects.requireNonNull(JsonUtil.getKey(jsonData, "first_friend")));
        Positions.SEND_GIFT.setXY(Objects.requireNonNull(JsonUtil.getKey(jsonData, "send_gift")));
        Positions.ACCEPT_GIFT.setXY(Objects.requireNonNull(JsonUtil.getKey(jsonData, "accept_gift")));
        Positions.SWIPE_START.setXY(Objects.requireNonNull(JsonUtil.getKey(jsonData, "swipe_start")));
        Positions.SWIPE_END.setXY(Objects.requireNonNull(JsonUtil.getKey(jsonData, "swipe_end")));
        Positions.GAME_LOCK.setXY(Objects.requireNonNull(JsonUtil.getKey(jsonData, "game_lock")));
        Positions.UNLOCK_START.setXY(Objects.requireNonNull(JsonUtil.getKey(jsonData, "unlock_start")));
        Positions.UNLOCK_END.setXY(Objects.requireNonNull(JsonUtil.getKey(jsonData, "unlock_end")));
    }

}
