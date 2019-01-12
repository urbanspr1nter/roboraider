package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;

public class ActionRunInvalid extends BattleHandler {
    public ActionRunInvalid(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        String cantRun = "Can't\nrun!";

        this.uiManager.getInformationalBox().setMessage(cantRun);
        this.uiManager.getInformationalBox().render();

        this.store.battleInteractionState.getTimer().run(() -> {
            this.store.battleInteractionState.moveToStage(CombatStage.EnemyAttack);
            this.store.battleInteractionState.getTimer().reset(500);
            return true;
        });
    }
}
