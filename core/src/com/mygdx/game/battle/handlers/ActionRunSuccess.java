package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;

public class ActionRunSuccess extends BattleHandler {
    public ActionRunSuccess(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        String runMessage = "Ran\naway!";

        this.uiManager.getInformationalBox().setMessage(runMessage);
        this.uiManager.getInformationalBox().render();

        this.store.battleInteractionState.getTimer().run(() -> {
            this.store.battleInteractionState.moveToStage(CombatStage.BattleEndFromRun);
            this.store.battleInteractionState.getTimer().reset(500);
            return true;
        });
    }
}
