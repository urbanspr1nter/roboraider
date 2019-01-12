package com.mygdx.game.battle.handlers;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.enums.CombatStage;
import com.mygdx.game.graphics.GraphicsHelper;
import com.mygdx.game.graphics.helpers.FadeTransitionOut;
import com.mygdx.game.util.Timer;

public class BattleEndDeath extends BattleHandler {
    private Timer battleEndDeathTimer;
    private GraphicsHelper gfxHelper;

    public BattleEndDeath(GameStore store) {
        super(store);
        this.battleEndDeathTimer = new Timer(6000);

        this.gfxHelper = new GraphicsHelper();
    }

    @Override
    public void handle(CombatStage stage) {
        this.uiManager.getInformationalBox().setMessage("Annihilated\n......");
        this.uiManager.getInformationalBox().render();

        this.battleEndDeathTimer.run(() -> {
            this.store.callbackQueue.register(() -> {
                FadeTransitionOut fOut = new FadeTransitionOut();

                boolean hasFadeResolved = fOut.render(this.store, this.gfxHelper.getFadeProps(8, Color.BLACK));

                if(hasFadeResolved) {
                    this.battleEndDeathTimer.reset(3000);
                    this.store.battleInteractionState.moveToStage(CombatStage.BattleEndDeathExit);
                    return true;
                }
                return false;
            });
            return true;
        });
    }
}
