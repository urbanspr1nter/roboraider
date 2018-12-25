package com.mygdx.game.character.items;

import com.mygdx.game.GameStore;
import com.mygdx.game.character.enums.EquipmentAreas;
import com.mygdx.game.character.items.contracts.Equippable;
import com.mygdx.game.character.items.enums.Name;

public class ClothArmor extends Item implements Equippable {
    public ClothArmor(GameStore store, Name name) {
        super(store, name);
    }

    @Override
    public EquipmentAreas equippableArea() {
        return EquipmentAreas.Chest;
    }

    @Override
    public boolean use() {
        this.uiSounds.playCursorConfirm();
        this.store.playerData
                .getPlayerStatistics()
                .setDefense(this.store.playerData.getPlayerStatistics().getDefense() + 3);
        this.store.playerData
                .getPlayerStatistics()
                .setVitality(this.store.playerData.getPlayerStatistics().getDefense() + 3);

        return true;
    }
}
