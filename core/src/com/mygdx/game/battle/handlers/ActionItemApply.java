package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;
import com.mygdx.game.character.items.Item;

public class ActionItemApply extends BattleHandler {
    public ActionItemApply(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        Item item = this.store.battleInteractionState.getTargettedItem();

        if(this.store.battleInteractionState.getUtilityBoxChoice() == -1) {
            String usedItem = "Used\nitem!";

            this.uiManager.getInformationalBox().setMessage(usedItem);
            this.uiManager.getInformationalBox().render();

            if(!item.isCombatable()) {
                this.handleInvalidItem();
            } else {
                this.store.battleInteractionState.getTimer().run(() -> {
                    item.use();
                    this.battleUiSounds.playItemUsed();

                    this.store.battleInteractionState.popUtilityBoxChoice();
                    this.store.playerData.getInventory().put(item, this.store.playerData.getInventory().get(item) - 1);

                    if(this.store.playerData.getInventory().get(item) <= 0) {
                        this.store.playerData.getInventory().remove(item);
                    }

                    this.store.battleInteractionState.moveToStage(CombatStage.DelayEnemyAttack);
                    this.store.battleInteractionState.getTimer().reset(1000);

                    return true;
                });
            }
        } else {
            this.handleInvalidItem();
        }
    }

    private void handleInvalidItem() {
        this.uiSounds.playError();
        this.store.battleInteractionState.getTimer().reset(1000);
        this.store.battleInteractionState.moveToStage(CombatStage.ActionItemInvalid);
    }
}
