package com.mygdx.game.sprite;

public enum SpriteDirection {
    START("START"),
    UP_0("UP-0"),
    UP_1("UP-1"),
    UP_2("UP-2"),
    UP_3("UP-3"),
    DOWN_0("DOWN-0"),
    DOWN_1("DOWN-1"),
    DOWN_2("DOWN-2"),
    DOWN_3("DOWN-3"),
    LEFT_0("LEFT-0"),
    LEFT_1("LEFT-1"),
    LEFT_2("LEFT-2"),
    LEFT_3("LEFT-3"),
    RIGHT_0("RIGHT-0"),
    RIGHT_1("RIGHT-1"),
    RIGHT_2("RIGHT-2"),
    RIGHT_3("RIGHT-3");

    private String direction;

    SpriteDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return this.direction;
    }
}
