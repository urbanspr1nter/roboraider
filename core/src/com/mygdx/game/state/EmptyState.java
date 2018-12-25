package com.mygdx.game.state;

public class EmptyState extends BaseState {
    public EmptyState() {
        super(null, null);
    }

    public void onEnter() {
        return;
    }

    public void onUpdate() {
        // Empty
        return;
    }

    public void onExecute() {
        // Empty
        return;
    }

    public void onExit() {
        // Empty
        return;
    }
}
