/*
 * ConfirmationBox.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A simple confirmation box to be rendered on the middle of
 * the screen. Lasts for about 800ms by default.
 */

package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameStore;
import com.mygdx.game.ui.enums.DialogBoxSize;

public class ConfirmationBox {
    private static int TIME_DISPLAYED_MS = 800;

    private GameStore store;
    private float elapsedTime;
    private String message;
    private DialogBox dialogBox;
    private Texture dimScreenTexture;

    public ConfirmationBox(GameStore store, String message) {
        this.store = store;
        this.message = message;
        this.dialogBox = new DialogBox(this.store.configuration);
        this.dimScreenTexture = new Texture(
            Gdx.files.internal(this.store.configuration.Assets.Registry.get("FullScreenDimmedTexture").File)
        );
    }

    public boolean isValid() {
        return this.message != null;
    }

    public void setMessageAndReset(String message) {
        this.message = message;
        this.elapsedTime = 0;
    }

    public void render() {
        if(this.elapsedTime <= TIME_DISPLAYED_MS) {
            if(!this.dialogBox.visible()) {
                this.dialogBox.toggle();
            }

            this.dimScreen();

            this.dialogBox.display(this.message, DialogBoxSize.Small, DialogBoxPositions.SmallCenter());
            this.elapsedTime += (Gdx.graphics.getDeltaTime() * 1000);
        } else {
            this.dialogBox.toggle();
            this.message = null;
        }
    }

    private void dimScreen() {
        this.store.spriteBatch.begin();
        this.store.spriteBatch.draw(
            this.dimScreenTexture,
            0, 0,
            this.store.configuration.ViewPort.Width, this.store.configuration.ViewPort.Height
        );
        this.store.spriteBatch.end();
    }
}
