/*
 * LocalMapState.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * The local map state is the state that represents dungeons, towns,
 * programmatic cut scenes, etc. Basically, anything not world map,
 * title screens (ending too), battles and menu.
 */

package com.mygdx.game.state;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.GameStore;
import com.mygdx.game.GameStoreEntity;
import com.mygdx.game.OrthogonalTiledMapRendererWithSprites;
import com.mygdx.game.ui.DialogBox;
import com.mygdx.game.ui.DialogBoxPositions;
import com.mygdx.game.ui.enums.DialogBoxSize;
import com.mygdx.game.util.ListUtils;

import java.awt.*;

public class LocalMapState extends BaseState {
    private ListUtils<String> listUtils;
    private OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    private Music music;

    public LocalMapState(GameStore store, GameStoreEntity newEntity) {
        super(store, newEntity);

        this.listUtils = new ListUtils<>();
        this.music = this.store.currentEntity().getBgTheme();

        if(this.music != null) {
            this.music.setVolume(this.store.currentEntity().getBgThemeVolume());
            this.music.setLooping(true);
        }

        this.tiledMapRenderer
                = new OrthogonalTiledMapRendererWithSprites(
                this.store.currentEntity().getTiledMap(),
                this.store.currentEntity().getSpriteRegistry()
        );
        this.tiledMapRenderer.setView(this.store.currentEntity().getCameraManager().getCamera());
    }

    @Override
    public void onEnter() {
        if(this.music != null) {
            this.music.play();
        }
    }

    @Override
    public void onExit() {
        if(this.music != null) {
            this.music.stop();
            this.music.dispose();
        }
    }

    @Override
    public void onUpdate() {
        this.store.currentEntity().getCameraManager().getCamera().update();
        OrthographicCamera cam = this.store.currentEntity().getCameraManager().getCamera();
        this.tiledMapRenderer.setView(cam);
        this.store.currentEntity().getInputAdapter().handleKeyPress(this.store.getCurrentKeyCode());
    }

    @Override
    public void onExecute() {
        this.tiledMapRenderer.render();


        if(this.store.npcInteractionState.getInteraction()) {
            if (this.store.npcInteractionState.getCharacter() != null) {
                this.store.npcInteractionState.getCharacter().trigger();
            }
        }

        if(this.store.dialogChoiceState.getCursorVisible()) {
            String choices = this.listUtils.join(
                System.lineSeparator() + System.lineSeparator(),
                this.store.npcInteractionState.getCurrentChoices()
            );

            // Create a new dialog box to display the choices.
            this.store.dialogChoiceState.setChoiceDialog(new DialogBox(this.store.configuration));
            this.store.dialogChoiceState.getChoiceDialog().toggle();

            DialogBoxSize dialogSize;
            Point dialogPosition;

            int totalChoices = this.store.npcInteractionState.getCurrentChoices().size();

            if(totalChoices <= 2) {
                dialogSize = DialogBoxSize.Small;
                dialogPosition = DialogBoxPositions.SmallRightBottom();
            } else {
                dialogSize = DialogBoxSize.SmallTall;
                dialogPosition = DialogBoxPositions.SmallTallRightBottom();
            }

            this.store.dialogChoiceState.getChoiceDialog().display(choices, dialogSize, dialogPosition);
            this.store.dialogChoiceState.getCursor().draw(this.store.dialogChoiceState.getCursorCellPosition());
        }
    }
}
