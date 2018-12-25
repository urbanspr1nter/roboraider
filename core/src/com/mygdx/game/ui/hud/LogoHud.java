/*
 * LogoHud.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Shows the main watermark for the shared. It is configurable
 * through Configuration.json.
 */

package com.mygdx.game.ui.hud;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameStore;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.configuration.ConfigurationBuilder;
import com.mygdx.game.graphics.ColorComponents;
import com.mygdx.game.ui.text.TextRenderer;

import java.awt.*;

public class LogoHud extends BaseHud {
    private Point imageLocation;
    private Point textLocation;
    private int size;
    private String image;
    private String text;

    private Configuration config;

    public LogoHud(GameStore store) {
        super(store);

        this.config = ConfigurationBuilder.config();

        this.imageLocation = new Point(
            this.config.Game.Watermark.Image.Location.X,
            this.config.Game.Watermark.Image.Location.Y
        );
        this.textLocation = new Point(
            this.config.Game.Watermark.Text.Location.X,
            this.config.Game.Watermark.Text.Location.Y
        );
        this.size = this.config.Game.Watermark.Size;
        this.text = this.config.Game.Watermark.Text.Value;
        this.image = this.config.Game.Watermark.Image.File;
    }

    public void onRender() {
        TextRenderer.get().render(
            this.store.spriteBatch,
            this.text,
            this.textLocation,
            ColorComponents.BLACK
        );
        Texture texture = new Texture(this.image);
        this.store.spriteBatch.draw(
            texture,
            this.imageLocation.x,
            this.imageLocation.y,
            this.size,
            this.size
        );
    }
}
