package com.mygdx.game.input.adapters;

import com.mygdx.game.GameStore;
import com.mygdx.game.input.handlers.contracts.KeyPressHandler;

public class MenuScreenInputAdapter extends GameInputAdapter {
    private KeyPressHandler keyPressHandler;
    private GameStore store;

    public MenuScreenInputAdapter(GameStore gameStore, KeyPressHandler keyHandler) {
        super();

        this.keyPressHandler = keyHandler;
        this.store = gameStore;
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
            this.keyPressHandler.handleKeyPress(keyCode);
        }
    }
}
