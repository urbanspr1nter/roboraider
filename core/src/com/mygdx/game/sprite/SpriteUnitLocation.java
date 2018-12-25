package com.mygdx.game.sprite;

import java.awt.*;

public class SpriteUnitLocation {
    public Point start;
    public Point end;

    public SpriteUnitLocation(Point start) {
        this.start = start;
    }

    public SpriteUnitLocation(Point start, Point end) {
        this.start= start;
        this.end = end;
    }
}
