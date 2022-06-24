package me.ghost.pokefarm.util.pogo;

import me.ghost.pokefarm.Main;
import me.ghost.pokefarm.util.system.ADBHelper;

public class Position {

    private final String name;
    private String x;
    private String y;
    
    public Position(String x, String y, String name) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public String getX() { return this.x; }
    public String getY() { return this.y; }
    public String getName() { return this.name; }

    public void setX(String x) { this.x = x; }
    public void setY(String y) { this.y = y; }
    
    public void tap() {
        if (Main.debug) Main.debug("Tapping " + this.name);
        ADBHelper.Tap(this.x, this.y);
    }
    public void dragTo(String x, String y) { ADBHelper.Swipe(this.x, this.y, x, y, "500"); }
    public void dragTo(Position p) {
        if (Main.debug) Main.debug("Dragging " + this.name + " to " + p.name);
        ADBHelper.Swipe(this.x, this.y, p.x, p.y, "500");
    }
    
}
