/*
 * CollisionDetector.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Detects collision between objects and NPC in an orthogonal map.
 */
package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Direction;
import com.mygdx.game.GameStore;
import com.mygdx.game.GameStoreEntity;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.objects.Character;
import com.mygdx.game.objects.NonPlayableCharacter;
import com.mygdx.game.sprite.SpriteOrientation;
import com.mygdx.game.sprite.SpriteUnitLocationNode;
import com.mygdx.game.util.TileUtils;

import java.awt.*;

public class CollisionDetector {
    private GameStore store;
    private Configuration configuration;

    public CollisionDetector(GameStore store, Configuration configuration) {
        this.store = store;
        this.configuration = configuration;
    }

    private Point playerCellLocation() {
        float cellUnitWidth = (float)this.configuration.Map.Unit.Width;
        float cellUnitHeight = (float)this.configuration.Map.Unit.Height;

        int cellX = Math.round(this.store.currentEntity().getCameraManager().getCurrentDisplacement().x / cellUnitWidth);
        int cellY = Math.round(this.store.currentEntity().getCameraManager().getCurrentDisplacement().y / cellUnitHeight);

        return new Point(cellX, cellY);
    }


    public void setNextLocationNode(Direction dir, GameStoreEntity entity) {
        for(SpriteUnitLocationNode loc : entity.getPlayer().getSpriteGraph()
                .getGraph()
                .get(entity.getPlayerCurrentDirectionNode().spriteDir)) {
            if(loc.dir == dir) {
                entity.setPlayerCurrentDirectionNode(loc);
                break;
            }
        }
    }

    public boolean hasObjectCollision() {
        Point playerCellLocation = this.playerCellLocation();
        TiledMap currentTiledMap = this.store.currentEntity().getTiledMap();
        MapLayers layers = currentTiledMap.getLayers();
        TiledMapTileLayer layer = (TiledMapTileLayer)layers.get(1);

        boolean hasCollision = false;

        if((layer.getCell(playerCellLocation.x + 1, playerCellLocation.y) != null
                || layer.getCell(playerCellLocation.x + 1, playerCellLocation.y + 1) != null)
                && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            setNextLocationNode(Direction.RIGHT, this.store.currentEntity());
            hasCollision = true;
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)
                && (layer.getCell(playerCellLocation.x - 1, playerCellLocation.y) != null
                || layer.getCell(playerCellLocation.x - 1, playerCellLocation.y + 1) != null)) {
            setNextLocationNode(Direction.LEFT, this.store.currentEntity());
            hasCollision = true;
        } else if(Gdx.input.isKeyPressed(Input.Keys.UP)
                && (layer.getCell(playerCellLocation.x, playerCellLocation.y + 1) != null)) {
            setNextLocationNode(Direction.UP, this.store.currentEntity());
            hasCollision = true;
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)
                && (layer.getCell(playerCellLocation.x, playerCellLocation.y - 1) != null)) {
            setNextLocationNode(Direction.DOWN, this.store.currentEntity());
            hasCollision = true;
        }

        return hasCollision;
    }

    public boolean hasNpcCollision() {
        Point playerCellLocation = this.playerCellLocation();

        boolean hasCollision = false;

        for(Character c : this.store.currentEntity().getSpriteRegistry().getObjects()) {
            if(!(c instanceof NonPlayableCharacter)) {
                continue;
            }

            Point npcPos = TileUtils.getCellFromRealLocation(
                    this.configuration,
                    c.getSpriteData().getMapPosition()
            );

            boolean isNpcRight = (playerCellLocation.x + 1 == npcPos.x && playerCellLocation.y == npcPos.y)
                    || (playerCellLocation.x + 1 == npcPos.x && playerCellLocation.y + 1 == npcPos.y)
                    || (playerCellLocation.x + 1 == npcPos.x && playerCellLocation.y - 1 == npcPos.y);

            boolean isNpcLeft = (playerCellLocation.x - 1 == npcPos.x && playerCellLocation.y == npcPos.y)
                    || (playerCellLocation.x - 1 == npcPos.x && playerCellLocation.y + 1 == npcPos.y)
                    || (playerCellLocation.x - 1 == npcPos.x && playerCellLocation.y - 1 == npcPos.y);

            boolean isNpcUp = (playerCellLocation.x == npcPos.x && playerCellLocation.y + 1 == npcPos.y);

            boolean isNpcDown = (playerCellLocation.x == npcPos.x && playerCellLocation.y - 1 == npcPos.y)
                    || (playerCellLocation.x == npcPos.x && playerCellLocation.y - 2 == npcPos.y);

            if(isNpcRight && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                setNextLocationNode(Direction.RIGHT, this.store.currentEntity());
                hasCollision = true;
                break;
            } else if(isNpcLeft && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                setNextLocationNode(Direction.LEFT, this.store.currentEntity());
                hasCollision = true;
                break;
            } else if(isNpcUp && Gdx.input.isKeyPressed(Input.Keys.UP)) {
                setNextLocationNode(Direction.UP, this.store.currentEntity());
                hasCollision = true;
                break;
            } else if(isNpcDown && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                setNextLocationNode(Direction.DOWN, this.store.currentEntity());
                hasCollision = true;
                break;
            } else if((isNpcLeft || isNpcRight || isNpcUp || isNpcDown)
                    && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

                this.store.npcInteractionState.setInteraction(true);
                this.store.npcInteractionState.setCharacter(c);
                this.store.npcInteractionState.setOrientation(this.getOppositeDirectionFromPlayer());
                this.store.npcInteractionState.setHasDialogChoice(false);
                hasCollision = true;
                break;
            }
        }

        return hasCollision;
    }

    private SpriteOrientation getOppositeDirectionFromPlayer() {
        Direction playerSpriteDirection = this.store.currentEntity().getPlayer().getSpriteData().getOrientation().dir;
        if(playerSpriteDirection == Direction.LEFT) {
            return SpriteOrientation.RIGHT;
        } else if(playerSpriteDirection == Direction.RIGHT) {
            return SpriteOrientation.LEFT;
        } else if(playerSpriteDirection == Direction.UP) {
            return SpriteOrientation.DOWN;
        } else if(playerSpriteDirection == Direction.DOWN) {
            return SpriteOrientation.UP;
        }

        return SpriteOrientation.DOWN;
    }
}
