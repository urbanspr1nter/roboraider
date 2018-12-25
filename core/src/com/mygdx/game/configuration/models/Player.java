package com.mygdx.game.configuration.models;

public class Player {
    public String AssetName;
    public String Name;
    public Dimension Dimension;
    public InitialPosition InitialPosition;
    public String InitialOrientation;

    public Player() {
        this.Dimension = new Dimension();
        this.InitialPosition = new InitialPosition();
    }
}
