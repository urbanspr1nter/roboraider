/*
 * BaseState.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Base state is the skeletal implementation of a basic state
 * which can be extended by other states.
 */

package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameStore;
import com.mygdx.game.GameStoreEntity;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.configuration.ConfigurationBuilder;
import com.mygdx.game.state.contracts.State;
import com.mygdx.game.ui.hud.BaseHud;
import com.mygdx.game.ui.hud.DebugHud;
import com.mygdx.game.ui.hud.LogoHud;

public abstract class BaseState implements State {
    protected GameStore store;
    protected GameStoreEntity entity;
    private BaseHud debugHud;
    private BaseHud logoHud;
    private Configuration config;

    BaseState(GameStore store, GameStoreEntity newEntity) {
        this.store = store;

        this.entity = newEntity;
        this.store.setEntity(newEntity);

        this.debugHud = new DebugHud(this.store);
        this.logoHud = new LogoHud(this.store);
        this.config = ConfigurationBuilder.config();
    }

    public void enter() {
        this.onEnter();

        // Set the active input adapter
        Gdx.input.setInputProcessor(this.store.currentEntity().getInputAdapter());
    }

    public void execute() {
        this.onExecute();

        // Invoke all renders that are mandatory which do not pertain
        // directly to the current state.
        if(this.config.Game.Debug) {
            this.debugHud.render();
        }

        if(this.config.Game.Watermark.Display) {
            this.logoHud.render();
        }


        // Invoke all the callbacks.
        this.store.callbackQueue.handleCalls();
    }

    public void exit() {
        // Handle all callbacks one last time.
        this.store.callbackQueue.handleCalls();

        this.onExit();
    }

    public void update() {
        this.onUpdate();
    }

    public GameStoreEntity getEntity() {
        return this.entity;
    }

    protected abstract void onEnter();
    protected abstract void onUpdate();
    protected abstract void onExecute();
    protected abstract void onExit();
}
