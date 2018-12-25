package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;

import java.util.LinkedList;
import java.util.List;

public class BattleEndApplied extends BattleHandler {
    public BattleEndApplied(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        String infoString = "Victory!";

        this.uiManager.getInformationalBox().setMessage(infoString);
        this.uiManager.getInformationalBox().render();

        List<String> payload = new LinkedList<>();
        payload.add("Victorious!");

        this.uiManager.getUtilityBox().setData(payload);
        this.uiManager.getUtilityBox().render();
    }
}
