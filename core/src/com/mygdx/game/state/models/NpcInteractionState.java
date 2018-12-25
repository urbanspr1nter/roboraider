/*
 * NpcInteractionState.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * This is a plain object class intended to encapsulate the current
 * state of an NPC interaction
 */
package com.mygdx.game.state.models;

import com.mygdx.game.objects.Character;
import com.mygdx.game.objects.NonPlayableCharacter;
import com.mygdx.game.sprite.SpriteOrientation;

import java.util.List;

public class NpcInteractionState {
    private boolean interaction;
    private boolean hasDialogChoice;
    private Character character;
    private SpriteOrientation orientation;
    private String question;
    private List<String> currentChoices;

    public NpcInteractionState() {
        this.interaction = false;
        this.hasDialogChoice = false;
        this.character = null;
        this.orientation = null;
        this.question = null;
        this.currentChoices = null;
    }

    public void resetInteractionState() {
        this.interaction = false;
        this.hasDialogChoice = false;
        this.character = null;
        this.orientation = null;
        this.question = null;
        this.currentChoices = null;
    }

    public void setInteraction(boolean interaction) {
        this.interaction = interaction;
    }

    public void setHasDialogChoice(boolean hasDialogChoice) {
        this.hasDialogChoice = hasDialogChoice;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setOrientation(SpriteOrientation orientation) {
        this.orientation = orientation;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCurrentChoices(List<String> currentChoices) {
        this.currentChoices = currentChoices;
    }

    public boolean getInteraction() {
        return this.interaction;
    }

    public boolean getHasDialogChoice() {
        return this.hasDialogChoice;
    }

    public NonPlayableCharacter getCharacter() {
        return (NonPlayableCharacter)this.character;
    }

    public SpriteOrientation getOrientation() {
        return this.orientation;
    }

    public String getQuestion() {
        return this.question;
    }

    public List<String> getCurrentChoices() {
        return this.currentChoices;
    }
}
