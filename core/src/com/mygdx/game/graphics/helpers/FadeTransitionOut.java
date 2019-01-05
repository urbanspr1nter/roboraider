/*
 * FadeTransitionOut.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Simple fade transition going out.
 */

package com.mygdx.game.graphics.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.GameStore;
import com.mygdx.game.graphics.TransitionRenderer;
import com.mygdx.game.graphics.enums.TransitionType;

import java.util.Map;

public class FadeTransitionOut implements Renderable {
    @Override
    public boolean render(GameStore store, Map<String, Object> props) {
        int seconds = (int)props.get(TransitionProperties.Seconds);

        if(store.transitionRenderer == null || store.transitionRenderer.getType() != TransitionType.Out) {
            store.transitionRenderer = new TransitionRenderer(store, TransitionType.Out, seconds);
        }

        if(props.containsKey(TransitionProperties.Color)) {
            Color color = (Color)props.get(TransitionProperties.Color);
            store.transitionRenderer.render(Gdx.graphics.getDeltaTime(), new Color(color.r, color.g, color.b, color.a));
        } else {
            store.transitionRenderer.render(Gdx.graphics.getDeltaTime(), new Color(0, 0, 0, 0.0f));
        }

        if(store.transitionRenderer.getOpacity() >= 1.0f) {
            store.transitionRenderer = null;
            return true;
        }

        return false;
    }
}
