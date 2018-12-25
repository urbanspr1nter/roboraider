/*
 * Cursor.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A cool cursor for the menu UI. :)
 */

package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.util.TileUtils;

import java.awt.*;

public class Cursor {
    private Configuration configuration;
    private SpriteBatch batch;
    private Texture texture;

    public Cursor(Configuration configuration, SpriteBatch batch) {
        this.configuration = configuration;
        this.texture = new Texture(this.configuration.Ui.Cursor.Image);
        this.batch = batch;
    }

    public void draw(Point pointLocation) {
        int yPixelError = 8;

        this.batch.begin();
        this.batch.draw(
            this.texture,
            pointLocation.x,
            pointLocation.y - yPixelError
        );
        this.batch.end();
    }
}
