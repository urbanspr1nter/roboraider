package com.mygdx.game.battle.handlers;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.GameStore;
import com.mygdx.game.battle.BattleHandler;
import com.mygdx.game.battle.CombatStage;
import com.mygdx.game.graphics.GraphicsHelper;
import com.mygdx.game.graphics.helpers.FadeTransitionIn;

public class ActionAttackApplyComplete extends BattleHandler {
    private GraphicsHelper gfxHelper;
    private boolean soundLock;

    public ActionAttackApplyComplete(GameStore store) {
        super(store);
        this.gfxHelper = new GraphicsHelper();
        this.soundLock = false;
    }

    @Override
    public void handle(CombatStage stage) {
        if(this.store.battleInteractionState.getUtilityBoxChoice() == -1) {
            this.attackSelf(stage);
        } else {
            this.attackEnemy(stage);
        }
    }

    private void attackSelf(CombatStage stage) {
        int damage = this.calculateSelfDamage();

        this.uiManager.getInformationalBox().setMessage("Took " + damage + " HP\nfrom self!");
        this.uiManager.getInformationalBox().render();

        this.store.battleInteractionState.getTimer().run(() -> {
            if(!this.soundLock) {
                this.battleUiSounds.playWeaponSlice();
                this.soundLock = true;
            }

            this.store.callbackQueue.register(() -> {
                FadeTransitionIn fIn = new FadeTransitionIn();
                boolean hasFadeResolved = fIn.render(this.store, this.gfxHelper.getFadeProps(1, Color.RED));
                if(hasFadeResolved) {
                    int finalHp = this.store.playerData.getPlayerStatistics().getCurrentHp() - damage;

                    this.store.playerData.getPlayerStatistics().setCurrentHp(finalHp);

                    this.store.battleInteractionState.getTimer().reset(833);

                    if(this.store.playerData.getPlayerStatistics().getCurrentHp() == 0) {
                        this.store.battleInteractionState.moveToStage(CombatStage.BattleEndDeath);
                    } else {
                        this.store.battleInteractionState.moveToStage(CombatStage.DelayEnemyAttack);
                    }

                    this.soundLock = false;

                    return true;
                }
                return false;
            });

            return true;
        });
    }

    private int calculateSelfDamage() {
        return (int)(Math.round(this.store.playerData.getPlayerStatistics().getAttack()
                - (this.store.playerData.getPlayerStatistics().getDefense() * 0.67)));
    }

    private void attackEnemy(CombatStage stage) {
        if(this.store.playerData.getPlayerStatistics().getCurrentHp() == 0) {
            this.store.battleInteractionState.getTimer().run(() -> {
                this.store.battleInteractionState.moveToStage(CombatStage.BattleEndDeath);

                return true;
            });
        } else {
            this.store.battleInteractionState.getTimer().run(() -> {
                if(this.store.battleInteractionState.getTargetted().getDeath()) {
                    this.store.battleInteractionState.moveToStage(CombatStage.WaitingAction);
                } else {
                    this.store.battleInteractionState.moveToStage(CombatStage.EnemyAttack);
                }

                this.store.battleInteractionState.getTimer().reset(250);

                return true;
            });
        }
    }
}
