package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;

public class BattleEndExp extends BattleHandler {
    public BattleEndExp(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        this.store.playerData.getPlayerStatistics().setExp(
                this.store.playerData.getPlayerStatistics().getCurrentExp()
                        + this.store.battleInteractionState.getCurrentTotalExp()
        );
        this.store.battleInteractionState.moveToStage(CombatStage.BattleEndExpApplied);
    }
}
