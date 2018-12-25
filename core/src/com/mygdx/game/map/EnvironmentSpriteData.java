package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.sprite.*;

import java.awt.*;
import java.util.Objects;

public class EnvironmentSpriteData {
    private String name;
    private SpriteCharacter sprite;
    private boolean isNpc;
    private Point mapPosition;
    private int renderAfterLayer;
    private SpriteUnitLocationNode orientation;

    public EnvironmentSpriteData(String name, SpriteCharacter sprite, int renderAfterLayer, Point mapPosition) {
        this.name = name;
        this.sprite = sprite;
        this.mapPosition = mapPosition;
        this.renderAfterLayer = renderAfterLayer;
        this.isNpc = true;

        // Default orientation
        this.orientation = this.sprite.getGraph().get(SpriteDirection.START).get(SpriteOrientation.DOWN.value());
    }

    public void setPlayable(boolean flag) {
        this.isNpc = flag;
    }

    public boolean isPlayable() {
        return this.isNpc;
    }

    public String getName() {
        return this.name;
    }

    public Sprite getSprite() {
        return this.sprite.getSpriteHandler().getSprite();
    }

    public Point getMapPosition() {
        return this.mapPosition;
    }

    public void setMapPosition(Point mapPosition) {
        this.mapPosition = mapPosition;
    }

    public SpriteUnitLocationNode getOrientation() {
        return this.orientation;
    }

    public void setOrientation(SpriteUnitLocationNode orientation) {
        this.orientation = orientation;
    }

    public void setOrientation(SpriteOrientation orientation) {
        if(orientation == SpriteOrientation.LEFT) {
            this.orientation = this.sprite.getGraph().get(SpriteDirection.START).get(SpriteOrientation.LEFT.value());
        } else if(orientation == SpriteOrientation.RIGHT) {
            this.orientation = this.sprite.getGraph().get(SpriteDirection.START).get(SpriteOrientation.RIGHT.value());
        } else if(orientation == SpriteOrientation.UP) {
            this.orientation = this.sprite.getGraph().get(SpriteDirection.START).get(SpriteOrientation.UP.value());
        } else if(orientation == SpriteOrientation.DOWN) {
            this.orientation = this.sprite.getGraph().get(SpriteDirection.START).get(SpriteOrientation.DOWN.value());
        } else {
            this.orientation = this.sprite.getGraph().get(SpriteDirection.START).get(SpriteOrientation.DOWN.value());
        }
    }

    public int getRenderAfterLayer() {
        return this.renderAfterLayer;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof EnvironmentSpriteData)) {
            return false;
        }

        EnvironmentSpriteData that = (EnvironmentSpriteData)o;
        if(this.sprite.getSpriteHandler().getSprite().equals(that.sprite.getSpriteHandler().getSprite())
                && this.renderAfterLayer == that.renderAfterLayer) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.sprite, this.renderAfterLayer);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SpriteName: ");
        sb.append(this.name);
        sb.append(", MapPosition: ");
        sb.append(this.mapPosition.x + ", " + this.mapPosition.y);
        sb.append(", Layer: ");
        sb.append(this.renderAfterLayer);

        return sb.toString();
    }
}
