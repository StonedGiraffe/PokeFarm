package me.ghost.pokefarm.util.pogo.farm;

public class FarmTask {

    private final String name;

    public FarmTask(String name) {
        this.name = name;
    }

    public void run() {
        this.task();
    }

    public void task() {}

}
