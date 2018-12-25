package com.mygdx.game.battle.handlers;

import com.mygdx.game.GameStore;
import com.mygdx.game.battle.*;
import com.mygdx.game.character.enums.EquipmentAreas;
import com.mygdx.game.character.skills.Skill;
import com.mygdx.game.objects.Monster;

import java.util.LinkedList;
import java.util.List;

public class WaitingAction extends BattleHandler {
    public WaitingAction(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        // This stage specifies that we are:
        //  Waiting for the user to choose an action:
        //      Attack, Skill, Item, or Run.

        // Update informational box
        this.uiManager.getInformationalBox().setMessage("Take\naction!");
        this.uiManager.getInformationalBox().render();

        this.removeMonsters();

        // Go to victory if no monsters in playing field.
        if(this.store.battleInteractionState.getMonsters().size() == 0) {
            this.store.battleInteractionState.moveToStage(CombatStage.BattleEnd);
        } else {
            // Reset animations
            // -> Reset all skill animations
            // -> Reset all attack animations
            // -> Reset the timers
            for(Skill s : this.store.playerData.getSkills().values()) {
                if(s == null) {
                    continue;
                }
                s.getAnimation().reset();
            }
            this.store.playerData.getEquippedItemOnCharacter(EquipmentAreas.RightHand).getAnimation().reset();

            this.store.battleInteractionState.getDamageCounter().reset();

            // Reset all choices.
            this.store.battleInteractionState.setTarget(null);
            this.store.battleInteractionState.setTargettedSkill(null);
            this.store.battleInteractionState.setTargettedItem(null);

            this.store.battleInteractionState.resetUtilityBoxChoice();
            this.store.battleInteractionState.resetItemBoxChoice();

            this.store.battleInteractionState.getTimer().run(() -> {
                this.store.battleInteractionState.setCursorVisibility(true);
                this.store.battleInteractionState.getTimer().reset(500);
                return true;
            });

            this.uiHelper.updateCursor(stage);
            // Use this opportunity to do some pro-active garbage collection.
            System.gc();
        }
    }


    private void removeMonsters() {
        // Go through all monsters to see if any needs to be removed from the field.
        List<Monster> toRemove = new LinkedList<>();

        for(Monster m : this.store.battleInteractionState.getMonsters()) {
            if(m.getDeath() && m.isRemovalRenderComplete()) {
                m.playDefeatSound();
                this.store.battleInteractionState.setCurrentTotalExp(
                    this.store.battleInteractionState.getCurrentTotalExp()
                        + m.getProfile().getExp()
                );
                toRemove.add(m);
            }
        }

        for(Monster m : toRemove) {
            this.store.battleInteractionState.getMonsters().remove(m);
        }
    }
}
