package com.mygdx.game.configuration.enums;

public enum ModelType {
    AssetItem("AssetItem"),
    Assets("Assets"),
    Game("Game"),
    Ui("Ui"),
    Map("Map"),
    Sprite("Sprite"),
    Unit("Unit"),
    ViewPort("ViewPort");

    private String modelType;

    ModelType(String modelType) {
        this.modelType = modelType;
    }

    @Override
    public String toString() {
        return this.modelType;
    }
}
