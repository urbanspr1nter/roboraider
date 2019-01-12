package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;

public class ActionItemTarget extends BattleHandler {
    public ActionItemTarget(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        String whichTarget = "Use item\non who?";

        this.uiManager.getInformationalBox().setMessage(whichTarget);
        this.uiManager.getInformationalBox().render();

        this.uiManager.getUtilityBox().setData(this.store.battleInteractionState.getMonsters());
        this.uiManager.getUtilityBox().render();

        this.uiHelper.updateCursor(stage);

        this.store.battleInteractionState.getTimer().reset(500);
    }
}
