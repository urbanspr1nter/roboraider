package com.mygdx.game.character.items;

import com.mygdx.game.GameStore;
import com.mygdx.game.character.enums.EquipmentAreas;
import com.mygdx.game.character.items.contracts.Equippable;
import com.mygdx.game.character.items.enums.Name;

public class LifeRing extends Item implements Equippable {
    public LifeRing(GameStore store, Name name) {
        super(store, name);
    }

    @Override
    public EquipmentAreas equippableArea() {
        return EquipmentAreas.Accessory;
    }

    @Override
    public boolean use() {
        this.uiSounds.playCursorConfirm();
        this.store.playerData
                .getPlayerStatistics()
                .setMaximumHp(this.store.playerData.getPlayerStatistics().getMaximumHp() + 10);

        return true;
    }
}
