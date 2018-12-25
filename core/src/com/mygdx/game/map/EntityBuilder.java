package com.mygdx.game.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.GameStore;
import com.mygdx.game.GameStoreEntity;
import com.mygdx.game.camera.CameraManager;
import com.mygdx.game.configuration.models.Entity;
import com.mygdx.game.configuration.models.NonPlayableCharacter;
import com.mygdx.game.configuration.models.Trigger;
import com.mygdx.game.input.adapters.DefaultInputAdapter;
import com.mygdx.game.input.adapters.GameInputAdapter;
import com.mygdx.game.input.handlers.DefaultKeyPressHandler;
import com.mygdx.game.input.handlers.contracts.KeyPressHandler;
import com.mygdx.game.map.enums.Action;
import com.mygdx.game.map.models.MapInformation;
import com.mygdx.game.map.triggers.Triggerable;
import com.mygdx.game.objects.Character;
import com.mygdx.game.objects.Dialog;
import com.mygdx.game.objects.PlayableCharacter;
import com.mygdx.game.sprite.SpriteOrientation;
import com.mygdx.game.state.StateMachine;
import com.mygdx.game.util.TileUtils;

import java.awt.*;
import java.lang.reflect.Constructor;

public class EntityBuilder {
    private GameStore store;

    public EntityBuilder(GameStore store) {
        this.store = store;
    }

    public GameStoreEntity buildEntity(Entity e) {
        GameStoreEntity entity = new GameStoreEntity();

        MapInformation mapInfo = new MapInformation(this.store.configuration);
        entity.setMapInfo(mapInfo);

        EnvironmentSpriteRegistry spriteRegistry = new EnvironmentSpriteRegistry();
        entity.setEnvironmentSpriteRegistry(spriteRegistry);

        this.buildInputAdapterAndHandlerForEntity(entity, e)
            .buildCameraForEntity(entity, e)
            .buildAndRegisterPlayerForEntity(entity, e, spriteRegistry)
            .buildAndRegisterNonPlayableCharacterForEntity(e, spriteRegistry)
            .buildMapForEntity(entity, e)
            .buildMusicForMap(entity, e).buildTriggersForMap(e, mapInfo)
            .buildEnemiesForMap(entity, e);

        return entity;
    }

    private EntityBuilder buildCameraForEntity(GameStoreEntity entity, Entity e) {
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        Point mapDimensions = new Point(e.Map.Dimension.Width, e.Map.Dimension.Height);

        Point initialPosition;
        if(e.Player != null) {
            initialPosition = new Point(e.Player.InitialPosition.X, e.Player.InitialPosition.Y);
        } else {
            initialPosition = new Point(0, 0);
        }

        Point viewPortDimensions = new Point(e.ViewPort.Width, e.ViewPort.Height);
        CameraManager cameraManager = new CameraManager(
                orthographicCamera,
                mapDimensions,
                initialPosition,
                viewPortDimensions
        );

        entity.setCameraManager(cameraManager);

        return this;
    }

    private EntityBuilder buildInputAdapterAndHandlerForEntity(GameStoreEntity entity, Entity e) {
        KeyPressHandler keyPressHandler;

        try {
            Class<?> HandlerClass = Class.forName("com.mygdx.game.input.handlers." + e.Input.Handler);
            Constructor<?> HandlerClassConstructor =
                    HandlerClass.getConstructor(GameStore.class);

            keyPressHandler =
                    (KeyPressHandler) HandlerClassConstructor.newInstance(this.store);

        } catch(Exception ex) {
            System.out.println(ex);
            keyPressHandler = new DefaultKeyPressHandler(this.store);
        }

        GameInputAdapter inputAdapter;

        try {
            Class<?> AdapterClass = Class.forName("com.mygdx.game.input.adapters." + e.Input.Adapter);
            Constructor<?> AdapterClassConstructor =
                    AdapterClass.getConstructor(GameStore.class, KeyPressHandler.class);

            inputAdapter =
                    (GameInputAdapter) AdapterClassConstructor.newInstance(this.store, keyPressHandler);
        } catch(Exception ex) {
            System.out.println(ex);

            inputAdapter = new DefaultInputAdapter();
        }

        entity.setInputAdapter(inputAdapter);

        return this;
    }

