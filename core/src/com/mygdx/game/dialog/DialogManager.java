/*
 * DialogManager.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Manages the NPC dialog interaction.
 */

package com.mygdx.game.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameStore;
import com.mygdx.game.map.triggers.dialogs.DialogTriggerHandler;
import com.mygdx.game.sprite.SpriteOrientation;
import com.mygdx.game.ui.CursorChoiceLocation;
import com.mygdx.game.ui.CursorPlacement;
import com.mygdx.game.ui.DialogBox;
import com.mygdx.game.ui.enums.GridPosition;

public class DialogManager {
    private GameStore store;

    public DialogManager(GameStore store) {
        this.store = store;
    }

    public void startInteraction() {
        this.store.npcInteractionState.setInteraction(true);
        this.updateNpcPosition(this.store.npcInteractionState.getOrientation());
    }

    public void setupQuestionForPlayer() {
        DialogTriggerHandler dHandler
                = (DialogTriggerHandler)this.store.npcInteractionState.getCharacter().getTriggerHandler();

        this.store.npcInteractionState.setQuestion(dHandler.currentText());
        this.store.npcInteractionState.setCurrentChoices(dHandler.currentDialog());

        this.store.dialogChoiceState.setPrimaryDialog(new DialogBox(this.store.configuration));
        this.store.dialogChoiceState.getChoiceDialog().toggle();
        this.store.dialogChoiceState.setCursorVisible(true);
        // When the cursor becomes invisible we need to create a new cursor choice location object
        this.store.cursorChoiceLocation = new CursorChoiceLocation(
                dHandler.currentDialog().size(),
                CursorPlacement.GetStartLocation(GridPosition.RightBottom, dHandler.currentDialog().size())
        );

        this.store.dialogChoiceState.setCursorCellPosition(
            CursorPlacement.GetStartLocation(GridPosition.RightBottom, dHandler.currentDialog().size())
        );
    }

    public void processAnswerFromPlayer() {
        DialogTriggerHandler dHandler
                = (DialogTriggerHandler)(this.store.npcInteractionState.getCharacter()).getTriggerHandler();
        for(int i = 0; i < this.store.cursorChoiceLocation.size(); i++) {
            if(this.store.dialogChoiceState.getCursorCellPosition().y
                    == this.store.cursorChoiceLocation.getLocation(i).y) {
                dHandler.next(dHandler.currentDialog().get(i));
                break;
            }
        }

        dHandler.next(dHandler.currentDialog().get(0));

        this.store.dialogChoiceState.setCursorVisible(false);
        this.store.dialogChoiceState.getChoiceDialog().toggle();
        this.store.dialogChoiceState.resetCursorChoice();

        this.store.npcInteractionState.setQuestion(null);
        this.store.npcInteractionState.setCurrentChoices(null);
    }

    public void completeInteraction() {
        DialogTriggerHandler dHandler
                = (DialogTriggerHandler)this.store.npcInteractionState.getCharacter().getTriggerHandler();

        this.store.dialogChoiceState.getPrimaryDialog().toggle();
        dHandler.resetDialog();
        this.updateNpcPosition(SpriteOrientation.DOWN);
        this.store.npcInteractionState.resetInteractionState();
    }

    public boolean isAnswerToQuestion() {
        DialogTriggerHandler dHandler
                = (DialogTriggerHandler)(this.store.npcInteractionState.getCharacter()).getTriggerHandler();

        return dHandler.currentText().equals(this.store.npcInteractionState.getQuestion());
    }

    public boolean isQuestion() {
        DialogTriggerHandler dHandler
                = (DialogTriggerHandler)(this.store.npcInteractionState.getCharacter()).getTriggerHandler();

        return dHandler.hasMoreDialog()
                    && !dHandler.currentText().equals(this.store.npcInteractionState.getQuestion());
    }


    public boolean isNpcInteraction() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE)
                    && this.store.dialogChoiceState.getPrimaryDialog().visible();
    }

    private void updateNpcPosition(SpriteOrientation orientation) {
        this.store.npcInteractionState.getCharacter().getSpriteData().setOrientation(orientation);
        this.store.currentEntity()
                .getSpriteRegistry()
                .updateOrientation(
                        this.store.npcInteractionState.getCharacter(),
                        this.store.npcInteractionState.getCharacter().getSpriteData().getOrientation()
                );
    }
}
