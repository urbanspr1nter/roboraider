package com.mygdx.game.configuration;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.game.configuration.enums.LogLevel;
import com.mygdx.game.configuration.enums.ModelType;
import com.mygdx.game.configuration.models.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ConfigurationBuilder {
    private static Configuration config = new Configuration();

    public ConfigurationBuilder() { }

    public void load(InputStream data) {
        JsonReader reader = new JsonReader();
        JsonValue val = reader.parse(data);

        config.Game = new Game();
        config.Game.Fps = val.get(ModelType.Game.toString())
                .getInt("Fps");
        config.Game.Debug = val.get(ModelType.Game.toString())
                .getBoolean("Debug");

        config.Game.Watermark.Size = val.get(ModelType.Game.toString())
                .get("Watermark")
                .getInt("Size");
        config.Game.Watermark.Display = val.get(ModelType.Game.toString())
                .get("Watermark")
                .getBoolean("Display");
        config.Game.Watermark.Image.File = val.get(ModelType.Game.toString())
                .get("Watermark")
                .get("Image")
                .getString("File");
        config.Game.Watermark.Image.Location.X = val.get(ModelType.Game.toString())
                .get("Watermark")
                .get("Image")
                .get("Location")
                .getInt("X");
        config.Game.Watermark.Image.Location.Y = val.get(ModelType.Game.toString())
                .get("Watermark")
                .get("Image")
                .get("Location")
                .getInt("Y");
        config.Game.Watermark.Text.Value = val.get(ModelType.Game.toString())
                .get("Watermark")
                .get("Text")
                .getString("Value");
        config.Game.Watermark.Text.Location.X = val.get(ModelType.Game.toString())
                .get("Watermark")
                .get("Text")
                .get("Location")
                .getInt("x");
        config.Game.Watermark.Text.Location.Y = val.get(ModelType.Game.toString())
                .get("Watermark")
                .get("Text")
                .get("Location")
                .getInt("Y");

        config.Game.TitleScreen.Image = val.get(ModelType.Game.toString())
                .get("TitleScreen")
                .getString("Image");

        config.Game.TitleScreen.Music = val.get(ModelType.Game.toString())
                .get("TitleScreen")
                .getString("Music");

        JsonValue titleScreenOptions
                = val.get(ModelType.Game.toString()).get("TitleScreen").get("Options");

        for(int i = 0; i < titleScreenOptions.size; i++) {
            config.Game.TitleScreen.Options.add(titleScreenOptions.getString(i));
        }


        String logLevel = val.get(ModelType.Game.toString()).getString("logLevel");
        this.setLogLevel(logLevel);


        config.Debug = new Debug();
        config.Debug.Font.Image = val.get("Debug").get("Font").getString("Image");
        config.Debug.Font.Resource = val.get("Debug").get("Font").getString("Resource");
        config.Debug.Background.Image = val.get("Debug").get("Background").getString("Image");

        config.Ui = new Ui();
        config.Ui.Font.Resource = val.get(ModelType.Ui.toString()).get("Font").getString("Resource");
        config.Ui.Font.Image = val.get(ModelType.Ui.toString()).get("Font").getString("Image");
        config.Ui.Menu.Image = val.get(ModelType.Ui.toString()).get("Menu").getString("Image");
        config.Ui.Menu.DebugImage = val.get(ModelType.Ui.toString()).get("Menu").getString("DebugImage");
        config.Ui.Cursor.Image = val.get(ModelType.Ui.toString()).get("Cursor").getString("Image");

        config.ViewPort = new ViewPort();
        config.ViewPort.Width = val.get(ModelType.ViewPort.toString()).getInt("Width");
        config.ViewPort.Height = val.get(ModelType.ViewPort.toString()).getInt("Height");

        config.Map = new Map();
        config.Map.Unit.Width = val.get(ModelType.Map.toString())
                .get(ModelType.Unit.toString())
                .getInt("Width");
        config.Map.Unit.Height = val.get(ModelType.Map.toString())
                .get(ModelType.Unit.toString())
                .getInt("Height");

        config.Sprite = new Sprite();
        config.Sprite.Unit.Width = val.get(ModelType.Sprite.toString())
                .get(ModelType.Unit.toString())
                .getInt("Width");
        config.Sprite.Unit.Height = val.get(ModelType.Sprite.toString())
                .get(ModelType.Unit.toString())
                .getInt("Height");
        config.Sprite.StepLength = val.get(ModelType.Sprite.toString())
                .getInt("StepLength");

        config.Assets = new Assets();
        JsonValue assetItems = val.get(ModelType.Assets.toString()).get("Registry");
        JsonValue currItem = assetItems.child();
        while(currItem != null) {
            float volume = 0.0f;

            if(currItem.has("Volume")) {
                try {
                    volume = ((float)currItem.getInt("Volume") / 100);
                } catch(IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            config.Assets.Registry.put(
                    currItem.getString("Name"),
                    new AssetItem(
                        currItem.getString("Type"),
                        currItem.getString("Name"),
                        currItem.getString("File"),
                        volume
                    )
            );
            currItem = currItem.next();
        }


        JsonValue entityFilenames = val.get("Entities");
        for(int i = 0; i < entityFilenames.size; i++) {
            String entityFilename = entityFilenames.getString(i);
            
            try(InputStream is = new FileInputStream(entityFilename)) {
                JsonValue entityItem = new JsonReader().parse(is);

                Entity entity = new Entity();

                entity.Key = entityItem.getString("Key");

                entity.ViewPort.Width = entityItem.get("ViewPort").getInt("Width");
                entity.ViewPort.Height = entityItem.get("ViewPort").getInt("Height");

                entity.Input.Adapter = entityItem.get("Input").getString("Adapter");
                entity.Input.Handler = entityItem.get("Input").getString("Handler");

                entity.Map.Dimension.Width = entityItem.get("Map").get("Dimension").getInt("Width");
                entity.Map.Dimension.Height = entityItem.get("Map").get("Dimension").getInt("Height");

                if(entityItem.get("Map").get("TiledMap").type() != JsonValue.ValueType.nullValue) {
                    entity.Map.TiledMap = entityItem.get("Map").getString("TiledMap");
                } else {
                    entity.Map.TiledMap = null;
                }

                if(entityItem.get("Music").type() != JsonValue.ValueType.nullValue) {
                    entity.Music.AssetName = entityItem.get("Music").getString("AssetName");
                } else {
                    entity.Music = null;
                }

                if(entityItem.get("Player").type() != JsonValue.ValueType.nullValue) {
                    entity.Player.AssetName = entityItem.get("Player").getString("AssetName");
                    entity.Player.Name = entityItem.get("Player").getString("Name");
                    entity.Player.Dimension.Height = entityItem.get("Player").get("Dimension").getInt("Height");
                    entity.Player.Dimension.Width = entityItem.get("Player").get("Dimension").getInt("Width");
                    entity.Player.InitialPosition.X = entityItem.get("Player").get("InitialPosition").getInt("X");
                    entity.Player.InitialPosition.Y = entityItem.get("Player").get("InitialPosition").getInt("Y");
                    entity.Player.InitialOrientation = entityItem.get("Player").getString("InitialOrientation");
                } else {
                    entity.Player = null;
                }

                JsonValue triggerItems = entityItem.get("Triggers");
                for(int j = 0; j < triggerItems.size; j++) {
                    Trigger trigger = new Trigger();

                    trigger.Action = triggerItems.get(j).getString("Action");
                    trigger.Location.X = triggerItems.get(j).get("Location").getInt("X");
                    trigger.Location.Y = triggerItems.get(j).get("Location").getInt("Y");
                    trigger.Handler = triggerItems.get(j).getString("Handler");

                    entity.Triggers.add(trigger);
                }

                JsonValue npcItems = entityItem.get("NonPlayableCharacters");
                for(int j = 0; j < npcItems.size; j++) {
                    NonPlayableCharacter npc = new NonPlayableCharacter();

                    npc.AssetName = npcItems.get(j).getString("AssetName");
                    npc.Name = npcItems.get(j).getString("Name");
                    npc.Location.X = npcItems.get(j).get("Location").getInt("X");
                    npc.Location.Y = npcItems.get(j).get("Location").getInt("Y");
                    npc.Dialog = npcItems.get(j).getString("Dialog");
                    npc.Trigger = npcItems.get(j).getString("Trigger");

                    entity.NonPlayableCharacters.add(npc);
                }

                JsonValue enemies = entityItem.get("Enemies");
                for(int j = 0; j < enemies.size; j++) {
                    entity.Enemies.add(enemies.get(j).asString());
                }

                config.Entities.add(entity);

            } catch(Exception ex) {
                System.out.println(ex);
                System.exit(1);
            }
        }
    }

    public static Configuration config() {
        return config;
    }

    private void setLogLevel(String logLevel) {
        if(logLevel.equals(LogLevel.Debug.toString())) {
            config.Game.LogLevel = Application.LOG_DEBUG;
        } else if(logLevel.equals(LogLevel.Info.toString())) {
            config.Game.LogLevel = Application.LOG_INFO;
        } else if(logLevel.equals(LogLevel.Error.toString())) {
            config.Game.LogLevel = Application.LOG_ERROR;
        } else {
            config.Game.LogLevel = Application.LOG_NONE;
        }
    }
}
