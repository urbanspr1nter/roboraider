/*
 * DefaultKeyPressHandler.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Default key press handler which will normally be extended.
 */
package com.mygdx.game.input.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameStore;
import com.mygdx.game.input.handlers.contracts.KeyPressHandler;

public class DefaultKeyPressHandler implements KeyPressHandler {
    GameStore store;

    public DefaultKeyPressHandler(GameStore store) {
        this.store = store;
    }
    @Override
    public void handleKeyPress(int keyCode) {
        return;
    }

    protected void handleDebugKey(int keyCode) {
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.store.configuration.Game.Debug = !this.store.configuration.Game.Debug;
        }
    }

    // This is a shortcut to quit.
    protected void handleQuit() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
}
