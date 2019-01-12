package com.mygdx.game.battle.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;
import com.mygdx.game.graphics.helpers.FadeTransitionOut;

public class DoneAction extends BattleHandler {
    public DoneAction(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        FadeTransitionOut ft = new FadeTransitionOut();

        if(ft.render(this.store, this.uiHelper.getFadeProps(3, new Color(1.0f, 1.0f, 1.0f, 0.0f)))) {
            this.store.battleInteractionState.moveToStage(CombatStage.Exited);
            Gdx.gl20.glClearColor(0, 0, 0, 1.0f);
        }
    }
}
