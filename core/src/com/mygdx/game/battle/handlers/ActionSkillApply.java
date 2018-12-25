package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;
import com.mygdx.game.character.skills.Skill;
import com.mygdx.game.objects.contracts.Targetable;

public class ActionSkillApply extends BattleHandler {
    public ActionSkillApply(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        String skillUsed = "Skill used!";
        this.uiManager.getInformationalBox().setMessage(skillUsed);
        this.uiManager.getInformationalBox().render();

        Skill skill = this.store.battleInteractionState.getTargettedSkill();
        Targetable target = this.store.battleInteractionState.getTargetted();

        skill.animate(() -> {
            target.applySkill(this.store.battleInteractionState.getTargettedSkill(), this.store.playerData);

            this.store.playerData.getPlayerStatistics().setCurrentMp(this.store.playerData.getPlayerStatistics().getCurrentMp() - 2);
            this.store.battleInteractionState.popUtilityBoxChoice();
            this.store.battleInteractionState.moveToStage(CombatStage.ActionSkillApplyShowDamage);
        }, target);
    }
}
