/*
 * BattleInvokable.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Interface to have the BattleStage easily invoke the logic
 * specific to each CombatStage.
 */

package com.mygdx.game.battle.contracts;

import com.mygdx.game.battle.CombatStage;

public interface BattleInvokable {
    void handle(CombatStage stage);
}
