package com.mygdx.game.input.adapters;

import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.input.handlers.contracts.KeyPressHandler;

public abstract class GameInputAdapter extends InputAdapter implements KeyPressHandler {
    private boolean keyDown;

    GameInputAdapter() {
        super();
    }

    public void setKeyDown(boolean state) {
        this.keyDown = state;
    }

    public boolean isKeyDown() {
        return this.keyDown;
    }

    public abstract boolean keyUp(int keyCode);
    public abstract boolean keyDown(int keyCode);
}
