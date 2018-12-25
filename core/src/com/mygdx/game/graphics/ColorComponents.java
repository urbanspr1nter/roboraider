/*
 * ColorComponents.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A simple table of pre-defined color components.
 *
 * The predefined calculations of the components for each color makes
 * it easier so that we can just reference the color by name, or even
 * by its component.
 */

package com.mygdx.game.graphics;

import com.badlogic.gdx.graphics.Color;

public class ColorComponents {
    public static Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    public static Color RED = new Color(0.9373f, 0.0549f, 0.0549f, 1.0f);
    public static Color ORANGE = new Color(0.9373f, 0.5686f, 0.0549f, 1.0f);
    public static Color YELLOW = new Color(0.9373f, 0.9216f, 0.0549f, 1.0f);
    public static Color GREEN = new Color(0.0549f, 0.9373f, 0.1567f, 1.0f);
    public static Color BLUE = new Color(0.0549f, 0.0549f, 0.9373f, 1.0f);
    public static Color PURPLE = new Color(0.6157f, 0.0549f, 0.9373f, 1.0f);
}

