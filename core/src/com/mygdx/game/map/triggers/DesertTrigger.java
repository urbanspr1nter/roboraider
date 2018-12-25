package com.mygdx.game.map.triggers;

import com.mygdx.game.Direction;
import com.mygdx.game.GameStore;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.sprite.SpriteOrientation;
import com.mygdx.game.state.LocalMapState;
import com.mygdx.game.state.StateMachine;

public class DesertTrigger implements Triggerable {
    private StateMachine stateMachine;
    private GameStore gameStore;
    private Configuration config;

    public DesertTrigger(GameStore gameStore, StateMachine stateMachine) {
        this.stateMachine = stateMachine;
        this.gameStore = gameStore;
        this.config = this.gameStore.configuration;
    }
    @Override
    public void trigger() {
        this.gameStore.currentEntity().getCameraManager().updateInputDirection(Direction.DOWN);
        this.gameStore.currentEntity().getPlayer().getSpriteData().setOrientation(SpriteOrientation.DOWN);

        this.stateMachine.exitState();
        this.stateMachine.setState(
            "Desert",
            new LocalMapState(this.gameStore, this.gameStore.entityLibrary.get("Desert"))
        );
    }
}
