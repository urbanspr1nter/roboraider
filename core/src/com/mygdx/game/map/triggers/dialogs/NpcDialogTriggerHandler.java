/*
 * NpcDialogTriggerHandler.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Dialog triggering for NPCs.
 */
package com.mygdx.game.map.triggers.dialogs;

import com.mygdx.game.GameStore;
import com.mygdx.game.objects.Dialog;
import com.mygdx.game.ui.DialogBoxPositions;
import com.mygdx.game.ui.enums.DialogBoxSize;

public class NpcDialogTriggerHandler extends DialogTriggerHandler {
    private GameStore gameStore;

    public NpcDialogTriggerHandler(GameStore store, Dialog dialog) {
        super(store.configuration, dialog);
        this.gameStore = store;
    }

    @Override
    public void trigger() {
        if(!this.gameStore.dialogChoiceState.getPrimaryDialog().visible()) {
            this.gameStore.dialogChoiceState.getPrimaryDialog().toggle();
        }

        this.gameStore.dialogChoiceState.getPrimaryDialog().display(
                this.currText,
                DialogBoxSize.Large,
                DialogBoxPositions.Large()
        );
    }
}
