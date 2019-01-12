package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;

public class ActionRunBoss extends BattleHandler {
    public ActionRunBoss(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        this.uiManager.getInformationalBox().setMessage("Can't run!");
        this.uiManager.getInformationalBox().render();

        this.store.battleInteractionState.getTimer().run(() -> {
            this.store.battleInteractionState.moveToStage(CombatStage.EnemyAttack);
            this.store.battleInteractionState.getTimer().reset(500);
            return true;
        });
    }
}
