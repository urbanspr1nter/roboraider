/*
 * BaseHud.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A skeletal HUD class meant to be extended.
 */

package com.mygdx.game.ui.hud;

import com.mygdx.game.GameStore;

public abstract class BaseHud implements Hud {
    protected GameStore store;

    public BaseHud(GameStore store) {
        this.store = store;
    }

    public void render() {
        this.store.spriteBatch.begin();
        this.onRender();
        this.store.spriteBatch.end();
    }

    public abstract void onRender();
}
