/*
 * BattleState.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * The main battle state controller.
 */

package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameStore;
import com.mygdx.game.GameStoreEntity;
import com.mygdx.game.battle.*;
import com.mygdx.game.battle.handlers.*;
import com.mygdx.game.logging.Logger;
import com.mygdx.game.objects.Monster;

import java.util.*;

public class BattleState extends BaseState {
    private Texture battleBgImage;
    private Texture selectionArrow;

    private Map<CombatStage, BattleHandler> handlers;


    public BattleState(GameStore store, GameStoreEntity newEntity) {
        super(store, newEntity);

        // Ensure that each battle obtains a new state.
        //this.store.battleInteractionState = new BattleInteractionState(this.store);

        this.store.battleInteractionState.setBackgroundMusic("DefaultBattleBgm");
        this.store.battleInteractionState.setVictoryBackgroundMusic("VictoryFanfare");
        this.battleBgImage = this.store.battleInteractionState.getCurrentBackground();
        this.selectionArrow = new Texture(this.store.configuration.Assets.Registry.get("CombatSelectionArrow").File);

        // Register all the handlers
        this.handlers = new HashMap<>();
        this.handlers.put(CombatStage.WaitingAction, new WaitingAction(this.store));
        this.handlers.put(CombatStage.Done, new DoneAction(this.store));
        this.handlers.put(CombatStage.DelayEnemyAttack, new DelayEnemyAttack(this.store));
        this.handlers.put(CombatStage.EnemyAttack, new EnemyAttack(this.store));
        this.handlers.put(CombatStage.ActionAttack, new ActionAttack(this.store));
        this.handlers.put(CombatStage.ActionAttackApply, new ActionAttackApply(this.store));
        this.handlers.put(CombatStage.ActionAttackApplyShowDamage, new ActionAttackApplyShowDamage(this.store));
        this.handlers.put(CombatStage.ActionAttackApplyComplete, new ActionAttackApplyComplete(this.store));
        this.handlers.put(CombatStage.ActionSkill, new ActionSkill(this.store));
        this.handlers.put(CombatStage.ActionSkillTarget, new ActionSkillTarget(this.store));
        this.handlers.put(CombatStage.ActionSkillTargetChosen, new ActionSkillTargetChosen(this.store));
        this.handlers.put(CombatStage.ActionSkillApply, new ActionSkillApply(this.store));
        this.handlers.put(CombatStage.ActionSkillApplyShowDamage, new ActionSkillApplyShowDamage(this.store));
        this.handlers.put(CombatStage.ActionSkillApplyComplete, new ActionSkillApplyComplete(this.store));
        this.handlers.put(CombatStage.ActionItem, new ActionItem(this.store));
        this.handlers.put(CombatStage.ActionItemTarget, new ActionItemTarget(this.store));
        this.handlers.put(CombatStage.ActionItemApply, new ActionItemApply(this.store));
        this.handlers.put(CombatStage.ActionItemInvalid, new ActionItemInvalid(this.store));
        this.handlers.put(CombatStage.ActionRun, new ActionRun(this.store));
        this.handlers.put(CombatStage.ActionRunBoss, new ActionRunBoss(this.store));
        this.handlers.put(CombatStage.ActionRunSuccess, new ActionRunSuccess(this.store));
        this.handlers.put(CombatStage.ActionRunInvalid, new ActionRunInvalid(this.store));
        this.handlers.put(CombatStage.BattleEndFromRun, new BattleEndFromRun(this.store));
        this.handlers.put(CombatStage.BattleEnd, new BattleEnd(this.store));
        this.handlers.put(CombatStage.BattleEndDeath, null);
        this.handlers.put(CombatStage.BattleEndApplied, new BattleEndApplied(this.store));
        this.handlers.put(CombatStage.BattleEndExp, new BattleEndExp(this.store));
        this.handlers.put(CombatStage.BattleEndExpApplied, new BattleEndExpApplied(this.store));
        this.handlers.put(CombatStage.BattleEndLevelUp, new BattleEndLevelUp(this.store));
        this.handlers.put(CombatStage.BattleEndLevelUpApplied, new BattleEndLevelUpApplied(this.store));
        this.handlers.put(CombatStage.BattleEndItem, new BattleEndItem(this.store));
        this.handlers.put(CombatStage.BattleEndItemApplied, new BattleEndItemApplied(this.store));
        this.handlers.put(CombatStage.BattleEndExit, new BattleEndExit(this.store));
        this.handlers.put(CombatStage.Exited, null);
    }

    @Override
    protected void onExecute() {
        if(this.store.battleInteractionState.currentStage() != CombatStage.Exited) {
            this.renderBackgroundAndMonsters(this.store.battleInteractionState.currentStage());
            ((BattleUiManager)this.store.battleInteractionState.getServiceContainer()
                    .get(BattleUiManager.class))
                    .getPlayerInformationHeader()
                    .render();
            ((BattleUiManager)this.store.battleInteractionState.getServiceContainer()
                    .get(BattleUiManager.class))
                    .getActionChoiceBox()
                    .render();
            this.handlers.get(this.store.battleInteractionState.currentStage())
                    .handle(this.store.battleInteractionState.currentStage());
            this.renderEnemySelectionArrow(this.store.battleInteractionState.currentStage());
        }
    }

    private void renderBackgroundAndMonsters(CombatStage stage) {
        this.store.spriteBatch.begin();
        this.store.spriteBatch.draw(
            this.battleBgImage, 0, 0, this.store.configuration.ViewPort.Width, this.store.configuration.ViewPort.Height
        );
        this.store.spriteBatch.end();

        int i = 0;
        for (Monster m : this.store.battleInteractionState.getMonsters()) {
            if(i == this.store.battleInteractionState.getEnemyIndex()) {
                m.render(stage, true);
            } else {
                m.render(stage, false);
            }

            i++;
        }
    }

    private void renderEnemySelectionArrow(CombatStage stage) {
        if(stage == CombatStage.ActionAttack && this.store.battleInteractionState.getUtilityBoxChoice() != -1) {
            Monster m = this.store.battleInteractionState
                    .getMonsters()
                    .get(this.store.battleInteractionState.getUtilityBoxChoice());
            this.store.spriteBatch.begin();
            this.store.spriteBatch.draw(
                    this.selectionArrow,
                    m.getPosition().x + (m.getSize().x / 2.0f),
                    m.getPosition().y + m.getSize().y,
                    this.selectionArrow.getWidth(),
                    this.selectionArrow.getHeight()
            );
            this.store.spriteBatch.end();
        }
    }

    @Override
    protected void onEnter() {
        Logger.log("Entering BattleState.");
        if (this.store.battleInteractionState.getCurrentBackgroundMusic() != null) {
            this.store.battleInteractionState.getCurrentBackgroundMusic().play();
        }
    }

    @Override
    protected void onUpdate() {
    }

    @Override
    protected void onExit() {
        if(this.store.battleInteractionState.getCurrentBackgroundMusic() != null) {
            this.store.battleInteractionState.getCurrentBackgroundMusic().stop();
            this.store.battleInteractionState.getCurrentBackgroundMusic().dispose();
        }
        if(this.store.battleInteractionState.getCurrentVictoryBgm() != null) {
            this.store.battleInteractionState.getCurrentVictoryBgm().stop();
            this.store.battleInteractionState.getCurrentVictoryBgm().dispose();
        }
    }
}
