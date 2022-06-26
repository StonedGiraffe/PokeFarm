package me.ghost.pokefarm;

import me.ghost.pokefarm.util.pogo.farm.TaskManager;
import me.ghost.pokefarm.util.system.*;
import me.ghost.screenhelper.ScreenHelper;

import java.util.stream.IntStream;

public class Main {


    public static void log(String s) {
        System.out.println("[PokeFarm] " + s);
    }

    public static void debug(String s) {
        System.out.println("[PF-DEBUG] " + s);
    }

    public static boolean debug = false;

    public static void main(String[] args) {
        String cmd;

        if (!ConfigHelper.init()) {
            log("An error occured while starting up.");
            System.exit(0);
        }

        ADBHelper.prepADB();
        if (!ADBHelper.isDeviceConnected()) {
            log("No connected device found!");
            System.exit(0);
        }

        TaskManager.init();
        ScreenHelper.load();
        ScreenHelper.setDebug(true);

        if (args.length < 1) cmd = "--farm";
        else cmd = args[0];

        if (cmd.equals("--farm")) {
            TaskManager.launchPogo();
            TaskManager.autoGiftTask.run();
            IntStream.rangeClosed(1, ConfigHelper.FARM_LOOPS).forEach(i -> {
                TaskManager.autoWalkTask.run();
                TaskManager.restartPogo();
            });
        }
    }


}
