package com.mygdx.game.state.contracts;

public interface State {
    void enter();
    void update();
    void execute();
    void exit();
}
