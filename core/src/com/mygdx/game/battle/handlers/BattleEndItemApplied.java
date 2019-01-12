package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;

import java.util.LinkedList;
import java.util.List;

public class BattleEndItemApplied extends BattleHandler {
    public BattleEndItemApplied(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        String infoString = "Victory!";

        this.uiManager.getInformationalBox().setMessage(infoString);
        this.uiManager.getInformationalBox().render();

        List<String> payload = new LinkedList<>();
        payload.add("Received POTION.");

        this.uiManager.getUtilityBox().setData(payload);
        this.uiManager.getUtilityBox().render();
    }
}
