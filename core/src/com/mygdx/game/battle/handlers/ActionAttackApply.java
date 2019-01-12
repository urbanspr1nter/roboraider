package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;
import com.mygdx.game.character.enums.EquipmentAreas;
import com.mygdx.game.objects.Monster;

import java.awt.*;

public class ActionAttackApply extends BattleHandler {
    public ActionAttackApply(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        this.store.battleInteractionState.setCursorVisibility(false);

        if (this.store.battleInteractionState.getUtilityBoxChoice() == -1) {
            this.store.battleInteractionState.getTimer().reset(500);
            this.store.battleInteractionState.moveToStage(CombatStage.ActionAttackApplyComplete);
        } else {
            Monster m = (Monster)this.store.battleInteractionState.getTargetted();
            if(m != null) {
                Point monsterSize = m.getSize();
                Point monsterLocation = m.getPosition();
                Point animationPosition = new Point(
                    monsterLocation.x + (monsterSize.x / 2) - 72,
                    monsterLocation.y + (monsterSize.y / 2 - 72)
                );
                this.store.playerData.getEquippedItemOnCharacter(EquipmentAreas.RightHand)
                        .getAnimation()
                        .setLocation(animationPosition);
                this.store.playerData.getEquippedItemOnCharacter(EquipmentAreas.RightHand).animate(() -> {
                    this.store.playerData.getEquippedItemOnCharacter(EquipmentAreas.RightHand).playSound();

                    m.applyAttack(this.store.playerData);

                    this.store.battleInteractionState.moveToStage(CombatStage.ActionAttackApplyShowDamage);
                }, m);
            }
        }
    }
}
