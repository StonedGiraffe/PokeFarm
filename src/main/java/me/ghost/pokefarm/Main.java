package me.ghost.pokefarm;

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

    public static void main(String[] args) {
        log("Starting ADB Server...");
        CommandHelper.prepADB();
        if (!CommandHelper.isDeviceConnected()) {
            log("No connected devices were found, Quitting.");
            System.exit(0);
        }
        log("Connected to device: " + CommandHelper.getDeviceID());

        Console console = System.console();
        int loops = Integer.parseInt(console.readLine("How many loops to run?: "));
        IntStream.rangeClosed(1, loops).forEach(i -> {
            log("Preparing for farm loop " + i);
            PogoHelper.start(); // open and prep app
            PogoHelper.startFarm(); // connect go plus & start auto walking
            log("Farming for 30 minutes..."); // farm for 30 minutes and repeat
            CommandHelper.sleep(30, 3);
        });

    }


}
