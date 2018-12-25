package com.mygdx.game.configuration.models;

public class Map {
    public Unit Unit;
    public Dimension Dimension;
    public String TiledMap;

    public Map() {
        this.Unit = new Unit();
        this.Dimension = new Dimension();
    }
}