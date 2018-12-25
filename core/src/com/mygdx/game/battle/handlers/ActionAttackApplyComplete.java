package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;

public class ActionAttackApplyComplete extends BattleHandler {
    public ActionAttackApplyComplete(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        if(this.store.playerData.getPlayerStatistics().getCurrentHp() == 0) {
            this.store.battleInteractionState.getTimer().run(() -> {
                this.store.battleInteractionState.moveToStage(CombatStage.BattleEndDeath);

                return true;
            });
        } else {
            this.store.battleInteractionState.getTimer().run(() -> {
                if(this.store.battleInteractionState.getTargetted().getDeath()) {
                    this.store.battleInteractionState.moveToStage(CombatStage.WaitingAction);
                } else {
                    this.store.battleInteractionState.moveToStage(CombatStage.EnemyAttack);
                }

                this.store.battleInteractionState.getTimer().reset(250);

                return true;
            });
        }
    }
}
