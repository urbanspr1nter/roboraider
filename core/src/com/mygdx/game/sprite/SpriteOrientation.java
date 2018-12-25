package com.mygdx.game.sprite;

public enum SpriteOrientation {
    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3);

    private int index;

    SpriteOrientation(int index) {
        this.index = index;
    }

    public int value() {
        return this.index;
    }

    @Override
    public String toString() {
        return String.valueOf(this.index);
    }
}
