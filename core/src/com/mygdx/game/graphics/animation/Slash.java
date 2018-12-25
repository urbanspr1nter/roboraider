package com.mygdx.game.graphics.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.GameStore;
import com.mygdx.game.graphics.animation.contracts.AnimationDoneCallback;
import com.mygdx.game.objects.contracts.Targetable;

public class Slash extends Animation {
    private int frameIndex;
    private float elapsedTime;

    public Slash(GameStore store) {
        super(store, store.configuration.Assets.Registry.get("SlashAnimation").File);
        this.frameIndex = 0;
        this.elapsedTime = 0;
    }

    public void reset() {
        this.frameIndex = 0;
    }

    public void animate(AnimationDoneCallback cb, Targetable t) {
        if(this.frameIndex == 3) {
            if(cb != null) {
                cb.done();
            }
            return;
        }

        this.elapsedTime += (Gdx.graphics.getDeltaTime() * 1000);

        if(this.elapsedTime >= 67) {
            this.elapsedTime = 0;
            this.frameIndex++;
        }

        this.store.spriteBatch.begin();
        this.store.spriteBatch.draw(
                new TextureRegion(this.spriteSheetTexture, frameIndex * 144, 0, 138, 140),
                location.x, location.y
        );
        this.store.spriteBatch.end();
    }
}
