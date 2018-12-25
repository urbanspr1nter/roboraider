/*
 * TransitionRenderer.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A simple transition screen that fades-in to the next screen.
 */

package com.mygdx.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameStore;
import com.mygdx.game.graphics.enums.TransitionType;

public class TransitionRenderer {
    private float alphaChannelDelta;
    private int durationPerFrameMs;

    private GameStore store;
    private float elapsedTime;
    private float opacity;
    private ShapeRenderer shapeRenderer;
    private TransitionType transitionType;
    private boolean customOpacity;

    public TransitionRenderer(GameStore store, TransitionType type, int seconds) {
        this.store = store;

        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setProjectionMatrix(this.store.currentEntity().getCameraManager().getCamera().combined);
        this.shapeRenderer.setAutoShapeType(true);

        this.elapsedTime = 0;
        this.transitionType = type;

        if(this.transitionType == TransitionType.In) {
            this.opacity = 1.0f;
        } else {
            this.opacity = 0.0f;
        }

        this.alphaChannelDelta = (1.0f / (seconds * 12));
        this.durationPerFrameMs = 16 * seconds;
    }

    public void render(float deltaTime, Color color) {
        if(!this.customOpacity) {
            this.opacity = color.a;
            this.customOpacity = true;
        }

        if(this.transitionType == TransitionType.In) {
            if(this.opacity <= 0.0f) {
                // We are done, so we no longer need to transition.
                return;
            }
        } else {
            if(this.opacity >= 1.0f) {
                // We are done, so we no longer need to transition.
                return;
            }
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);

        elapsedTime += this.getMs(deltaTime);

        this.shapeRenderer.begin();
        this.shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

        if(this.transitionType == TransitionType.In) {
            if (this.elapsedTime >= this.durationPerFrameMs && this.opacity >= 0.0f) {
                this.opacity -= this.alphaChannelDelta;
                this.elapsedTime = 0;
            }
        } else {
            if (this.elapsedTime >= this.durationPerFrameMs && this.opacity <= 1.0f) {
                this.opacity += this.alphaChannelDelta;
                this.elapsedTime = 0;
            }
        }

        this.shapeRenderer.setColor(new Color(color.r, color.g, color.b, this.opacity));

        /*
            This one is a bit weird to understand, but basically one should know that
            the origin point of the rectangle is actually at the center.

            The camera always tries to focus the character at the center of the screen.

            Suppose the screen looks like:

            |------------|
            |            |
            |     X      |
            |            |
            |------------|

            If the character is at (350, 200), then basically, we just draw a rectangle originating
            at this point and spread it towards the dimensions of the map. it is overkill, but not
            that expensive, and it is guaranteed to cover the viewport if it is smaller than the map
            dimensions (which it should be...)

              |--------------------|
              |       RECT         |
              |   |------------|   |
              |   |   VP       |   |
              |   |     X      |   |
              |   |            |   |
              |   |------------|   |
              |                    |
              |--------------------|

         */

        this.shapeRenderer.rect(
            this.store.currentEntity().getCameraManager().getCurrentDisplacement().x
                    - this.store.currentEntity().getCameraManager().getCamera().viewportWidth
                    - (this.store.currentEntity().getCameraManager().getCamera().viewportWidth / 2),
            this.store.currentEntity().getCameraManager().getCurrentDisplacement().y
                    - this.store.currentEntity().getCameraManager().getCamera().viewportHeight
                    - (this.store.currentEntity().getCameraManager().getCamera().viewportHeight / 2),
            this.store.currentEntity().getCameraManager().getCamera().viewportWidth * 4,
            this.store.currentEntity().getCameraManager().getCamera().viewportHeight * 4
        );
        this.shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public float getOpacity() {
        return this.opacity;
    }

    private float getMs(float timeInSeconds) {
        return timeInSeconds * 1000;
    }
}
