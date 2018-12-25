/*
 * UiSounds.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A helper object to play various sounds which are needed throughout
 * the shared. Saves a lot of time in initialization for things such as
 * setting the volume, etc.
 */

package com.mygdx.game.audio;

import com.mygdx.game.GameStore;

public class UiSounds {
    private GameStore store;

    public UiSounds(GameStore store) {
        this.store = store;
    }

    public void playCancel() {
        float volume = this.store.configuration.Assets.Registry.get("CursorCancel").Volume;

        this.store.soundLibrary.get("CursorCancel").play(volume);
    }

    public void playError() {
        float volume = this.store.configuration.Assets.Registry.get("CursorError").Volume;

        this.store.soundLibrary.get("CursorError").play(volume);
    }

    public void playCursor() {
        float volume = this.store.configuration.Assets.Registry.get("CursorSound").Volume;

        this.store.soundLibrary.get("CursorSound").play(volume);
    }

    public void playCursorConfirm() {
        float volume = this.store.configuration.Assets.Registry.get("ConfirmSound").Volume;

        this.store.soundLibrary.get("ConfirmSound").play(volume);
    }
}
