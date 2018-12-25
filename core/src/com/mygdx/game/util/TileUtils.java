/*
 * TileUtils.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Basic tile manipulation utilities.
 */

package com.mygdx.game.util;

import com.mygdx.game.configuration.Configuration;

import java.awt.*;

public class TileUtils {
    public static Point getCellFromRealLocation(Configuration configuration, Point realLocation) {
        return new Point(
            realLocation.x / configuration.Map.Unit.Width,
            realLocation.y / configuration.Map.Unit.Height
        );
    }

    public static Point getRealLocationFromCell(Configuration configuration, Point cellLocation) {
        return new Point(
            cellLocation.x * configuration.Map.Unit.Width,
            cellLocation.y * configuration.Map.Unit.Height
        );
    }
}
