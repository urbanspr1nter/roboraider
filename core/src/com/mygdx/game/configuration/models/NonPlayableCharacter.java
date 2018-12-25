package com.mygdx.game.configuration.models;

public class NonPlayableCharacter {
    public String AssetName;
    public String Name;
    public Location Location;
    public String Dialog;
    public String Trigger;

    public NonPlayableCharacter() {
        this.Location = new Location();
    }
}
