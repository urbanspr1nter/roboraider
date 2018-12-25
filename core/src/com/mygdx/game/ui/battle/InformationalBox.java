/*
 * InformationalBox.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Handles the display of informational content during battle. This is the small
 * box at the left-hand corner of the screen.
 */

package com.mygdx.game.ui.battle;

import com.mygdx.game.GameStore;
import com.mygdx.game.ui.DialogBox;
import com.mygdx.game.ui.DialogBoxPositions;
import com.mygdx.game.ui.enums.DialogBoxSize;

public class InformationalBox {
    private final static String DefaultMessage = "Take\naction!";

    private GameStore store;
    private DialogBox dialogBox;
    private String message;

    public InformationalBox(GameStore store) {
        this.store = store;
        this.dialogBox = new DialogBox(this.store.configuration);
    }

    public void render() {
        if(!this.dialogBox.visible()) {
            this.dialogBox.toggle();
        }

        this.dialogBox.display(this.getMessage(), DialogBoxSize.SmallHeader, DialogBoxPositions.SmallHeaderTop());
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String getMessage() {
        if(this.message != null) {
            return this.message;
        }

        return DefaultMessage;
    }
}
