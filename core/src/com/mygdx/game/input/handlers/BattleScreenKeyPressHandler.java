package com.mygdx.game.input.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameStore;
import com.mygdx.game.audio.UiSounds;
import com.mygdx.game.battle.CombatStage;
import com.mygdx.game.character.items.Item;

import java.util.LinkedList;
import java.util.List;

public class BattleScreenKeyPressHandler extends DefaultKeyPressHandler {
    private static class CursorOn {
        final static int Attack = 0;
        final static int Skill = 1;
        final static int Item = 2;
        final static int Run = 3;
    }

    private UiSounds uiSounds;

    public BattleScreenKeyPressHandler(GameStore store) {
        super(store);
        this.uiSounds = new UiSounds(this.store);
    }

    @Override
    public void handleKeyPress(int keyCode) {
        // Handle debug key if needed to toggle debug mode on/off, and handle any "quick quits"
        this.handleDebugKey(keyCode);
        if(this.store.configuration.Game.Debug) {
            this.store.setCurrentKeyCode(keyCode);
            this.handleQuit();
        }

        CombatStage currentStage = this.store.battleInteractionState.currentStage();

        if(currentStage == CombatStage.WaitingAction) {
            this.handleWaitingActionKeyPress();
        } else if(currentStage == CombatStage.ActionAttack) {
            this.handleActionAttackKeyPress();
        } else if(currentStage == CombatStage.ActionSkill) {
            this.handleActionSkillKeyPress();
        } else if(currentStage == CombatStage.ActionSkillTarget) {
            this.handleActionSkillTargetKeyPress();
        } else if(currentStage == CombatStage.ActionItem) {
            this.handleActionItemKeyPress();
        } else if(currentStage == CombatStage.ActionItemTarget) {
            this.handleActionItemTargetKeyPress();
        } else if(currentStage == CombatStage.BattleEndApplied || currentStage == CombatStage.BattleEndExp
                || currentStage == CombatStage.BattleEndExpApplied || currentStage == CombatStage.BattleEndLevelUp
                || currentStage == CombatStage.BattleEndLevelUpApplied || currentStage == CombatStage.BattleEndItem
                || currentStage == CombatStage.BattleEndItemApplied) {
            this.handleBattleEnd();
        }
    }

