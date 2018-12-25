package com.mygdx.game.input.enums;

public enum HandlerType {
    DefaultKeyPressHandler("DefaultKeyPressHandler"),
    LocalMapKeyPressHandler("LocalMapKeyPressHandler"),
    TitleScreenKeyPressHandler("TitleScreenKeyPressHandler");

    private String handlerType;

    HandlerType(String value) {
        this.handlerType = value;
    }

    @Override
    public String toString() {
        return this.handlerType;
    }
}
