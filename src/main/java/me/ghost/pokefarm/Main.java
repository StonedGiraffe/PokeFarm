package me.ghost.pokefarm;

import me.ghost.pokefarm.util.pogo.Position;
import me.ghost.pokefarm.util.screen.ImageParser;
import me.ghost.pokefarm.util.system.ADBHelper;
import me.ghost.pokefarm.util.system.CommandHelper;
import me.ghost.pokefarm.util.pogo.PogoHelper;
import me.ghost.pokefarm.util.system.FileHelper;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
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
        //OpenCV.loadShared();
        OpenCV.loadLocally();
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
        if (cmd.equals("--debug-image")) {
            debug = true;
            debug("Now debugging image parsing");
            File in = new File(FileHelper.getWorkDir(), "screenshot.jpg");
            if (!in.exists()) {
                debug("screenshot.png not located in current path!");
                System.exit(0);
            }
            File pokeball = new File(FileHelper.getWorkDir(), "go_plus.png");
            if (!pokeball.exists()) {
                debug("pokeball.png not located in current path, trying to copy...");
                boolean wrote = FileHelper.writeResource("go_plus.png", pokeball);
                if (!wrote) {
                    debug("some shit went wrong extracting it");
                    System.exit(0);
                }
            }
            debug("Trying to locate go plus icon in main screen...");
            Mat screenshot = null;
            Mat toFind = null;
            screenshot = Imgcodecs.imread(in.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);
            toFind = Imgcodecs.imread(pokeball.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);
            ImageParser.locateImage(screenshot, toFind, "go_plus_button");
            System.exit(0);
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
