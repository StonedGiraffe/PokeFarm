package me.ghost.pokefarm.util;

import me.ghost.pokefarm.Main;

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
        Main.debug("Tapping " + this.name);
        CommandHelper.Tap(this.x, this.y);
    }
    public void dragTo(String x, String y) { CommandHelper.Swipe(this.x, this.y, x, y, "500"); }
    public void dragTo(Position p) { CommandHelper.Swipe(this.x, this.y, p.x, p.y, "500"); }
    
}
