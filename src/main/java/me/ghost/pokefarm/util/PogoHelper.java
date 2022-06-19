package me.ghost.pokefarm.util;

import me.ghost.pokefarm.Main;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PogoHelper {

    public static boolean isOpen() {
        String pogoPID = CommandHelper.runCommandWithResult("adb shell pidof com.nianticlabs.pokemongo");
        return pogoPID != null && !pogoPID.isBlank() && !pogoPID.isEmpty();
    }

    public static void start() { // start (or restart) pogo
        if (isOpen()) stop();
        Main.debug("Opening pogo");
        CommandHelper.runCommand("adb shell am start -n com.nianticlabs.pokemongo/com.nianticproject.holoholo.libholoholo.unity.UnityMainActivity");
        Main.debug("Waiting for pogo to load...");
        CommandHelper.sleep(40, 2);
        Main.debug("Accepting initial popup...");
        Positions.CENTER_OK.tap();
        CommandHelper.sleep(800, 1);
        Positions.BOTTOM_DISMISS.tap();
        CommandHelper.sleep(800, 1);
        Main.debug("Startup complete.");
    }

    public static void stop() { // go back to the home screen and kill pogo
        if (!isOpen()) {
            Main.debug("stop() called but pogo is already closed?");
            return;
        }
        Main.debug("Stopping pogo (unlocking game lock)");
        Positions.UNLOCK_START.dragTo(Positions.UNLOCK_END);
        CommandHelper.sleep(1, 2);
        Main.debug("Returning to home screen");
        CommandHelper.runCommand("adb shell input keyevent KEYCODE_HOME");
        CommandHelper.sleep(1, 2);
        Main.debug("Closing pogo process");
        CommandHelper.runCommand("adb shell am force-stop com.nianticlabs.pokemongo");
        CommandHelper.sleep(5, 2);
        Main.debug("Shutdown complete");
    }

    public static void startFarm() { // connect to go plus, start autowalking
        if (!isOpen()) {
            Main.debug("startFarm() called when pogo isn't open?");
            Main.debug("Starting pogo..");
            start();
        }
        Positions.GO_PLUS.tap();
        CommandHelper.sleep(20, 2);
        Positions.PGS_MENU.tap();
        CommandHelper.sleep(800, 1);
        Positions.PGS_AUTOWALK.tap();
        CommandHelper.sleep(800, 1);
        Positions.PGS_AUTOWALK_OK.tap();
        CommandHelper.sleep(800, 1);
        Positions.PGS_MENU.tap();
        CommandHelper.sleep(800, 1);
        Positions.GAME_LOCK.tap();
    }


}
