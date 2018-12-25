package com.mygdx.game.character.items;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleUiSounds;
import com.mygdx.game.character.enums.EquipmentAreas;
import com.mygdx.game.character.items.contracts.Equippable;
import com.mygdx.game.character.items.enums.Name;
import com.mygdx.game.graphics.animation.Animation;
import com.mygdx.game.graphics.animation.contracts.AnimationDoneCallback;
import com.mygdx.game.objects.contracts.Targetable;

public class IronSword extends Item implements Equippable {
    Animation slash;
    public IronSword(GameStore store, Name name) {
        super(store, name);
    }

    @Override
    public EquipmentAreas equippableArea() {
        return EquipmentAreas.RightHand;
    }

    @Override
    public boolean use() {
        this.uiSounds.playCursorConfirm();
        this.store.playerData
                .getPlayerStatistics()
                .setAttack(this.store.playerData.getPlayerStatistics().getDefense() + 2);
        this.store.playerData
                .getPlayerStatistics()
                .setAccuracy(this.store.playerData.getPlayerStatistics().getDefense() + 1);

        return true;
    }

    public void setAnimation(Animation animation) {
        this.slash = animation;
    }

    public Animation getAnimation() {
        return this.slash;
    }

    public void playSound() {
        BattleUiSounds uiSounds = new BattleUiSounds(this.store);
        uiSounds.playWeaponSlice();
    }

    @Override
    public void animate(AnimationDoneCallback callback, Targetable target) {
        this.slash.animate(callback, target);
    }
}
