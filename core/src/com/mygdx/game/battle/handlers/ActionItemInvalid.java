/*
 * ActionItemInvalid.java
 *
 * Author: Roger Ngo
 * Copyright 2019
 *
 * Handles invalid items and takes the player back to the item picker.
 */

package com.mygdx.game.battle.handlers;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;

public class ActionItemInvalid extends BattleHandler {
    private float elapsedTime;
    private boolean visibleText;
    private int renderCycles;

    public ActionItemInvalid(GameStore store) {
        super(store);

        this.reset();
    }

    @Override
    public void handle(CombatStage stage) {
        String invalidItem = "Can't use\nitem!";

        this.elapsedTime += (Gdx.graphics.getDeltaTime() * 1000);

        // Flash at 10 fps (1/10) * 1000 == 100 ms per frame time.
        // Only flash for 4 times. The rest of the time should display message
        // at rest.
        if(this.elapsedTime >= 100 && this.renderCycles <= 4) {
            this.elapsedTime = 0;
            this.renderCycles++;
            this.visibleText = !this.visibleText;
        } else if(this.renderCycles > 4) {
            this.visibleText = true;
        }

        if(this.visibleText) {
            this.uiManager.getInformationalBox().setMessage(invalidItem);
        } else {
            this.uiManager.getInformationalBox().setMessage("");
        }
        this.uiManager.getInformationalBox().render();

        this.store.battleInteractionState.getTimer().run(() -> {
            this.reset();
            this.store.battleInteractionState.moveToStage(CombatStage.ActionItem);

            return true;
        });
    }

    private void reset() {
        this.elapsedTime = 0;
        this.visibleText = true;
        this.renderCycles = 0;
    }
}
