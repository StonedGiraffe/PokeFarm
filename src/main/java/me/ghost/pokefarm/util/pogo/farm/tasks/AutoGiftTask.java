package me.ghost.pokefarm.util.pogo.farm.tasks;

import me.ghost.pokefarm.util.pogo.Positions;
import me.ghost.pokefarm.util.pogo.farm.FarmTask;
import me.ghost.pokefarm.util.pogo.farm.TaskManager;
import me.ghost.pokefarm.util.system.CommandHelper;
import me.ghost.pokefarm.util.system.ConfigHelper;

import java.util.stream.IntStream;

import static me.ghost.pokefarm.Main.log;

public class AutoGiftTask extends FarmTask {

    public AutoGiftTask() {
        super("AutoGifter");
    }

    @Override
    public void task() {
        if (ConfigHelper.FRIENDS <= 1) return;
        log("Starting AutoGifter...");
        TaskManager.getToMainMenu();
        this.openFriendsPage();
        log("Sending/Accepting gifts | Friends: " + ConfigHelper.FRIENDS);
        IntStream.rangeClosed(1, ConfigHelper.FRIENDS).forEach(i -> this.doAutoGift());
        log("Going back to main screen");
        TaskManager.getToMainMenu();
    }

    public void openFriendsPage() {
        Positions.TRAINER_MENU.tap();
        CommandHelper.sleep(2, 2);
        Positions.FRIEND_TAB.tap();
        CommandHelper.sleep(1, 2);
        Positions.FIRST_FRIEND.tap();
    }

    public void doAutoGift() {
        CommandHelper.sleep(1300, 1);
        Positions.ACCEPT_GIFT.tap();
        CommandHelper.sleep(1200, 1);
        Positions.SEND_GIFT.tap();
        CommandHelper.sleep(700, 1);
        Positions.SWIPE_START.dragTo(Positions.SWIPE_END);
    }
}
