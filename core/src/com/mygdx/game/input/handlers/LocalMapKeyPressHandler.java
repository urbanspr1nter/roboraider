/*
 * LocalMapKeyPressHandler.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A key handler for LocalMap states.
 * It can be used for local maps, and even world maps.
 */

package com.mygdx.game.input.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Direction;
import com.mygdx.game.GameStore;
import com.mygdx.game.audio.UiSounds;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.dialog.DialogManager;
import com.mygdx.game.graphics.helpers.FadeTransitionIn;
import com.mygdx.game.input.CollisionDetector;
import com.mygdx.game.map.triggers.dialogs.DialogTriggerHandler;
import com.mygdx.game.state.MenuState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalMapKeyPressHandler extends DefaultKeyPressHandler {
    private Configuration configuration;
    private CollisionDetector collisionDetector;
    private DialogManager dialogManager;
    private UiSounds uiSounds;


    public LocalMapKeyPressHandler(GameStore store) {
        super(store);

        this.configuration = this.store.configuration;
        this.collisionDetector = new CollisionDetector(this.store, this.configuration);
        this.dialogManager = new DialogManager(this.store);

        this.uiSounds = new UiSounds(this.store);
    }

    @Override
    public void handleKeyPress(int keyCode) {
        this.store.setCurrentKeyCode(keyCode);

        this.handleDebugKey(keyCode);
        if(this.configuration.Game.Debug) {
            this.handleQuit();
        }

        if(!this.store.keyPressAllowed()) {
            return;
        }

        // Handle Dialog Stuff
        if(this.store.dialogChoiceState.getCursorVisible()) {
            if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if(this.store.dialogChoiceState.getCurrentCursorChoice() > 0) {
                    this.uiSounds.playCursor();
                    this.store.dialogChoiceState.decrementCursorChoice();
                }
            } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                this.uiSounds.playCursor();
                if(this.store.dialogChoiceState.getCurrentCursorChoice()
                        < this.store.npcInteractionState.getCurrentChoices().size() - 1) {
                    this.store.dialogChoiceState.incrementCursorChoice();
                }
            }

            this.store.dialogChoiceState.setCursorCellPosition(
                this.store.cursorChoiceLocation.getLocation(this.store.dialogChoiceState.getCurrentCursorChoice())
            );
        }

        if(this.dialogManager.isNpcInteraction()) {
            DialogTriggerHandler dHandler
                    = (DialogTriggerHandler)this.store.npcInteractionState.getCharacter().getTriggerHandler();

            if(this.dialogManager.isQuestion()) {
                List<String> choices = dHandler.currentDialog();
                dHandler.next(choices.get(0));

                if(dHandler.currentDialog().size() > 0) {
                    this.dialogManager.setupQuestionForPlayer();
                }
            } else if(this.dialogManager.isAnswerToQuestion()) {
                this.uiSounds.playCursorConfirm();

                this.dialogManager.processAnswerFromPlayer();
                // Handle if the next dialog is another question.
                if(dHandler.currentDialog().size() > 1) {
                    this.dialogManager.setupQuestionForPlayer();
                }
            } else {
                this.dialogManager.completeInteraction();
            }
        } else if(this.store.dialogChoiceState.getPrimaryDialog().visible()) {
            return;
        } else if(Gdx.input.isKeyPressed(Input.Keys.M)) {
            new UiSounds(this.store).playCursorConfirm();
            //this.store.stateMachine.exitState();
            this.store.callbackQueue.registerImmediate(() -> {
                Map<String, Object> props = new HashMap<>();
                props.put("color", Color.BLACK);
                props.put("seconds", 1);

                FadeTransitionIn ft = new FadeTransitionIn();
                if(ft.render(this.store, props)) {
                    return true;
                } else {
                    return false;
                }
            });
            this.store.menuInteractionState.setInteracting(true);
            this.store.stateMachine.setState("Menu", new MenuState(this.store, this.store.entityLibrary.get("MenuScreen")));
        } else {
            boolean hasCollision = this.hasCollision();

            if(!hasCollision) {
                if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    this.store.environmentAction = true;
                } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    this.store.currentEntity().getCameraManager().updateInputDirection(Direction.LEFT);
                    this.collisionDetector.setNextLocationNode(Direction.LEFT, this.store.currentEntity());
                    this.store.decrementSteps();
                } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    this.store.currentEntity().getCameraManager().updateInputDirection(Direction.RIGHT);
                    this.collisionDetector.setNextLocationNode(Direction.RIGHT, this.store.currentEntity());
                    this.store.decrementSteps();
                } else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    this.store.currentEntity().getCameraManager().updateInputDirection(Direction.UP);
                    this.collisionDetector.setNextLocationNode(Direction.UP, this.store.currentEntity());
                    this.store.decrementSteps();
                } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    this.store.currentEntity().getCameraManager().updateInputDirection(Direction.DOWN);
                    this.collisionDetector.setNextLocationNode(Direction.DOWN, this.store.currentEntity());
                    this.store.decrementSteps();
                } else if(this.store.debug && Gdx.input.isKeyPressed(Input.Keys.Z)) {
                    this.store.exitState = true;
                }
            } else {
                if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    if(this.store.npcInteractionState.getCharacter() != null) {
                        DialogTriggerHandler dHandler = (DialogTriggerHandler)this.store.npcInteractionState.getCharacter().getTriggerHandler();
                        if(dHandler.hasMoreDialog()) {
                            List<String> choices = dHandler.currentDialog();
                            dHandler.next(choices.get(0));
                            if(dHandler.currentDialog().size() > 1) {
                                this.dialogManager.setupQuestionForPlayer();
                            }
                        }
                        this.dialogManager.startInteraction();
                    }
                }
            }
        }

        this.store.currentEntity().updatePlayerPosition();
    }

    private boolean hasCollision() {
        return this.collisionDetector.hasObjectCollision() || this.collisionDetector.hasNpcCollision();
    }
}