    private void handleBattleEnd() {
        CombatStage currentStage = this.store.battleInteractionState.currentStage();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(currentStage == CombatStage.BattleEndApplied) {
                this.store.battleInteractionState.moveToStage(CombatStage.BattleEndExp);
            } else if(currentStage == CombatStage.BattleEndExpApplied) {
                this.store.battleInteractionState.moveToStage(CombatStage.BattleEndLevelUp);
            } else if(currentStage == CombatStage.BattleEndLevelUpApplied) {
                this.store.battleInteractionState.moveToStage(CombatStage.BattleEndItem);
            } else if(currentStage == CombatStage.BattleEndItemApplied) {
                this.store.battleInteractionState.moveToStage(CombatStage.BattleEndExit);
            }
        }
    }

    private void handleWaitingActionKeyPress() {
        if(!this.store.battleInteractionState.getCursorVisiblity()) {
            return;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(this.store.battleInteractionState.getActionChoice() <= 0) {
                this.uiSounds.playCursor();
                for(int i = 0; i < 3; i++) {
                    this.store.battleInteractionState.incrementActionChoice();
                }
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.decrementActionChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(this.store.battleInteractionState.getActionChoice() >= 3) {
                this.uiSounds.playCursor();
                for(int i = 3; i > 0; i--) {
                    this.store.battleInteractionState.decrementActionChoice();
                }
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.incrementActionChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.uiSounds.playCursorConfirm();

            if(this.store.battleInteractionState.getActionChoice() == CursorOn.Attack) {
                this.store.battleInteractionState.moveToStage(CombatStage.ActionAttack);
                this.store.battleInteractionState.setUtilityBoxChoiceToFirst();
            } else if(this.store.battleInteractionState.getActionChoice() == CursorOn.Skill) {
                this.store.battleInteractionState.moveToStage(CombatStage.ActionSkill);
                this.store.battleInteractionState.setUtilityBoxChoiceToFirst();
            } else if(this.store.battleInteractionState.getActionChoice() == CursorOn.Item) {
                this.store.battleInteractionState.moveToStage(CombatStage.ActionItem);
                this.store.battleInteractionState.setUtilityBoxChoiceToFirst();
            } else if(this.store.battleInteractionState.getActionChoice() == CursorOn.Run) {
                this.store.battleInteractionState.moveToStage(CombatStage.ActionRun);
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.uiSounds.playError();
        }
    }

    private void handleActionAttackKeyPress() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(this.store.battleInteractionState.getUtilityBoxChoice() <= -1) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.decrementUtilityBoxChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(this.store.battleInteractionState.getUtilityBoxChoice()
                    >= this.store.battleInteractionState.getMonsters().size() - 1) {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.resetUtilityBoxChoice();
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.incrementUtilityBoxChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(this.store.battleInteractionState.getUtilityBoxChoice() == -1) {
                this.store.battleInteractionState.setTarget(this.store.playerData);
            } else {
                this.store.battleInteractionState.setTarget(
                    this.store.battleInteractionState.getMonsters().get(this.store.battleInteractionState.getUtilityBoxChoice())
                );
            }
            this.store.battleInteractionState.moveToStage(CombatStage.ActionAttackApply);
        } else if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.uiSounds.playCancel();
            this.store.battleInteractionState.moveToStage(CombatStage.WaitingAction);
        }
    }

    private void handleActionSkillKeyPress() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(this.store.battleInteractionState.getUtilityBoxChoice() <= 0) {
                this.uiSounds.playCursor();
                for(int i = 0; i < 3; i++) {
                    this.store.battleInteractionState.incrementUtilityBoxChoice();
                }
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.decrementUtilityBoxChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(this.store.battleInteractionState.getUtilityBoxChoice()
                    >= this.store.playerData.getSkills().size() - 1) {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.resetUtilityBoxChoice();
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.incrementUtilityBoxChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.store.battleInteractionState.setTargettedSkill(
                this.store.playerData.getSkill(this.store.battleInteractionState.getUtilityBoxChoice())
            );
            if(this.store.battleInteractionState.getTargettedSkill() == null) {
                this.uiSounds.playError();
            } else {
                this.store.battleInteractionState.moveToStage(CombatStage.ActionSkillTarget);
                this.store.battleInteractionState.setUtilityBoxChoiceToFirst();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.uiSounds.playCancel();
            this.store.battleInteractionState.moveToStage(CombatStage.WaitingAction);
        }
    }

    private void handleActionSkillTargetKeyPress() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(this.store.battleInteractionState.getUtilityBoxChoice() <= -1) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.decrementUtilityBoxChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(this.store.battleInteractionState.getUtilityBoxChoice()
                    >= this.store.battleInteractionState.getMonsters().size() - 1) {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.resetUtilityBoxChoice();
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.incrementUtilityBoxChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.store.battleInteractionState.moveToStage(CombatStage.ActionSkillTargetChosen);
        } else if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.uiSounds.playCancel();
            this.store.battleInteractionState.moveToStage(CombatStage.WaitingAction);
        }
    }

    private void handleActionItemKeyPress() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(this.store.battleInteractionState.getItemBoxChoice() - 3 < 0) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.decrementItemBoxChoice(3);
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(this.store.battleInteractionState.getItemBoxChoice() + 3
                    > this.store.playerData.getInventory().keySet().size() - 1) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.incrementItemBoxChoice(3);
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if(this.store.battleInteractionState.getItemBoxChoice() <= 0) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.decrementItemBoxChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(this.store.battleInteractionState.getItemBoxChoice()
                    >= this.store.playerData.getInventory().keySet().size() - 1) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.incrementItemBoxChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            List<Item> currentItems = new LinkedList<>(this.store.playerData.getInventory().keySet());
            this.store.battleInteractionState.setTargettedItem(
                currentItems.get(this.store.battleInteractionState.getItemBoxChoice())
            );

            this.store.battleInteractionState.moveToStage(CombatStage.ActionItemTarget);
            this.store.battleInteractionState.setUtilityBoxChoiceToPlayer();
        } else if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.uiSounds.playCancel();
            this.store.battleInteractionState.moveToStage(CombatStage.WaitingAction);
        }
    }

    private void handleActionItemTargetKeyPress() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(this.store.battleInteractionState.getUtilityBoxChoice() <= -1) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.decrementUtilityBoxChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(this.store.battleInteractionState.getUtilityBoxChoice()
                    >= this.store.battleInteractionState.getMonsters().size() - 1) {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.resetUtilityBoxChoice();
            } else {
                this.uiSounds.playCursor();
                this.store.battleInteractionState.incrementUtilityBoxChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.store.battleInteractionState.moveToStage(CombatStage.ActionItemApply);
        } else if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.uiSounds.playCancel();
            this.store.battleInteractionState.moveToStage(CombatStage.WaitingAction);
        }
    }
}
