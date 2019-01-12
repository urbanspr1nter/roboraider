package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;

public class BattleEndExit extends BattleHandler {
    public BattleEndExit(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        this.store.battleInteractionState.moveToStage(CombatStage.Done);

        // Use this opportunity to do some pro-active garbage collection.
        System.gc();
    }
}
