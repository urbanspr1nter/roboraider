package com.mygdx.game.battle.handlers;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;
import com.mygdx.game.graphics.helpers.FadeTransitionIn;
import com.mygdx.game.objects.Monster;

import java.util.Date;
import java.util.Random;

public class EnemyAttack extends BattleHandler {
    public EnemyAttack(GameStore store) {
        super(store);
    }

    @Override
    public void handle(CombatStage stage) {
        // STUPID STUB FOR NOW!

        if(this.store.battleInteractionState.getEnemyIndex() >= this.store.battleInteractionState.getMonsters().size()) {
            this.store.battleInteractionState.setEnemyIndex(0);
        }

        Monster m = this.store.battleInteractionState.getMonsters().get(this.store.battleInteractionState.getEnemyIndex());

        this.uiManager.getInformationalBox()
                .setMessage(m.getProfile().getName() + "\nattacks!");
        this.uiManager.getInformationalBox().render();

        this.store.battleInteractionState.getTimer().run(() -> {
            m.playAttackSound();
            this.store.callbackQueue.register(() -> {
                FadeTransitionIn ft = new FadeTransitionIn();

                if(ft.render(this.store, this.uiHelper.getFadeProps(1, new Color(0.7f, 0.7f, 0.7f, 0.5f)))) {
                    Random rand = new Random(new Date().getTime());

                    this.store.battleInteractionState.setEnemyIndex(
                            rand.nextInt(this.store.battleInteractionState.getMonsters().size())
                    );

                    if(this.store.battleInteractionState.getEnemyIndex() == this.store.battleInteractionState.getMonsters().size()) {
                        this.store.battleInteractionState.setEnemyIndex(0);
                    }
                    this.store.playerData.applyAttack(m);

                    this.store.battleInteractionState.moveToStage(CombatStage.WaitingAction);

                    return true;
                }

                return false;
            });

            return true;
        });
    }
}
