package com.mygdx.game.input.adapters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameConstants;
import com.mygdx.game.GameStore;
import com.mygdx.game.configuration.ConfigurationBuilder;
import com.mygdx.game.input.handlers.contracts.KeyPressHandler;

public class LocalMapInputAdapter extends GameInputAdapter {
    private KeyPressHandler keyHandler;
    private GameStore gameStore;

    public LocalMapInputAdapter(GameStore gameStore, KeyPressHandler keyHandler) {
        super();

        this.keyHandler = keyHandler;
        this.gameStore = gameStore;
    }

    @Override
    public boolean keyUp(int keyCode) {
        this.setKeyDown(false);

        return false;
    }

    @Override
    public boolean keyDown(int keyCode) {
        this.setKeyDown(true);

        this.handleKeyPress(keyCode);

        return false;
    }

    public void handleKeyPress(int keyCode) {
        if(this.isKeyDown()) {
            this.keyHandler.handleKeyPress(keyCode);

            /// WTF is this for again? ... why did i do this again?
            // **** remember to just quickly refactor this... 10-14-2018
            if(this.gameStore.npcInteractionState.getInteraction()) {
                this.setKeyDown(false);
            } else if((!Gdx.input.isKeyPressed(Input.Keys.UP)
                        && !Gdx.input.isKeyPressed(Input.Keys.DOWN)
                        && !Gdx.input.isKeyPressed(Input.Keys.LEFT)
                        && !Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            ) {
                this.setKeyDown(false);
            }
            this.delay();
        }
    }

    private void delay() {
        try {
            Thread.sleep(GameConstants.RenderDelay(ConfigurationBuilder.config().Game.Fps));
        } catch(Exception e) {
            Gdx.app.error("ERROR-LocalMapState-Frame-Render",
                    "Could not delay the thread to render the next frame.");
        }
    }
}
