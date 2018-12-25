/*
 * Direction.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Enum for Directions: UP, DOWN, LEFT, RIGHT
 */

package com.mygdx.game;

public enum Direction {
    UP("UP"),
    DOWN("DOWN"),
    LEFT("LEFT"),
    RIGHT("RIGHT");

    private String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return this.direction;
    }
}
