/*
 * Ether.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A concrete Ether implementation. Heals 20% of MP.
 */

package com.mygdx.game.character.items;

import com.mygdx.game.GameStore;
import com.mygdx.game.character.items.enums.Name;

public class Ether extends Item {
    public Ether(GameStore store, Name name) {
        super(store, name);
    }

    @Override
    public boolean use() {
        int mpUp = (int)Math.ceil(this.store.playerData.getPlayerStatistics().getMaximumMp() * 0.20);

        if(this.store.playerData.getPlayerStatistics().getCurrentMp()
                == this.store.playerData.getPlayerStatistics().getMaximumMp()) {
            this.uiSounds.playError();
            return false;
        }

        this.uiSounds.playCursorConfirm();

        if(this.store.playerData.getPlayerStatistics().getCurrentMp() + mpUp
                >= this.store.playerData.getPlayerStatistics().getMaximumMp()) {
            this.store.playerData
                    .getPlayerStatistics()
                    .setCurrentMp(this.store.playerData.getPlayerStatistics().getMaximumMp());
            return true;
        }

        this.store.playerData
                .getPlayerStatistics()
                .setCurrentMp(this.store.playerData.getPlayerStatistics().getCurrentMp() + mpUp);


        return true;
    }

    @Override
    public boolean isCombatable() {
        return true;
    }
}
