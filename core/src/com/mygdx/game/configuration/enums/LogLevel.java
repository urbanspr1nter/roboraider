package com.mygdx.game.configuration.enums;

public enum LogLevel {
    Debug("DEBUG"),
    Info("INFO"),
    Error("ERROR"),
    None("NONE");

    private String level;

    LogLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return this.level;
    }
}
