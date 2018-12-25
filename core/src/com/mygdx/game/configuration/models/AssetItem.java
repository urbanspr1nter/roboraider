package com.mygdx.game.configuration.models;

public class AssetItem {
    public String Type;
    public String Name;
    public String File;
    public float Volume;

    public AssetItem(String type, String name, String file, float volume) {
        this.Type = type;
        this.Name = name;
        this.File = file;
        this.Volume = volume;
    }
}
