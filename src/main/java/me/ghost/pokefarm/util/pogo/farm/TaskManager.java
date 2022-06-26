package me.ghost.pokefarm.util.pogo.farm;

import me.ghost.pokefarm.util.pogo.Positions;
import me.ghost.pokefarm.util.pogo.farm.tasks.AutoGiftTask;
import me.ghost.pokefarm.util.pogo.farm.tasks.AutoWalkTask;
import me.ghost.pokefarm.util.system.CommandHelper;
import me.ghost.pokefarm.util.system.ConfigHelper;
import me.ghost.screenhelper.ScreenHelper;

import java.util.stream.IntStream;

public class TaskManager {

    public static AutoGiftTask autoGiftTask;
    public static AutoWalkTask autoWalkTask;

    public static void init() {
        autoGiftTask = new AutoGiftTask();
        autoWalkTask = new AutoWalkTask();
    }


    public static boolean isPogoOpen() {
        String pogoPID = CommandHelper.runCommandWithResult("adb shell pidof com.nianticlabs.pokemongo");
        return pogoPID != null && !pogoPID.isBlank() && !pogoPID.isEmpty();
    }

    public static void launchPogo() {
        if (isPogoOpen()) return;
        CommandHelper.runCommand("adb shell am start -n com.nianticlabs.pokemongo/com.nianticproject.holoholo.libholoholo.unity.UnityMainActivity");
        CommandHelper.sleep(5, 2);
        ScreenHelper.waitForPogo();
        CommandHelper.sleep(1200, 1);
    }

    public static void closePogo() {
        if (!isPogoOpen()) return;
        if (ConfigHelper.IS_SAMSUNG) Positions.UNLOCK_START.dragTo(Positions.UNLOCK_END);
        CommandHelper.sleep(1, 2);
        CommandHelper.runCommand("adb shell input keyevent KEYCODE_HOME");
        CommandHelper.sleep(1, 2);
        CommandHelper.runCommand("adb shell am force-stop com.nianticlabs.pokemongo");
        CommandHelper.sleep(5, 2);
    }

    public static void restartPogo() {
        closePogo();
        launchPogo();
    }


    public static void getToMainMenu() { // shitty but works every time lol
        IntStream.rangeClosed(1, 4).forEach(i -> {
            CommandHelper.runCommand("adb shell input keyevent KEYCODE_BACK");
            CommandHelper.sleep(900, 1);
        });
    }
}
