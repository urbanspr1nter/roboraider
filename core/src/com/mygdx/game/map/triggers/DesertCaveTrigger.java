package com.mygdx.game.map.triggers;

import com.mygdx.game.Direction;
import com.mygdx.game.GameStore;
import com.mygdx.game.GameStoreEntity;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.sprite.SpriteOrientation;
import com.mygdx.game.state.LocalMapState;
import com.mygdx.game.state.StateMachine;
import com.mygdx.game.util.TileUtils;

public class DesertCaveTrigger implements Triggerable {
    private StateMachine stateMachine;
    private GameStore gameStore;
    private Configuration config;

    public DesertCaveTrigger(GameStore gameStore, StateMachine stateMachine) {
        this.stateMachine = stateMachine;
        this.gameStore = gameStore;
        this.config = this.gameStore.configuration;
    }

    public void trigger() {
        this.gameStore.currentEntity().getCameraManager().updateInputDirection(Direction.DOWN);
        this.gameStore.currentEntity().getPlayer().getSpriteData().setOrientation(SpriteOrientation.DOWN);

        this.stateMachine.exitState();

        GameStoreEntity entity = this.gameStore.entityLibrary.get("Dungeon-1");
        this.stateMachine.setState("Dungeon-1", new LocalMapState(this.gameStore, entity));

        this.gameStore.callbackQueue.register(() -> {
            this.gameStore.currentEntity().getCameraManager().setInitialPosition(
                    TileUtils.getRealLocationFromCell(
                            this.gameStore.configuration,
                            entity.getPlayer().getStartingLocation()
                    )
            );

            this.gameStore.currentEntity().getCameraManager().updateInputDirection(Direction.DOWN);
            this.gameStore.currentEntity().getPlayer().getSpriteData().setOrientation(SpriteOrientation.DOWN);
            this.gameStore.currentEntity().updatePlayerPosition();

            return true;
        });

    }
}
