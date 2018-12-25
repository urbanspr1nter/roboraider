package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;

public class BattleEndLevelUp extends BattleHandler {
    public BattleEndLevelUp(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        this.store.playerData.levelUp();
        this.store.battleInteractionState.moveToStage(CombatStage.BattleEndLevelUpApplied);
    }
}
