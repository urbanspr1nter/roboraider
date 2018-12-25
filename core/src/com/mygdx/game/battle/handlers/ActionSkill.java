package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;

import java.util.LinkedList;

public class ActionSkill extends BattleHandler {
    public ActionSkill(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        // Display list of skills and allow the player to pick from the
        // list of skills they already knw.
        String whichSkill = "Use a skill.";
        this.uiManager.getInformationalBox().setMessage(whichSkill);
        this.uiManager.getInformationalBox().render();

        this.uiManager.getUtilityBox().setData(new LinkedList<>(this.store.playerData.getSkills().values()));
        this.uiManager.getUtilityBox().render();

        this.uiHelper.updateCursor(stage);
    }
}
