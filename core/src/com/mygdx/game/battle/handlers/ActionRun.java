package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;
import com.mygdx.game.objects.Monster;

public class ActionRun extends BattleHandler {
    public ActionRun(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        // Is this a boss fight?
        for(Monster m : this.store.battleInteractionState.getMonsters()) {
            if(m.isBoss()) {
                this.store.battleInteractionState.getTimer().reset(2000);

                this.store.battleInteractionState.moveToStage(CombatStage.ActionRunBoss);
                return;
            }
        }

        // Ok it isn't a boss fight, so let's see if we can actually run.
        // The math is , the attack and defense of the character must be at least 20% greater
        // than the average enemy's. Or in simpler terms, enemy's stats must be less than
        // 80% of the player's.
        int playerAttack = this.store.playerData.getPlayerStatistics().getAttack();
        int playerDefense = this.store.playerData.getPlayerStatistics().getDefense();

        float averageEnemyAttack = 0;
        float averageEnemyDefense = 0;
        for(Monster m : this.store.battleInteractionState.getMonsters()) {
            averageEnemyAttack += m.getProfile().getStrength();
            averageEnemyDefense += m.getProfile().getDefense();
        }

        averageEnemyAttack /= this.store.battleInteractionState.getMonsters().size();
        averageEnemyDefense /= this.store.battleInteractionState.getMonsters().size();

        float attackRatio = averageEnemyAttack / playerAttack;
        float defenseRatio = averageEnemyDefense / playerDefense;

        if(attackRatio < 0.8 && defenseRatio < 0.8) {
            this.store.battleInteractionState.moveToStage(CombatStage.ActionRunSuccess);
        } else {
            this.store.battleInteractionState.moveToStage(CombatStage.ActionRunInvalid);
        }

        this.store.battleInteractionState.getTimer().reset(2000);
    }
}
