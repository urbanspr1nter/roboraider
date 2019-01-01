package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;

public class ActionItem extends BattleHandler {
    public ActionItem(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        String whichItem = "Which item\nto use?";

        this.uiManager.getInformationalBox().setMessage(whichItem);
        this.uiManager.getInformationalBox().render();

        this.store.battleInteractionState.setUtilityBoxChoiceToFirst();
        this.uiManager.getItemBox().setData(this.store.playerData.getInventory());
        this.uiManager.getItemBox().render();

        this.uiHelper.updateCursor(stage);
    }
}
