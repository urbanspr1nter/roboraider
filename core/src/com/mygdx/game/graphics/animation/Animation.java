package com.mygdx.game.graphics.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameStore;
import com.mygdx.game.graphics.animation.contracts.Animatable;
import com.mygdx.game.graphics.animation.contracts.AnimationDoneCallback;
import com.mygdx.game.objects.contracts.Targetable;

import java.awt.*;

public abstract class Animation implements Animatable {
    protected GameStore store;
    protected Point location;
    protected Texture spriteSheetTexture;

    protected Animation(GameStore store, String spriteSheetFile) {
        this.store = store;
        if(spriteSheetFile != null) {
            this.spriteSheetTexture = new Texture(Gdx.files.internal(spriteSheetFile));
        }
    }

    @Override
    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public Point getLocation() {
        return this.location;
    }

    public abstract void reset();
    public abstract void animate(AnimationDoneCallback cb, Targetable t);
}
