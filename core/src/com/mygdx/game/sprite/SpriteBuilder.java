package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public class SpriteBuilder {
    private SpriteCharacter spriteCharacter;

    public SpriteBuilder() { }

    public Sprite buildSprite(String filename, int unitWidth, int unitHeight, Point initialPosition) {
        this.spriteCharacter = this.createSpriteGraph(filename, unitWidth, unitHeight);

        SpriteHandler spriteHandler = this.spriteCharacter.getSpriteHandler();
        TextureRegion characterRegion = new TextureRegion(
            spriteHandler.getSpriteTexture(),
            0,
            0,
            spriteHandler.getUnitWidth(),
            spriteHandler.getUnitHeight()
        );

        spriteHandler.getSprite().setSize(spriteHandler.getUnitWidth(), spriteHandler.getUnitHeight());
        spriteHandler.getSprite().setRegion(characterRegion);
        spriteHandler.getSprite().setPosition(initialPosition.x, initialPosition.y);

        return spriteHandler.getSprite();
    }

    public SpriteCharacter getSpriteCharacter() {
        return this.spriteCharacter;
    }

    private SpriteCharacter createSpriteGraph(String spriteSheetFile, int unitWidth, int unitHeight) {
        return new SpriteCharacter(spriteSheetFile, unitWidth, unitHeight);
    }

}
