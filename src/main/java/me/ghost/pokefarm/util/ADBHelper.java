package me.ghost.pokefarm.util;

import java.io.File;

public class ADBHelper {
    public static void prepADB() {
        CommandHelper.runCommand("adb kill-server");
        CommandHelper.runCommand("adb start-server");
    }

    public static boolean isDeviceConnected() {
        String deviceID = CommandHelper.runCommandWithResult("adb devices | findstr \"\\<device\\>\"");
        return deviceID != null && !deviceID.isBlank() && !deviceID.isEmpty();
    }

    public static String getDeviceID() {
        String deviceID = CommandHelper.runCommandWithResult("adb devices | findstr \"\\<device\\>\"");
        if (deviceID == null || deviceID.isBlank() || deviceID.isEmpty()) return "Unknown?";
        return deviceID.replace("device", "").strip();
    }

    public static void Tap(String x, String y) {
        CommandHelper.runCommand("adb shell input tap " + x + " " + y);
    }

    public static void Swipe(String startX, String startY, String endX, String endY, String duration) {
        CommandHelper.runCommand("adb shell input swipe " + startX + " " + startY + " " + endX + " " + endY + " " + duration);
    }

    public static File screenshot(String name) { // todo similar image comparison to locate on-screen elements programmatically
        if (name == null || name.isEmpty() || name.isBlank()) name = "screen.png";
        else if (!name.endsWith(".png")) name += ".png";
        CommandHelper.runCommand("adb shell screencap -p /sdcard/" + name);
        File workdir = FileHelper.getThis();
        if (workdir != null) {
            CommandHelper.runCommand("adb pull /sdcard/" + name + " " + workdir.getPath());
            CommandHelper.runCommand("adb shell rm /sdcard/" + name);
            return new File(workdir, name);
        } else {
            CommandHelper.runCommand("adb shell rm /sdcard/" + name);
            return null;
        }
    }
}
