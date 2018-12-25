package com.mygdx.game.configuration.models;

public class Trigger {
    public String Action;
    public Location Location;
    public String Handler;

    public Trigger() {
        this.Location = new Location();
    }
}
