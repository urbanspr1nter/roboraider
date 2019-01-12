package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;

public class ActionSkillTarget extends BattleHandler {
    public ActionSkillTarget(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        // Skill chosen. Player should determine who to use skill on... player, or monster?
        String whichTarget = "On who?";

        this.uiManager.getInformationalBox().setMessage(whichTarget);
        this.uiManager.getInformationalBox().render();

        this.uiManager.getUtilityBox().setData(this.store.battleInteractionState.getMonsters());
        this.uiManager.getUtilityBox().render();

        this.uiHelper.updateCursor(stage);
    }
}
