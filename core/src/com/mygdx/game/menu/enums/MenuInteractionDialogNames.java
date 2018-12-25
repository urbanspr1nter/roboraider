package com.mygdx.game.menu.enums;

public enum MenuInteractionDialogNames {
    Main("Main"),
    Navigation("Navigation"),
    Time("Time"),
    Wallet("Wallet"),
    Items("Items"),
    Equipment("Equipment"),
    Skills("Skills"),
    Statistics("Statistics"),
    Configuration("Configuration"),
    Save("Save");

    private String value;

    MenuInteractionDialogNames(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
