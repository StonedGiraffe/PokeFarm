package me.ghost.pokefarm.util.pogo;

import me.ghost.pokefarm.Main;
import me.ghost.pokefarm.util.system.CommandHelper;

import java.util.stream.IntStream;

public class PogoHelper {

    public static boolean isOpen() {
        String pogoPID = CommandHelper.runCommandWithResult("adb shell pidof com.nianticlabs.pokemongo");
        return pogoPID != null && !pogoPID.isBlank() && !pogoPID.isEmpty();
    }

    public static void start() { // start (or restart) pogo
        if (isOpen()) stop();
        if (Main.debug) Main.debug("Opening pogo");
        CommandHelper.runCommand("adb shell am start -n com.nianticlabs.pokemongo/com.nianticproject.holoholo.libholoholo.unity.UnityMainActivity");
        if (Main.debug) Main.debug("Waiting for pogo to load...");
        CommandHelper.sleep(40, 2);
        if (Main.debug) Main.debug("Accepting initial popup...");
        Positions.CENTER_OK.tap();
        CommandHelper.sleep(800, 1);
        Positions.BOTTOM_DISMISS.tap();
        CommandHelper.sleep(800, 1);
        if (Main.debug) Main.debug("Startup complete.");
    }

    public static void stop() { // go back to the home screen and kill pogo
        if (!isOpen()) {
            if (Main.debug) Main.debug("stop() called but pogo is already closed?");
            return;
        }
        if (Main.debug) Main.debug("Stopping pogo (unlocking game lock)");
        Positions.UNLOCK_START.dragTo(Positions.UNLOCK_END);
        CommandHelper.sleep(1, 2);
        if (Main.debug) Main.debug("Returning to home screen");
        CommandHelper.runCommand("adb shell input keyevent KEYCODE_HOME");
        CommandHelper.sleep(1, 2);
        if (Main.debug) Main.debug("Closing pogo process");
        CommandHelper.runCommand("adb shell am force-stop com.nianticlabs.pokemongo");
        CommandHelper.sleep(5, 2);
        if (Main.debug) Main.debug("Shutdown complete");
    }

    public static void getToMainMenu() { // shitty but works every time lol
        IntStream.rangeClosed(1, 4).forEach(i -> {
            CommandHelper.runCommand("adb shell input keyevent KEYCODE_BACK");
            CommandHelper.sleep(900, 1);
        });
    }

    public static void startFarm() { // connect to go plus, start autowalking
        if (!isOpen()) {
            if (Main.debug) Main.debug("startFarm() called when pogo isn't open?");
            if (Main.debug) Main.debug("Starting pogo..");
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

    public static void openFriendsPage() {
        Positions.TRAINER_MENU.tap(); // get to the friends tab
        CommandHelper.sleep(2, 2);
        Positions.FRIEND_TAB.tap();
        CommandHelper.sleep(1, 2);
        Positions.FIRST_FRIEND.tap(); // open initial friend page
    }

    public static void handleFriendPage() {
        if (Main.debug) Main.debug("handleFriendPage() - waiting for page to load");
        CommandHelper.sleep(1300, 1);
        Positions.ACCEPT_GIFT.tap();
        if (Main.debug) Main.debug("handleFriendPage() - waiting after accepting gift");
        CommandHelper.sleep(1200, 1);
        Positions.SEND_GIFT.tap();
        if (Main.debug) Main.debug("handleFriendPage() - waiting after sending gift");
        CommandHelper.sleep(700, 1);
        Positions.SWIPE_START.dragTo(Positions.SWIPE_END);
    }


    public static void friendLoop(int friends) { // send + accept gifts to friends
        if (!isOpen()) {
            if (Main.debug) Main.debug("friendLoop() called when pogo isn't open?");
            if (Main.debug) Main.debug("Starting pogo..");
            start();
        }
        if (Main.debug) Main.debug("friendLoop() with " + friends + " friends (eta: " + friends * 3 + " seconds)");
        openFriendsPage();
        IntStream.rangeClosed(1, friends).forEach(i -> { // accept gift, send gift, swipe to next friend, repeat
            Main.debug("Doing friend sequence " + i + " (waiting for page to load)");
            handleFriendPage();
        });
        if (Main.debug) Main.debug("friendLoop finished, returning to main menu");
        getToMainMenu();
        CommandHelper.sleep(900, 1);
    }

}
