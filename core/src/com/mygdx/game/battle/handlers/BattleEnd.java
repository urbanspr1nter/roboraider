package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;

public class BattleEnd extends BattleHandler {
    public BattleEnd(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        this.store.battleInteractionState.moveToStage(CombatStage.BattleEndApplied);
    }
}
