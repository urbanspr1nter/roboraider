/*
 * TextRenderer.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A simple way to draw text on screen using the default font
 * provided within the configuration data.
 */

package com.mygdx.game.ui.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.configuration.ConfigurationBuilder;

import java.awt.*;

public class TextRenderer {
    private BitmapFont font;
    private BitmapFont debugFont;

    private static TextRenderer renderer;

    private TextRenderer() {
        Configuration configuration = ConfigurationBuilder.config();

        this.font = new BitmapFont(
            Gdx.files.internal(configuration.Ui.Font.Resource),
            Gdx.files.internal(configuration.Ui.Font.Image),
            false
        );

        this.debugFont = new BitmapFont(
            Gdx.files.internal(configuration.Debug.Font.Resource),
            Gdx.files.internal(configuration.Debug.Font.Image),
            false
        );
    }

    public void render(SpriteBatch batch, String text, Point location, Color fontColor) {
        this.font.setColor(fontColor);
        this.font.draw(batch, text, location.x, location.y);
    }

    public void renderDebug(SpriteBatch batch, String text, Point location, Color fontColor) {
        this.debugFont.setColor(fontColor);
        this.debugFont.draw(batch, text, location.x, location.y);
    }

    public static TextRenderer get() {
        if(renderer == null) {
            renderer = new TextRenderer();
        }

        return renderer;
    }
}
