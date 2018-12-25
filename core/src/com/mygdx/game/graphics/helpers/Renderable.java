/*
 * Renderable.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * An interface to implement very simple VFX.
 */

package com.mygdx.game.graphics.helpers;

import com.mygdx.game.GameStore;

import java.util.Map;

public interface Renderable {
    boolean render(GameStore store, Map<String, Object> props);
}
