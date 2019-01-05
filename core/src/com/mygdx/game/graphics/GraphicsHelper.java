/*
 * GraphicsHelper.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Helper methods for graphics related things.
 */

package com.mygdx.game.graphics;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.graphics.helpers.TransitionProperties;

import java.util.HashMap;
import java.util.Map;

public class GraphicsHelper {
    public Map<String, Object> getFadeProps(int seconds, Color c) {
        Map<String, Object> props = new HashMap<>();
        props.put(TransitionProperties.Seconds, seconds);
        props.put(TransitionProperties.Color, c);

        return props;
    }
}
