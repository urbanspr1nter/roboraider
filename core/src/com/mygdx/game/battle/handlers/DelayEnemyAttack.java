package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;

public class DelayEnemyAttack extends BattleHandler {
    public DelayEnemyAttack(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        this.store.battleInteractionState.getTimer().run(() -> {
            this.store.battleInteractionState.getTimer().reset(500);
            this.store.battleInteractionState.moveToStage(CombatStage.EnemyAttack);
            return true;
        });
    }
}
