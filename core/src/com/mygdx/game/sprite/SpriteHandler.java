package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.awt.*;

public class SpriteHandler implements Disposable {
    private Sprite sprite;
    private int unitWidth;
    private int unitHeight;

    public SpriteHandler(String filename, int unitWidth, int unitHeight) {
        this.sprite = new Sprite(new Texture(filename));
        this.unitWidth = unitWidth;
        this.unitHeight = unitHeight;
    }

    public Texture getSpriteTexture() {
        return this.sprite.getTexture();
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public int getUnitWidth() {
        return this.unitWidth;
    }

    public int getUnitHeight() {
        return this.unitHeight;
    }

    public SpriteUnitLocation getSpriteUnitLocation(int row, int col) {
        int startX = col * this.unitWidth;
        int startY = row * this.unitHeight;
        int endX = col * this.unitWidth + this.unitWidth;
        int endY = row * this.unitHeight + this.unitHeight;

        return new SpriteUnitLocation(new Point(startX, startY), new Point(endX, endY));
    }

    @Override
    public void dispose() {
        //this.sprite.getTexture().dispose();
    }
}
