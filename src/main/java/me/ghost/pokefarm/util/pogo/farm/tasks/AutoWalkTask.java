package me.ghost.pokefarm.util.pogo.farm.tasks;

import me.ghost.pokefarm.util.pogo.Positions;
import me.ghost.pokefarm.util.pogo.farm.FarmTask;
import me.ghost.pokefarm.util.pogo.farm.TaskManager;
import me.ghost.pokefarm.util.system.CommandHelper;
import me.ghost.pokefarm.util.system.ConfigHelper;

import static me.ghost.pokefarm.Main.log;

public class AutoWalkTask extends FarmTask {


    public AutoWalkTask() {
        super("AutoWalk");
    }

    @Override
    public void task() {
        log("Starting AutoWalk + VGP Farm...");
        TaskManager.getToMainMenu();
        log("Connecting to VGP...");
        this.prepVGP();
        log("Starting AutoWalk...");
        this.prepAutoWalk();
        if (ConfigHelper.IS_SAMSUNG) {
            log("Turning on Samsung GameTools lock.");
            Positions.GAME_LOCK.tap();
        }
        log("Waiting for 30 minutes");
        CommandHelper.sleep(30, 3);
        if (ConfigHelper.IS_SAMSUNG) Positions.UNLOCK_START.dragTo(Positions.UNLOCK_END);
        log("AutoWalk + VGP Farm finished!");
    }

    public void prepVGP() {
        Positions.GO_PLUS.tap();
        CommandHelper.sleep(20, 2);
    }

    public void prepAutoWalk() {
        Positions.PGS_MENU.tap();
        CommandHelper.sleep(800, 1);
        Positions.PGS_AUTOWALK.tap();
        CommandHelper.sleep(800, 1);
        Positions.PGS_AUTOWALK_OK.tap();
        CommandHelper.sleep(800, 1);
        Positions.PGS_MENU.tap();
        CommandHelper.sleep(800, 1);
    }
}
