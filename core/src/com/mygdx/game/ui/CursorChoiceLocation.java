package com.mygdx.game.ui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CursorChoiceLocation {
    private Map<Integer, Point> mapping;

    public CursorChoiceLocation(int totalChoices, Point startingPoint) {
        this.mapping = new HashMap<Integer, Point>();

        // Construct the mapping
        int currX = startingPoint.x;
        int currY = startingPoint.y;

        for(int i = 0; i < totalChoices; i++) {
            this.mapping.put(i, new Point(currX, currY));
            currY -= 36;
        }
    }

    public Point getLocation(int currentChoice) {
        return this.mapping.get(currentChoice);
    }

    public int size() {
        return this.mapping.values().size();
    }
}
