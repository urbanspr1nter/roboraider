package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;
import com.mygdx.game.objects.Monster;

public class ActionSkillApplyShowDamage extends BattleHandler {
    public ActionSkillApplyShowDamage(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        Monster m = (Monster)this.store.battleInteractionState.getTargetted();

        if(!this.store.battleInteractionState.getDamageCounter().hasStarted()) {
            int damage = m.getSkillDamage(this.store.playerData);
            this.uiManager.getInformationalBox()
                    .setMessage(this.uiHelper.getSkillDamageMessage(damage, m.getProfile().getName()));

            this.store.battleInteractionState.getDamageCounter().setLocation(m.getPosition());
            this.store.battleInteractionState.getDamageCounter().setValue(Integer.toString(damage));
            this.store.battleInteractionState.getDamageCounter().reset();

            // Give about 333 ms to transition to the next stage.
            this.store.battleInteractionState.getTimer().reset(333);
        }

        this.store.battleInteractionState.getDamageCounter().animate(() -> {
            this.store.battleInteractionState.getTimer().run(() -> {
                this.store.battleInteractionState.moveToStage(CombatStage.ActionSkillApplyComplete);

                // Reset the timer for the next stage. Give 500ms before an immediate transition.
                this.store.battleInteractionState.getTimer().reset(500);
                return true;
            });
        }, m);

        this.uiManager.getInformationalBox().render();
    }
}
