package com.mygdx.game.input.adapters;

import com.mygdx.game.GameStore;
import com.mygdx.game.input.handlers.contracts.KeyPressHandler;

public class TitleScreenInputAdapter extends GameInputAdapter {
    private KeyPressHandler keyHandler;
    private GameStore gameStore;

    public TitleScreenInputAdapter(GameStore gameStore, KeyPressHandler keyHandler) {
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
        }
    }

}
