/*
 * TitleScreenKeyPressHandler.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Handles the input for the title screen.
 * Cursor and menu event handling are taken care of in here too.
 */

package com.mygdx.game.input.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameStore;
import com.mygdx.game.audio.UiSounds;
import com.mygdx.game.state.LocalMapState;
import com.mygdx.game.ui.Cursor;

import java.awt.*;

public class TitleScreenKeyPressHandler extends DefaultKeyPressHandler {
    private UiSounds uiSounds;
    private Point firstOptionLocation;
    private Point secondOptionLocation;

    public TitleScreenKeyPressHandler(GameStore store) {
        super(store);
        this.uiSounds = new UiSounds(this.store);

        this.firstOptionLocation = new Point(250, 155);
        this.secondOptionLocation = new Point(250, 115);

        this.store.dialogChoiceState.setCursor(new Cursor(this.store.configuration, this.store.spriteBatch));
        this.store.dialogChoiceState.setCursorCellPosition(this.firstOptionLocation);
    }

    @Override
    public void handleKeyPress(int keyCode) {
        // Allow debug toggling.
        super.handleDebugKey(keyCode);

        if(this.store.configuration.Game.Debug) {
            this.store.setCurrentKeyCode(keyCode);

            // ESC trap to quickly get out
            super.handleQuit();
        }

        if(!this.store.dialogChoiceState.getCursorVisible()) {
            return;
        }

        if(this.store.dialogChoiceState.getCursorVisible()) {
            if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                this.uiSounds.playCursor();
                if(this.store.dialogChoiceState.getCurrentCursorChoice() > 0) {
                    this.store.dialogChoiceState.decrementCursorChoice();
                    this.store.dialogChoiceState.setCursorCellPosition(this.firstOptionLocation);
                }
            } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                this.uiSounds.playCursor();
                if(this.store.dialogChoiceState.getCurrentCursorChoice() >= 0) {
                    this.store.dialogChoiceState.incrementCursorChoice();
                    this.store.dialogChoiceState.setCursorCellPosition(this.secondOptionLocation);
                }
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.uiSounds.playCursorConfirm();
            this.store.dialogChoiceState.setCursorVisible(false);
            if(this.store.dialogChoiceState.getCursorCellPosition().y == this.firstOptionLocation.y) {
                this.store.stateMachine.exitState();

                // Set the state to the desert state.
                this.store.stateMachine.setState(
                    "Desert",
                    new LocalMapState(this.store, this.store.entityLibrary.get("Desert"))
                );
            } else {
                Gdx.app.exit();
            }
        }
    }
}
