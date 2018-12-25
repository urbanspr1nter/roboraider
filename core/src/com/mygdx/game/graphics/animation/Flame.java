package com.mygdx.game.graphics.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.GameStore;
import com.mygdx.game.graphics.animation.contracts.AnimationDoneCallback;
import com.mygdx.game.graphics.helpers.FadeTransitionIn;
import com.mygdx.game.objects.Monster;
import com.mygdx.game.objects.contracts.Targetable;

import java.util.HashMap;
import java.util.Map;

public class Flame extends Animation {
    private int frameIndex;
    private float elapsedTime;
    private FadeTransitionIn ft;
    private Map<String, Object> ftProps;
    private boolean ftDoneRendering;

    public Flame(GameStore store) {
        super(store, store.configuration.Assets.Registry.get("FlameAnimation").File);
        this.frameIndex = 0;
        this.elapsedTime = 0;
        this.ft = new FadeTransitionIn();
        this.ftProps = new HashMap<>();
        this.ftProps.put("color", Color.RED);
        this.ftProps.put("seconds", 2);

        this.ftDoneRendering = false;
    }

    public void reset() {
        this.frameIndex = 0;
        this.ftDoneRendering = false;
    }

    public void animate(AnimationDoneCallback cb, Targetable t) {
        if(this.frameIndex == 6 || this.ftDoneRendering) {
            if(cb != null) {
                cb.done();
            }
            return;
        }

        if(t instanceof Monster) {
            this.elapsedTime += (Gdx.graphics.getDeltaTime() * 1000);

            if(this.elapsedTime >= 67) {
                this.elapsedTime = 0;
                this.frameIndex++;
            }

            Monster m = (Monster)t;
            this.store.spriteBatch.begin();
            this.store.spriteBatch.draw(
                    new TextureRegion(this.spriteSheetTexture, frameIndex * 128, 0, 128, 128),
                    m.getPosition().x + (float)(m.getSize().x / 2 - m.getPosition().x), m.getPosition().y + (float)(m.getSize().y / 2));
            this.store.spriteBatch.end();
        } else {
            if(ft.render(this.store, this.ftProps)) {
                this.ftDoneRendering = true;
            }
        }
    }
}
