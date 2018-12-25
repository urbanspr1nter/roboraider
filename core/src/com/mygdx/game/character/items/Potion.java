/*
 * Potion.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A concrete Potion implementation. Heals 20% of the HP.
 */

package com.mygdx.game.character.items;

import com.mygdx.game.GameStore;
import com.mygdx.game.character.items.enums.Name;

public class Potion extends Item {
    public Potion(GameStore store, Name name) {
        super(store, name);
    }

    @Override
    public boolean use() {
        int hpUp = (int)Math.ceil(this.store.playerData.getPlayerStatistics().getMaximumHp() * 0.20);

        if(this.store.playerData.getPlayerStatistics().getCurrentHp()
                == this.store.playerData.getPlayerStatistics().getMaximumHp()) {
            this.uiSounds.playError();
            return false;
        }

        this.uiSounds.playCursorConfirm();

        if(this.store.playerData.getPlayerStatistics().getCurrentHp() + hpUp
                >= this.store.playerData.getPlayerStatistics().getMaximumHp()) {
            this.store.playerData
                    .getPlayerStatistics()
                    .setCurrentHp(this.store.playerData.getPlayerStatistics().getMaximumHp());
            return true;
        }

        this.store.playerData
                .getPlayerStatistics()
                .setCurrentHp(this.store.playerData.getPlayerStatistics().getCurrentHp() + hpUp);

        return true;
    }

    @Override
    public boolean isCombatable() {
        return true;
    }
}
