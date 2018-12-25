package com.mygdx.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.map.EnvironmentSpriteRegistry;
import com.mygdx.game.objects.Character;
import com.mygdx.game.objects.NonPlayableCharacter;
import com.mygdx.game.objects.PlayableCharacter;
import com.mygdx.game.sprite.SpriteUnitLocationNode;

import java.util.List;

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {
    private EnvironmentSpriteRegistry registry;

    public OrthogonalTiledMapRendererWithSprites(TiledMap map, EnvironmentSpriteRegistry registry) {
        super(map);
        this.registry = registry;
    }

    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for(MapLayer layer : map.getLayers()) {
            if(layer instanceof TiledMapTileLayer) {
                renderTileLayer((TiledMapTileLayer)layer);
                currentLayer++;

                if(currentLayer == 2) {
                    List<Character> sprites = this.registry.getObjects();
                    for(Character sprite : sprites) {
                        if(sprite instanceof NonPlayableCharacter) {
                            continue;
                        }
                        SpriteUnitLocationNode orientation = sprite.getSpriteData().getOrientation();
                        sprite.getSpriteData().getSprite().setRegion(orientation.loc.start.x, orientation.loc.start.y, 32, 48);
                        sprite.getSpriteData().getSprite().setPosition(sprite.getSpriteData().getMapPosition().x, sprite.getSpriteData().getMapPosition().y);
                        sprite.getSpriteData().getSprite().draw(this.getBatch());
                    }
                } else if(currentLayer == 1) {
                    List<Character> sprites = this.registry.getObjects();
                    for(Character sprite : sprites) {
                        if(sprite instanceof PlayableCharacter) {
                            continue;
                        }
                        SpriteUnitLocationNode orientation = sprite.getSpriteData().getOrientation();
                        sprite.getSpriteData().getSprite().setRegion(orientation.loc.start.x, orientation.loc.start.y, 32, 48);
                        sprite.getSpriteData().getSprite().setPosition(sprite.getSpriteData().getMapPosition().x, sprite.getSpriteData().getMapPosition().y);
                        sprite.getSpriteData().getSprite().draw(this.getBatch());
                    }
                } else {
                    for(MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }

        endRender();
    }
}
