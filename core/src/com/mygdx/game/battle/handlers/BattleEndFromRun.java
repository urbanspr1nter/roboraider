package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;

public class BattleEndFromRun extends BattleHandler {
    public BattleEndFromRun(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        this.store.battleInteractionState.getTimer().run(() -> {
            this.store.battleInteractionState.moveToStage(CombatStage.Done);
            return true;
        });
    }
}
