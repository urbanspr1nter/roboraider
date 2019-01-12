package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;
import com.mygdx.game.character.skills.Skill;
import com.mygdx.game.objects.Monster;

public class ActionSkillTargetChosen extends BattleHandler {
    public ActionSkillTargetChosen(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        // Store the skill in the current state, and target to run the effect on.
        Skill skill = this.store.battleInteractionState.getTargettedSkill();

        if(this.store.battleInteractionState.getUtilityBoxChoice() == -1) {
            this.store.battleInteractionState.setTarget(this.store.playerData);
            this.store.battleInteractionState.getTimer().reset(250);
        } else {
            skill.playSound();

            Monster m = this.store.battleInteractionState.getMonsters()
                    .get(this.store.battleInteractionState.getUtilityBoxChoice());
            this.store.battleInteractionState.setTarget(m);

            skill.getAnimation().setLocation(m.getPosition());
        }
        this.store.battleInteractionState.moveToStage(CombatStage.ActionSkillApply);
    }
}
