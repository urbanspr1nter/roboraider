/*
 * GameStoreEntity.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * The local context for the current state.
 */

package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.camera.CameraManager;
import com.mygdx.game.input.adapters.DefaultInputAdapter;
import com.mygdx.game.input.adapters.GameInputAdapter;
import com.mygdx.game.map.EnvironmentSpriteRegistry;
import com.mygdx.game.map.models.MapInformation;
import com.mygdx.game.objects.Character;
import com.mygdx.game.sprite.SpriteUnitLocationNode;

import java.awt.*;
import java.util.List;

public class GameStoreEntity {
    private GameInputAdapter inputAdapter;
    private EnvironmentSpriteRegistry spriteRegistry;
    private CameraManager cameraManager;
    private Character player;
    private TiledMap tiledMap;
    private SpriteUnitLocationNode playerCurrentDirectionNode;
    private MapInformation mapInfo;
    private List<String> enemies;

    private Music bgTheme;
    private float bgThemeVolume;

    public GameStoreEntity() {
        this.inputAdapter = new DefaultInputAdapter();
        this.spriteRegistry = new EnvironmentSpriteRegistry();

        OrthographicCamera camera = new OrthographicCamera();
        this.cameraManager = new CameraManager(camera, new Point(0, 0), new Point(0, 0), new Point(0, 0));
    }

    public void updatePlayerPosition() {
        this.spriteRegistry
            .updateOrientation(this.player, this.playerCurrentDirectionNode);
        this.spriteRegistry
            .updatePosition(
                this.player,
                new Point(
                    this.cameraManager.getCurrentDisplacement().x,
                    this.cameraManager.getCurrentDisplacement().y
                )
            );
    }

    public void setInputAdapter(GameInputAdapter inputAdapter) {
        this.inputAdapter = inputAdapter;
    }

    public void setEnvironmentSpriteRegistry(EnvironmentSpriteRegistry registry) {
        this.spriteRegistry = registry;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public void setPlayer(Character player) {
        this.player = player;
    }

    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public void setPlayerCurrentDirectionNode(SpriteUnitLocationNode playerCurrentDirectionNode) {
        this.playerCurrentDirectionNode = playerCurrentDirectionNode;
    }

    public void setMapInfo(MapInformation mapInfo) {
        this.mapInfo = mapInfo;
    }

    public void setEnemies(List<String> enemies) {
        this.enemies = enemies;
    }

    public void setBgTheme(Music bgTheme) {
        this.bgTheme = bgTheme;
    }

    public void setBgThemeVolume(float volume) {
        this.bgThemeVolume = volume;
    }

    public GameInputAdapter getInputAdapter() {
        return this.inputAdapter;
    }

    public EnvironmentSpriteRegistry getSpriteRegistry() {
        return this.spriteRegistry;
    }

    public CameraManager getCameraManager() {
        return this.cameraManager;
    }

    public Character getPlayer() {
        return this.player;
    }

    public TiledMap getTiledMap() {
        return this.tiledMap;
    }

    public SpriteUnitLocationNode getPlayerCurrentDirectionNode() {
        return this.playerCurrentDirectionNode;
    }

    public MapInformation getMapInfo() {
        return this.mapInfo;
    }

    public List<String> getEnemies() {
        return this.enemies;
    }

    public Music getBgTheme() {
        return this.bgTheme;
    }

    public float getBgThemeVolume() {
        return this.bgThemeVolume;
    }

}
