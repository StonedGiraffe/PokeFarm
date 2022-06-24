package me.ghost.pokefarm;

import me.ghost.pokefarm.util.pogo.Position;
import me.ghost.pokefarm.util.screen.ImageCache;
import me.ghost.pokefarm.util.screen.ImageParser;
import me.ghost.pokefarm.util.system.*;
import me.ghost.pokefarm.util.pogo.PogoHelper;
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
        String cmd = args[0];
        if (cmd == null || cmd.isBlank() || cmd.isEmpty()) cmd = "--farm";
        if (cmd.equals("--debug")) debug = true;

        OpenCV.loadLocally();
        if (!ResourceLoader.init() || !ImageCache.init() || !ConfigHelper.init()) {
            Main.log("One or more systems failed to startup.");
            System.exit(0);
        }


        if (!debug && cmd.equals("--farm")) {
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
        if (debug) {
            File in = new File(FileHelper.getWorkDir(), "screenshot.jpg");
            if (!in.exists()) {
                debug("screenshot.png not located in current path!");
                System.exit(0);
            }
            Mat screenshot = Imgcodecs.imread(in.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);
            ImageParser.locateImage(screenshot, ImageCache.go_plus_icon, "go_plus_button");
            ImageParser.locateImage(screenshot, ImageCache.main_menu_icon, "main_menu_button");
            ImageParser.locateImage(screenshot, ImageCache.pgs_menu_icon, "pgs_menu_icon");
            System.exit(0);
        }


    }


}