    private EntityBuilder buildAndRegisterPlayerForEntity(
        GameStoreEntity entity,
        Entity e,
        EnvironmentSpriteRegistry spriteRegistry
    ) {
        if(e.Player != null) {
            Character player = new PlayableCharacter(
                this.store.configuration,
                e.Player.Name,
                this.store.configuration.Assets.Registry.get(e.Player.AssetName).File,
                TileUtils.getCellFromRealLocation(
                    this.store.configuration,
                    new Point(e.Player.InitialPosition.X, e.Player.InitialPosition.Y)
                )
            );

            if(e.Player.InitialOrientation.equals("UP")) {
                player.getSpriteData().setOrientation(SpriteOrientation.UP);
            } else if(e.Player.InitialOrientation.equals("DOWN")) {
                player.getSpriteData().setOrientation(SpriteOrientation.DOWN);
            } else if(e.Player.InitialOrientation.equals("LEFT")) {
                player.getSpriteData().setOrientation(SpriteOrientation.LEFT);
            } else if(e.Player.InitialOrientation.equals("RIGHT")) {
                player.getSpriteData().setOrientation(SpriteOrientation.RIGHT);
            }

            entity.setPlayer(player);
            entity.setPlayerCurrentDirectionNode(player.getSpriteData().getOrientation());

            spriteRegistry.register(player);
        }

        return this;
    }

    private EntityBuilder buildAndRegisterNonPlayableCharacterForEntity(Entity e, EnvironmentSpriteRegistry spriteRegistry) {
        for(NonPlayableCharacter npc : e.NonPlayableCharacters) {
            Triggerable triggerHandler;
            try {
                Class<?> TriggerClass = Class.forName("com.mygdx.game.map.triggers.dialogs." + npc.Trigger);
                Constructor<?> TriggerClassConstructor =
                        TriggerClass.getConstructor(GameStore.class, Dialog.class);

                triggerHandler =
                        (Triggerable)TriggerClassConstructor.newInstance(
                                this.store,
                                new com.mygdx.game.objects.Dialog(new com.mygdx.game.dialog.Graph(npc.Dialog))
                        );

            } catch(Exception ex) {
                System.out.println(ex);
                triggerHandler = null;
            }

            com.mygdx.game.objects.NonPlayableCharacter npcChar =
                    new com.mygdx.game.objects.NonPlayableCharacter(
                        this.store.configuration,
                        npc.Name,
                        this.store.configuration.Assets.Registry.get(npc.AssetName).File,
                        TileUtils.getCellFromRealLocation(
                            this.store.configuration,
                            new Point(npc.Location.X, npc.Location.Y)
                        ),
                        triggerHandler
                    );

            spriteRegistry.register(npcChar);
        }

        return this;
    }

    private EntityBuilder buildMapForEntity(GameStoreEntity entity, Entity e) {
        if(e.Map.TiledMap != null) {
            TmxMapLoader mapLoader = new TmxMapLoader();
            entity.setTiledMap(mapLoader.load(e.Map.TiledMap));
        }

        return this;
    }

    private EntityBuilder buildMusicForMap(GameStoreEntity entity, Entity e) {
        if(e.Music != null) {
            entity.setBgTheme(this.store.musicLibrary.get(e.Music.AssetName));
            entity.setBgThemeVolume(this.store.configuration.Assets.Registry.get(e.Music.AssetName).Volume);
        }

        return this;
    }

    private EntityBuilder buildTriggersForMap(Entity e, MapInformation mapInfo) {
        for(Trigger trigger : e.Triggers) {
            Triggerable triggerHandler;

            try {
                Class<?> TriggerClass = Class.forName("com.mygdx.game.map.triggers." + trigger.Handler);
                Constructor<?> TriggerClassConstructor =
                        TriggerClass.getConstructor(GameStore.class, StateMachine.class);

                triggerHandler =
                        (Triggerable)TriggerClassConstructor.newInstance(this.store, this.store.stateMachine);
            } catch(Exception ex) {
                System.out.println(ex);
                triggerHandler = null;
            }

            com.mygdx.game.map.models.Trigger theTrigger = new com.mygdx.game.map.models.Trigger(
                    this.store.configuration,
                    new Point(trigger.Location.X, trigger.Location.Y), Action.Teleport, triggerHandler
            );

            mapInfo.addTrigger(theTrigger);
        }

        return this;
    }

    private EntityBuilder buildEnemiesForMap(GameStoreEntity entity, Entity e) {
        entity.setEnemies(e.Enemies);

        return this;
    }
}
