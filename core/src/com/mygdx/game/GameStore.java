/*
 * GameStore.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * This GameStore holds all the relevant state and variables which
 * ultimately interacts with the entire game.
 */

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.battle.BattleInteractionState;
import com.mygdx.game.character.PlayerData;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.configuration.models.Entity;
import com.mygdx.game.graphics.TransitionRenderer;
import com.mygdx.game.map.EntityBuilder;
import com.mygdx.game.state.StateMachine;
import com.mygdx.game.state.models.DialogChoiceState;
import com.mygdx.game.menu.MenuInteractionState;
import com.mygdx.game.state.models.NpcInteractionState;
import com.mygdx.game.ui.Cursor;
import com.mygdx.game.ui.CursorChoiceLocation;
import com.mygdx.game.ui.DialogBox;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GameStore {
    private long totalSteps;
    private boolean keyPressDisabled;

    public long totalPlayTime;
    public long startTime;
    public StateMachine stateMachine;

    public Map<String, Sound> soundLibrary;
    public Map<String, Music> musicLibrary;
    public Map<String, GameStoreEntity> entityLibrary;

    public TransitionRenderer transitionRenderer;

    // System stuff
    public CallbackQueue callbackQueue;

    // Character
    public PlayerData playerData;

    // The global variables;
    public Configuration configuration;

    // Master SpriteBatch
    public SpriteBatch spriteBatch;

    // Variables used to control debugging, and development
    public boolean debug;
    private int currentKeyCode;

    // UI Variables -- cursors and stuff go here.
    public DialogChoiceState dialogChoiceState;
    public CursorChoiceLocation cursorChoiceLocation;
    public MenuInteractionState menuInteractionState;

    // This will tell the main shared loop what to do
    public boolean environmentAction;

    // NPC Interaction
    public NpcInteractionState npcInteractionState;

    // State management
    public boolean exitState;

    private GameStoreEntity currentEntity;

    public BattleInteractionState battleInteractionState;

    public GameStore(Configuration configuration) {
        this.configuration = configuration;
        this.startTime = new Date().getTime();
        this.totalPlayTime = 0;

        this.callbackQueue = new CallbackQueue();

        this.buildSoundLibrary();
        this.buildMusicLibrary();

        this.spriteBatch = new SpriteBatch();

        this.playerData = new PlayerData(this);

        this.npcInteractionState = new NpcInteractionState();
        this.dialogChoiceState = new DialogChoiceState();

        this.dialogChoiceState.setPrimaryDialog(new DialogBox(this.configuration));
        this.dialogChoiceState.setChoiceDialog(new DialogBox(this.configuration));
        this.dialogChoiceState.setCursor(new Cursor(this.configuration, this.spriteBatch));

        this.menuInteractionState = new MenuInteractionState(this);
        this.menuInteractionState.setCursor(new Cursor(this.configuration, this.spriteBatch));

        this.battleInteractionState = null;

        this.stateMachine = new StateMachine(this);

        this.entityLibrary = new HashMap<>();
        this.buildEntityLibrary();
    }

    public void incrementSteps() {
        this.totalSteps++;
    }

    public void decrementSteps() {
        this.totalSteps--;
    }

    public void setSteps(long steps) {
        this.totalSteps = steps;
    }

    public long getTotalSteps() {
        return this.totalSteps;
    }

    public void disableKeyPress() {
        this.keyPressDisabled = true;
    }

    public void enableKeypress() {
        this.keyPressDisabled = false;
    }

    public boolean keyPressAllowed() {
        return !this.keyPressDisabled;
    }

    public void setEntity(GameStoreEntity entity) {
        if(entity == null) {
            return;
        }

        this.currentEntity = entity;

        if(entity.getInputAdapter() != null) {
            this.currentEntity.getInputAdapter().setKeyDown(false);
        }
    }

    public GameStoreEntity currentEntity() {
        return this.currentEntity;
    }

    public void setCurrentKeyCode(int keyCode) {
        this.currentKeyCode = keyCode;
    }

    public int getCurrentKeyCode() {
        return this.currentKeyCode;
    }

    private void buildSoundLibrary() {
        this.soundLibrary = new HashMap<>();
        for(String k : this.configuration.Assets.Registry.keySet()) {
            if(this.configuration.Assets.Registry.get(k).Type.equals("Sound")) {
                this.soundLibrary.put(
                    k,
                    Gdx.audio.newSound(Gdx.files.internal(this.configuration.Assets.Registry.get(k).File))
                );
            }
        }
    }

    private void buildMusicLibrary() {
        this.musicLibrary = new HashMap<>();
        for(String k : this.configuration.Assets.Registry.keySet()) {
            if(this.configuration.Assets.Registry.get(k).Type.equals("Music")) {
                this.musicLibrary.put(
                    k,
                    Gdx.audio.newMusic(Gdx.files.internal(this.configuration.Assets.Registry.get(k).File))
                );
            }
        }
    }

    private void buildEntityLibrary() {
        this.entityLibrary = new HashMap<>();

        EntityBuilder eBuilder = new EntityBuilder(this);
        for(Entity e : this.configuration.Entities) {

            this.entityLibrary.put(e.Key, eBuilder.buildEntity(e));
        }
    }
}
