package com.mygdx.game.objects;

import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.map.EnvironmentSpriteData;
import com.mygdx.game.sprite.SpriteBuilder;
import com.mygdx.game.sprite.SpriteCharacter;

import java.awt.*;

public class Character {
    private Configuration configuration;

    private String resourceFilename;
    private Point cellLocation;
    private SpriteBuilder spriteBuilder;
    private EnvironmentSpriteData spriteData;

    public Character(Configuration configuration, String name, String resourceFilename, Point startingCellLocation) {
        this.configuration = configuration;

        this.resourceFilename = resourceFilename;
        this.cellLocation = startingCellLocation;

        this.spriteBuilder = new SpriteBuilder();
        this.spriteBuilder.buildSprite(
            this.resourceFilename,
            this.configuration.Sprite.Unit.Width,
            this.configuration.Sprite.Unit.Height,
            this.realLocation()
        );

        this.spriteData = new EnvironmentSpriteData(name, this.spriteBuilder.getSpriteCharacter(), 2, this.realLocation());
    }

    public void setStartingLocation(Point cellLocation) {
        this.cellLocation = cellLocation;
    }

    public Point getStartingLocation() {
        return this.cellLocation;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Character)) {
            return false;
        }

        Character that = (Character)o;

        return this.spriteData.equals(that.spriteData);
    }

    public EnvironmentSpriteData getSpriteData() {
        return this.spriteData;
    }

    public SpriteCharacter getSpriteGraph() {
        return this.spriteBuilder.getSpriteCharacter();
    }

    private Point realLocation() {
        return new Point(
            this.cellLocation.x * this.configuration.Map.Unit.Width,
            this.cellLocation.y * this.configuration.Map.Unit.Height
        );
    }
}
