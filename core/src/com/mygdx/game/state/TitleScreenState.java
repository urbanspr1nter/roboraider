/*
 * TitleScreenState.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Title screen!
 */
package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameStore;
import com.mygdx.game.GameStoreEntity;
import com.mygdx.game.graphics.helpers.FadeTransitionIn;
import com.mygdx.game.ui.DialogBox;
import com.mygdx.game.ui.DialogBoxPositions;
import com.mygdx.game.ui.enums.DialogBoxSize;
import com.mygdx.game.util.ListUtils;

import java.util.HashMap;
import java.util.Map;

public class TitleScreenState extends BaseState {
    private Texture titleScreenTexture;
    private DialogBox menuBox;
    private Music music;
    private String options;

    public TitleScreenState(GameStore store, GameStoreEntity entity) {
        super(store, entity);

        this.titleScreenTexture = new Texture(
            Gdx.files.internal(this.store.configuration.Game.TitleScreen.Image)
        );

        String assetName = this.store.configuration.Game.TitleScreen.Music;
        this.music = this.store.musicLibrary.get(assetName);
        this.music.setVolume(this.store.configuration.Assets.Registry.get(assetName).Volume);
        this.music.setLooping(true);

        this.options = new ListUtils<String>().join(
            System.lineSeparator() + System.lineSeparator(),
            this.store.configuration.Game.TitleScreen.Options
        );

        this.menuBox = new DialogBox(this.store.configuration);
        this.menuBox.toggle();
    }

    @Override
    protected void onEnter() {
        this.music.play();
        this.store.dialogChoiceState.setCursorVisible(false);
    }

    @Override
    protected void onUpdate() {}

    @Override
    protected void onExecute() {
        this.store.spriteBatch.begin();
        this.store.spriteBatch.draw(this.titleScreenTexture, 0, 0);
        this.store.spriteBatch.end();

        if(this.store.transitionRenderer == null
                && this.store.dialogChoiceState.getCursorCellPosition() != null) {
            this.menuBox.display(
                this.options,
                DialogBoxSize.XSmall,
                DialogBoxPositions.SmallRightBottomMiddle()
            );

            this.store.dialogChoiceState.getCursor()
                    .draw(this.store.dialogChoiceState.getCursorCellPosition());
        }

    }

    @Override
    protected void onExit() {
        this.music.stop();
        this.music.dispose();

        this.store.callbackQueue.registerImmediate(() -> {
            Map<String, Object> props = new HashMap<>();
            props.put("seconds", 2);
            props.put("color", Color.WHITE);

            return new FadeTransitionIn().render(this.store, props);
        });
    }
}
