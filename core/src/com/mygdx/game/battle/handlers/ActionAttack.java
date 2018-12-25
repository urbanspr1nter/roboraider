package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;

public class ActionAttack extends BattleHandler {
    public ActionAttack(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        // This stage allows the player to choose from a list of monsters to
        // target.
        String whichTargetMessage = "Attack\nwho?";

        this.uiManager.getInformationalBox().setMessage(whichTargetMessage);
        this.uiManager.getInformationalBox().render();

        this.uiManager.getUtilityBox().setData(this.store.battleInteractionState.getMonsters());
        this.uiManager.getUtilityBox().render();

        this.uiHelper.updateCursor(stage);
    }


}
