package com.mygdx.game.battle.handlers;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;
import com.mygdx.game.character.PlayerData;
import com.mygdx.game.character.skills.Skill;
import com.mygdx.game.graphics.GraphicsHelper;
import com.mygdx.game.graphics.helpers.FadeTransitionIn;
import com.mygdx.game.objects.Monster;
import com.mygdx.game.objects.contracts.Targetable;

public class ActionSkillApply extends BattleHandler {
    private GraphicsHelper gfxHelper;
    private boolean soundLock;

    public ActionSkillApply(GameStore store) {
        super(store);
        this.gfxHelper = new GraphicsHelper();
        this.soundLock = false;
    }

    @Override
    public void handle(CombatStage stage) {
        String skillUsed = "Skill used!";
        this.uiManager.getInformationalBox().setMessage(skillUsed);
        this.uiManager.getInformationalBox().render();

        Skill skill = this.store.battleInteractionState.getTargettedSkill();
        Targetable target = this.store.battleInteractionState.getTargetted();

        if(!(target instanceof Monster)) {
            this.store.battleInteractionState.getTimer().run(() -> {
                if(!this.soundLock) {
                    skill.playSound();
                    this.soundLock = true;
                }

                this.store.callbackQueue.register(() -> {
                    FadeTransitionIn fIn = new FadeTransitionIn();

                    boolean hasFadeResolved = fIn.render(this.store, this.gfxHelper.getFadeProps(2, Color.TEAL));

                    if(hasFadeResolved) {
                        this.soundLock = false;
                        this.store.playerData.applySkill(skill, this.store.playerData);

                        this.store.battleInteractionState.getTimer().reset(833);
                        this.store.battleInteractionState.moveToStage(CombatStage.DelayEnemyAttack);

                        return true;
                    }

                    return false;
                });

                return true;
            });
        } else {
            skill.animate(() -> {
                target.applySkill(this.store.battleInteractionState.getTargettedSkill(), this.store.playerData);

                this.store.playerData.getPlayerStatistics().setCurrentMp(this.store.playerData.getPlayerStatistics().getCurrentMp() - 2);
                this.store.battleInteractionState.popUtilityBoxChoice();
                this.store.battleInteractionState.moveToStage(CombatStage.ActionSkillApplyShowDamage);
            }, target);
        }
    }
}
