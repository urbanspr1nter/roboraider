/*
 * DamageCounter.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Animation showing the current damage of the enemy in combat.
 */

package com.mygdx.game.graphics.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameStore;
import com.mygdx.game.graphics.animation.contracts.AnimationDoneCallback;
import com.mygdx.game.objects.Monster;
import com.mygdx.game.objects.contracts.Targetable;
import com.mygdx.game.ui.text.TextRenderer;
import com.mygdx.game.util.Timer;

import java.awt.*;

public class DamageCounter extends Animation {
    private final int FRAME_TIME = 21;
    private final int PADDING_TOP = 8;
    private final int PADDING_BOTTOM = 8;
    private final int PADDING_LEFT = 8;
    private final int PADDING_RIGHT = 8;
    private final float TEXT_OPACITY_DELTA = 0.1677f;
    private final float BACKGROUND_OPACITY_DELTA = 0.0833f;
    private final float INITIAL_VELOCITY = 0.1f;
    private final float ACCELERATION = 0.15f;
    private final int MAX_ALLOWABLE_COUNTER_DISPLACEMENT = 35;
    private final int COUNTER_DISPLACEMENT_AT_TOP = 25;

    private int currentY;
    private float elapsedTime;
    private float totalTime;
    private float totalDisplacement;
    private String value;
    private boolean resolved;
    private boolean started;

    private ShapeRenderer shapeRenderer;
    private float backgroundOpacity;
    private float textOpacity;
    private int framesPassed;

    public DamageCounter(GameStore store) {
        super(store, null);
        this.reset();
    }

    @Override
    public void reset() {
        this.resolved = false;
        this.started  = false;
        this.elapsedTime = 0;
        this.totalDisplacement = 0;
        this.totalTime = 0;

        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
        this.framesPassed = 0;
        this.backgroundOpacity = 0.5f;
        this.textOpacity = 1.0f;
    }

    public boolean hasStarted() {
        return this.started;
    }

    public boolean isResolved() {
        return this.resolved;
    }

    @Override
    public void setLocation(Point location) {
        this.location = location;
        this.currentY = this.location.y + 150;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void animate(AnimationDoneCallback cb, Targetable t) {
        if(!this.started) {
            this.started = true;
        }

        Monster m = (Monster)t;

        int startPositionX = (m.getPosition().x + (m.getSize().x / 2));

        if(this.totalDisplacement >= MAX_ALLOWABLE_COUNTER_DISPLACEMENT) {
            this.renderDamageCounter(
                new Point(startPositionX, this.currentY - 70),
                new Point(startPositionX, this.currentY - 70),
                this.backgroundOpacity, this.textOpacity
            );

            // When 750ms has passed, begin the fade.
            if(this.framesPassed >= this.getFramesPassedByTime(750)) {
                this.backgroundOpacity -= BACKGROUND_OPACITY_DELTA;
                this.textOpacity -= TEXT_OPACITY_DELTA;
            }

            this.framesPassed++;

            // At time 1000ms, remove the counter and perform the callback.
            if(this.framesPassed >= this.getFramesPassedByTime(1000)) {
                this.resolved = true;
                if(cb != null) {
                    cb.done();
                }
            }
        } else {
            this.elapsedTime += (Gdx.graphics.getDeltaTime() * 1000);

            float initialVelocity = INITIAL_VELOCITY;
            float acceleration = ACCELERATION;

            if(this.elapsedTime >= FRAME_TIME) {
                this.elapsedTime = 0;
                this.totalTime++;

                if(this.totalDisplacement >= COUNTER_DISPLACEMENT_AT_TOP) {
                    this.currentY += getDeltaY(initialVelocity, -acceleration, this.totalTime);
                } else {
                    this.currentY += getDeltaY(initialVelocity, acceleration, this.totalTime);
                }

                this.totalDisplacement += getDeltaY(initialVelocity, acceleration, this.totalTime);
            }

            this.renderDamageCounter(
                new Point(startPositionX, this.currentY),
                new Point(startPositionX, this.currentY),
                this.backgroundOpacity,
                this.textOpacity
            );
        }
    }

    private int getDeltaY(float velocity, float acceleration, float currentTime) {
        return (int)Math.round(velocity * currentTime + (0.5 * acceleration * Math.pow(currentTime, 2)));
    }

    private int getFramesPassedByTime(int milliseconds) {
        int fps = 60;
        float seconds = milliseconds / 1000.0f;

        return (int)(fps * seconds);
    }

    private void renderBackgroundForCounter(Point location, float opacity) {
        int damage = Integer.parseInt(this.value);
        int marginRight = 3;
        int width = 16;
        int height = 16;

        int temp = damage;
        int digits = 0;
        while(temp > 0) {
            temp = temp / 10;
            digits++;
        }

        if(digits > 1) {
            marginRight *= digits;
        } else {
            marginRight = 0;
        }

        width = width * digits - marginRight;
        height = PADDING_TOP + height + PADDING_BOTTOM;

        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0f, 0f, 0f, opacity));
        shapeRenderer.rect(location.x, location.y, width, height);
        shapeRenderer.end();
    }

    private void renderDamageText(Point location, float opacity) {
        int textPaddingFromBottom = 24;

        this.store.spriteBatch.begin();
        TextRenderer.get().render(
            this.store.spriteBatch,
            this.value,
            new Point((PADDING_LEFT / 2) + location.x, location.y + textPaddingFromBottom),
            new Color(1.0f, 1.0f, 1.0f, opacity)
        );
        this.store.spriteBatch.end();
    }

    private void renderDamageCounter(Point bgLoc, Point txtLoc, float bgOpacity, float txtOpacity) {
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        this.renderBackgroundForCounter(bgLoc, bgOpacity);
        this.renderDamageText(txtLoc, txtOpacity);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
