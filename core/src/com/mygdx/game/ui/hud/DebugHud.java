/*
 * DebugHud.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * The debugging overlay screen.
 */

package com.mygdx.game.ui.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameStore;
import com.mygdx.game.logging.Logger;
import com.mygdx.game.ui.text.TextRenderer;
import com.mygdx.game.util.GameUtils;
import com.mygdx.game.logging.LogMessage;

import java.awt.*;


public class DebugHud extends BaseHud {
    private static Point BACKGROUND_TEXTURE_START_LOCATION = new Point(4, 6);
    private static Point MESSAGE_START_LOCATION = new Point(10, 230);
    private static int PIXEL_DELTA_FOR_LINE = 20;

    private final static int CELL_SIZE = 16;

    private Texture backgroundTexture;

    public DebugHud(GameStore store) {
        super(store);
        this.backgroundTexture = new Texture(this.store.configuration.Debug.Background.Image);
    }

    public void onRender() {
        if(this.store.currentEntity() == null) {
            return;
        }

        this.store.spriteBatch.draw(
                this.backgroundTexture,
                BACKGROUND_TEXTURE_START_LOCATION.x,
                BACKGROUND_TEXTURE_START_LOCATION.y
        );

        this.printExecutionInformation();
        this.printCharacterInformation();
        this.printLogMessages();
    }

    private void printExecutionInformation() {
        int horizontalDisplacement = this.store.currentEntity().getCameraManager().getCurrentDisplacement().x;
        int verticalDisplacement = this.store.currentEntity().getCameraManager().getCurrentDisplacement().y;

        Point cellLocation = new Point(horizontalDisplacement / CELL_SIZE, verticalDisplacement / CELL_SIZE);

        StringBuilder sb = new StringBuilder();
        sb.append("FPS: " + Gdx.graphics.getFramesPerSecond() + ", ");
        sb.append(this.store.stateMachine.getState().getClass().getSimpleName() + ", ");
        sb.append("CELL-LOC: " + "(" + cellLocation.x + ", " + cellLocation.y + ")" + ", ");
        sb.append("DISPLACEMENT: " + "(" + horizontalDisplacement + ", " + verticalDisplacement + ")" + ", ");
        sb.append("KEY: " + Input.Keys.toString(this.store.getCurrentKeyCode()) + ", ");
        sb.append("TIME: " + GameUtils.durationMsToFormattedTime(this.store.totalPlayTime) + ", ");
        sb.append("STEPS: " + this.store.getTotalSteps());

        TextRenderer.get().renderDebug(this.store.spriteBatch, sb.toString(), MESSAGE_START_LOCATION, Color.WHITE);
    }

    private void printCharacterInformation() {
        TextRenderer.get().renderDebug(
            this.store.spriteBatch, this.store.playerData.getPlayerStatistics().toHealthString(),
            new Point(MESSAGE_START_LOCATION.x, MESSAGE_START_LOCATION.y - PIXEL_DELTA_FOR_LINE),
            Color.ORANGE
        );
    }

    private void printLogMessages() {
        int i = 0;
        for(LogMessage s : Logger.getBuffer()) {
            if(s == null) {
                continue;
            }
            TextRenderer.get().renderDebug(
                this.store.spriteBatch,
                s.getMessage(),
                new Point(
                    MESSAGE_START_LOCATION.x,
                    MESSAGE_START_LOCATION.y - (2 * PIXEL_DELTA_FOR_LINE) - (PIXEL_DELTA_FOR_LINE * i)
                ),
                s.getColor()
            );
            i++;
        }
    }
}
