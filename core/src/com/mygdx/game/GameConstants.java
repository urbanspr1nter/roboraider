/*
 * GameConstants.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Random constants referenced by the game.
 */
package com.mygdx.game;

import com.mygdx.game.util.GameUtils;

public class GameConstants {
    public static int RenderDelay(int fps) {
        return GameUtils.delayInMs(fps);
    }
}
