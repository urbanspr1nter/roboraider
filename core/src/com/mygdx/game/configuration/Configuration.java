package com.mygdx.game.configuration;

import com.mygdx.game.configuration.models.*;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    public Game Game;
    public Debug Debug;
    public Ui Ui;
    public ViewPort ViewPort;
    public Map Map;
    public Sprite Sprite;
    public List<Entity> Entities;
    public Assets Assets;

    public Configuration() {
        this.Entities = new ArrayList<>();
    }
}