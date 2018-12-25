package com.mygdx.game.battle;

import com.mygdx.game.GameStore;

public class BattleUiSounds {
    private GameStore store;

    public BattleUiSounds(GameStore store) {
        this.store = store;
    }

    public void playWeaponSlice() {
        float volume = this.store.configuration.Assets.Registry.get("BattleSliceSound").Volume;

        this.store.soundLibrary.get("BattleSliceSound").play(volume);
    }

    public void playItemUsed() {
        float volume = this.store.configuration.Assets.Registry.get("ItemUsed").Volume;

        this.store.soundLibrary.get("ItemUsed").play(volume);
    }
}
