package com.mygdx.game.configuration.models;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    public String Key;
    public ViewPort ViewPort;
    public Input Input;
    public Map Map;
    public Music Music;
    public Player Player;
    public List<Trigger> Triggers;
    public List<NonPlayableCharacter> NonPlayableCharacters;
    public List<String> Enemies;

    public Entity() {
        this.ViewPort = new ViewPort();
        this.Input = new Input();
        this.Map = new Map();
        this.Music = new Music();
        this.Player = new Player();
        this.Triggers = new ArrayList<>();
        this.NonPlayableCharacters = new ArrayList<>();
        this.Enemies = new ArrayList<>();
    }
}
