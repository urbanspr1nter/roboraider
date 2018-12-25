package com.mygdx.game.input.enums;

public enum AdapterType {
    DefaultInputAdapter("DefaultInputAdapter"),
    LocalMapInputAdapter("LocalMapInputAdapter"),
    TitleScreenInputAdapter("TitleScreenInputAdapter");

    private String adapterType;

    AdapterType(String value) {
        this.adapterType = value;
    }

    @Override
    public String toString() {
        return this.adapterType;
    }
}
