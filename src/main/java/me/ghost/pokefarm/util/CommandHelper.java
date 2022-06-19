package me.ghost.pokefarm.util;

import me.ghost.pokefarm.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CommandHelper {

    public static String runCommandWithResult(String command) {
        try {
            String[] cmd = new String[]{"cmd", "/c", command};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            StringBuilder b = new StringBuilder();
            String line;
            Process p = pb.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = r.readLine()) != null) b.append(line);
            r.close();
            return b.toString();
        } catch (Exception e) {
            Main.debug("Error running command: " + command);
            Main.debug("Exception: " + e);
            return null;
        }
    }

    public static void runCommand(String command) {
        try {
            String[] cmd = new String[]{"cmd", "/c", command};
            new ProcessBuilder(cmd).start().waitFor();
        } catch (Exception e) {
            Main.debug("Error running command: " + command);
            Main.debug("Exception: " + e);
        }
    }

    public static void prepADB() {
        //Main.debug("killing server");
        runCommand("adb kill-server");
        //Main.debug("starting server");
        runCommand("adb start-server");
    }

    public static boolean isDeviceConnected() {
        String deviceID = runCommandWithResult("adb devices | findstr \"\\<device\\>\"");
        return deviceID != null && !deviceID.isBlank() && !deviceID.isEmpty();
    }

    public static String getDeviceID() {
        String deviceID = runCommandWithResult("adb devices | findstr \"\\<device\\>\"");
        if (deviceID == null || deviceID.isBlank() || deviceID.isEmpty()) return "Unknown?";
        return deviceID.replace("device", "").strip();
    }


    // Screen Input

    public static void Tap(String x, String y) {
        runCommand("adb shell input tap " + x + " " + y);
    }

    public static void Swipe(String startX, String startY, String endX, String endY, String duration) {
        runCommand("adb shell input swipe " + startX + " " + startY + " " + endX + " " + endY + " " + duration);
    }


    public static void sleep(long s, int t) {
        try {
            switch (t) {
                case 1 -> TimeUnit.MILLISECONDS.sleep(s);
                case 2 -> TimeUnit.SECONDS.sleep(s);
                case 3 -> TimeUnit.MINUTES.sleep(s);
            }
        } catch (Exception ignored) {}
    }

}
