package me.ghost.pokefarm;

import me.ghost.pokefarm.util.ADBHelper;
import me.ghost.pokefarm.util.CommandHelper;
import me.ghost.pokefarm.util.PogoHelper;

import java.io.Console;
import java.util.stream.IntStream;

public class Main {


    public static void log(String s) {
        System.out.println("[PokeFarm] " + s);
    }

    public static void debug(String s) {
        log("[DEBUG] " + s);
    }

    public static boolean debug = false;

    public static void main(String[] args) {
        String cmd = args[0];
        if (cmd == null || cmd.isBlank() || cmd.isEmpty()) cmd = "--farm";

        if (cmd.equals("--farm")) {
            log("Starting ADB Server...");
            ADBHelper.prepADB();
            if (!ADBHelper.isDeviceConnected()) {
                log("No connected devices were found, Quitting.");
                System.exit(0);
            }
            log("Connected to device: " + ADBHelper.getDeviceID());

            Console console = System.console();
            int loops = Integer.parseInt(console.readLine("How many loops to run?: "));
            IntStream.rangeClosed(1, loops).forEach(i -> {
                log("Preparing for farm loop " + i);
                PogoHelper.start(); // open and prep app
                if (i == 1) {
                    log("Doing friend sequence on first farm loop..");
                    PogoHelper.friendLoop(79); //todo config system for this stuff
                }
                PogoHelper.startFarm(); // connect go plus & start auto walking
                log("Farming for 30 minutes..."); // farm for 30 minutes and repeat
                CommandHelper.sleep(30, 3);
            });
        }
        if (cmd.equals("--debug-startup")) {
            debug("Now debugging startup functions");
            debug = true;
            log("Starting ADB Server...");
            ADBHelper.prepADB();
            if (!ADBHelper.isDeviceConnected()) {
                log("No connected devices were found, Quitting.");
                System.exit(0);
            }
            log("Connected to device: " + ADBHelper.getDeviceID());
            log("Testing startup sequence...");
            PogoHelper.start();
            log("Testing shutdown sequence...");
            PogoHelper.stop();
        }
        if (cmd.equals("--debug-friends")) {
            debug("Now debugging friends functions");
            debug = true;
            log("Starting ADB Server...");
            ADBHelper.prepADB();
            if (!ADBHelper.isDeviceConnected()) {
                log("No connected devices were found, Quitting.");
                System.exit(0);
            }
            log("Connected to device: " + ADBHelper.getDeviceID());
            log("Preparing to test friends functions (starting pogo)");
            PogoHelper.start();
            log("Opening friends page");
            PogoHelper.openFriendsPage();
            log("Testing friend page sequence");
            PogoHelper.handleFriendPage();
            log("Returning to Main Menu");
            PogoHelper.getToMainMenu();
        }
    }


}
