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

import java.awt.*;

public class DamageCounter extends Animation {
    private final int FRAME_TIME = 21;

    private int maxY;
    private int currentY;
    private float elapsedTime;
    private float totalTime;
    private float totalDisplacement;
    private String value;
    private boolean resolved;
    private boolean started;

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
        this.maxY = this.location.y + 200;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void animate(AnimationDoneCallback cb, Targetable t) {
        if(!this.started) {
            this.started = true;
        }

        if(this.totalDisplacement >= 100) {
            this.resolved = true;
            if(cb != null) {
                cb.done();
            }

            return;
        }

        this.elapsedTime += (Gdx.graphics.getDeltaTime() * 1000);

        float initialVelocity = 0.1f;
        float acceleration = 0.15f;

        if(this.elapsedTime >= FRAME_TIME) {
            this.elapsedTime = 0;
            this.totalTime++;

            if(this.totalDisplacement >= 50) {
                this.currentY += getDeltaY(initialVelocity, -acceleration, this.totalTime);
            } else {
                this.currentY += getDeltaY(initialVelocity, acceleration, this.totalTime);
            }

            this.totalDisplacement += getDeltaY(initialVelocity, acceleration, this.totalTime);
        }

        Monster m = (Monster)t;

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        Gdx.gl20.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0f, 0f, 0f, 0.50f));
        shapeRenderer.rect(m.getPosition().x + (m.getSize().x / 2) - 6, this.currentY - 20, 24, 24);
        shapeRenderer.end();

        this.store.spriteBatch.begin();
        TextRenderer.get().render(
                this.store.spriteBatch,
                this.value,
                new Point(m.getPosition().x + (m.getSize().x / 2), this.currentY),
                Color.WHITE
        );
        this.store.spriteBatch.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private int getDeltaY(float velocity, float acceleration, float currentTime) {
        return (int)Math.round(
                velocity * currentTime + (0.5 * acceleration * Math.pow(currentTime, 2))
        );
    }
}
