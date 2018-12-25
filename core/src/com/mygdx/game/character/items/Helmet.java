package com.mygdx.game.character.items;

import com.mygdx.game.GameStore;
import com.mygdx.game.character.enums.EquipmentAreas;
import com.mygdx.game.character.items.contracts.Equippable;
import com.mygdx.game.character.items.enums.Name;

public class Helmet extends Item implements Equippable {
    public Helmet(GameStore store, Name name) {
        super(store, name);
    }

    @Override
    public boolean use() {
        this.uiSounds.playCursorConfirm();
        this.store.playerData
                .getPlayerStatistics()
                .setDefense(this.store.playerData.getPlayerStatistics().getDefense() + 2);
        return true;
    }

    @Override
    public EquipmentAreas equippableArea() {
        return EquipmentAreas.Head;
    }
}
