package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;

import java.util.LinkedList;
import java.util.List;

public class BattleEndExpApplied extends BattleHandler {
    public BattleEndExpApplied(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        // Display the acquired EXP message if there was any EXP gained.
        // Otherwise, move to next stage.
        if(this.store.battleInteractionState.getCurrentTotalExp() == 0) {
            this.store.battleInteractionState.moveToStage(CombatStage.BattleEndLevelUp);
        } else {
            String infoString = "Victory!";

            this.uiManager.getInformationalBox().setMessage(infoString);
            this.uiManager.getInformationalBox().render();

            List<String> payload = new LinkedList<>();
            payload.add("Received " + this.store.battleInteractionState.getCurrentTotalExp() + " EXP.");

            this.uiManager.getUtilityBox().setData(payload);
            this.uiManager.getUtilityBox().render();
        }
    }
}
