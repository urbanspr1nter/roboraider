/*
 * Main.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Start here if you don't know where to go.
 *
 * Life cycle:
 * 	1. Main()
 * 	2. create()
 * 	3. Loop
 * 		-> update()
 * 		-> render()
 * 			-> Handle anything else here.
 */

package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.audio.UiSounds;
import com.mygdx.game.battle.BattleStateHelper;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.graphics.GraphicsHelper;
import com.mygdx.game.graphics.helpers.FadeTransitionIn;
import com.mygdx.game.logging.Logger;
import com.mygdx.game.map.enums.Action;
import com.mygdx.game.map.models.Trigger;
import com.mygdx.game.state.TitleScreenState;

import java.util.*;

public class Main extends ApplicationAdapter implements ApplicationListener {
	private GameStore gameStore;
	private Configuration config;
	private GraphicsHelper graphicsHelper;
	private BattleStateHelper battleStateHelper;

	Main() {}

	public Main(Configuration configuration) {
		super();
		this.config = configuration;

		this.graphicsHelper = new GraphicsHelper();
	}

	@Override
	public void dispose() {
		Logger.getInstance().dispose();
	}

	private void update() {
		this.gameStore.stateMachine.getState().update();
	}

	@Override
	public void create () {
		Gdx.app.setLogLevel(this.config.Game.LogLevel);

		this.gameStore = new GameStore(this.config);
		this.gameStore.debug = this.config.Game.Debug;
		this.battleStateHelper = new BattleStateHelper(this.gameStore);

		// Title Screen State
		this.gameStore.stateMachine.setState(
			"TitleScreen",
			new TitleScreenState(this.gameStore, this.gameStore.entityLibrary.get("TitleScreen"))
		);

		this.gameStore.callbackQueue.registerImmediate(() -> {
			FadeTransitionIn fadeTransition = new FadeTransitionIn();

			if(fadeTransition.render(this.gameStore, this.graphicsHelper.getFadeProps(2, Color.BLACK))) {
				this.gameStore.dialogChoiceState.setCursorVisible(true);
				new UiSounds(this.gameStore).playCursorConfirm();
				return true;
			} else {
				return false;
			}
		});

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.gameStore.totalPlayTime += ((new Date().getTime()) - this.gameStore.startTime);
		this.gameStore.startTime = new Date().getTime();

		this.update();
		this.gameStore.stateMachine.getState().execute();

		// Exit battle if needed
		this.battleStateHelper.exitBattleIfNeeded();

		// Any battles?
		this.battleStateHelper.goToBattleIfNeeded();

		this.handleTriggers();
	}

	private void handleTriggers() {
		if(this.gameStore.currentEntity().getMapInfo() != null) {
			List<Trigger> triggers = this.gameStore.currentEntity().getMapInfo().getTriggers();
			for(Trigger t : triggers) {
				int cellX = this.gameStore.currentEntity().getCameraManager().getCurrentDisplacement().x
						/ this.gameStore.configuration.Map.Unit.Width;
				int cellY = this.gameStore.currentEntity().getCameraManager().getCurrentDisplacement().y
						/ this.gameStore.configuration.Map.Unit.Height;

				if(t.location().x == cellX && t.location().y == cellY) {
					if(t.action() == Action.Teleport) {
						this.gameStore.callbackQueue.registerImmediate(() -> new FadeTransitionIn()
								.render(this.gameStore, this.graphicsHelper.getFadeProps(2, Color.BLACK)));

						t.execute();
					}
				}
			}
		}
	}
}
