package com.mygdx.game.battle.contracts;

import com.mygdx.game.battle.CombatStage;

public interface BattleInvokable {
    void handle(CombatStage stage);
}
