package com.mygdx.game.map.triggers.dialogs;

import com.mygdx.game.GameStore;
import com.mygdx.game.GameStoreEntity;
import com.mygdx.game.dialog.Graph;
import com.mygdx.game.objects.Dialog;
import com.mygdx.game.objects.NonPlayableCharacter;
import com.mygdx.game.util.TileUtils;

import java.awt.*;

public class NpcDialogLevelUpTriggerHandler extends NpcDialogTriggerHandler {

    private GameStore store;
    private boolean actionTriggered;

    public NpcDialogLevelUpTriggerHandler(GameStore store, Dialog dialog) {
        super(store, dialog);

        this.store = store;
    }

    @Override
    public void trigger() {
        super.trigger();

        if(!this.actionTriggered) {
            this.store.callbackQueue.register(() -> {
                if(this.store.npcInteractionState.getInteraction()) {
                    return false;
                }
                for(int i = 0; i < 49; i++) {
                    this.store.playerData.levelUp();
                }

                this.store.currentEntity().getSpriteRegistry().remove("Crono");
                this.store.currentEntity().getSpriteRegistry().remove("Magus");
                this.store.currentEntity().getSpriteRegistry().remove("Marle");

                GameStoreEntity currentEntity = this.store.currentEntity();
                Dialog magusDialog = new Dialog(new Graph("dialog/CharacterMagus.json"));
                currentEntity.getSpriteRegistry().register(
                        new NonPlayableCharacter(
                                this.config,
                                "Magus",
                                this.config.Assets.Registry.get("CharacterMagusOg").File,
                                TileUtils.getCellFromRealLocation(this.config, new Point(740, 550)),
                                new NpcDialogTriggerHandler(this.store, magusDialog)
                        )
                );


                Dialog cronoDialog = new Dialog(new Graph("dialog/CharacterCrono.json"));
                currentEntity.getSpriteRegistry().register(
                        new NonPlayableCharacter(
                                this.config,
                                "Crono",
                                this.config.Assets.Registry.get("CharacterCronoOg").File,
                                TileUtils.getCellFromRealLocation(this.config, new Point(600, 400)),
                                new NpcDialogTriggerHandler(this.store, cronoDialog)
                        )
                );

                Dialog marleDialog = new Dialog(new Graph("dialog/Halloween2.json"));
                currentEntity.getSpriteRegistry().register(
                        new NonPlayableCharacter(
                                this.config,
                                "Marle",
                                this.config.Assets.Registry.get("CharacterMarleOg").File,
                                TileUtils.getCellFromRealLocation(this.config, new Point(570, 400)),
                                new NpcDialogTriggerHandler(this.store, marleDialog)
                        )
                );

                this.config.Game.Watermark.Display = false;

                return true;
            });
        }

        this.actionTriggered = true;
    }
}
