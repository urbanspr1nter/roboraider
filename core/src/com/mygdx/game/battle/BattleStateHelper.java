/*
 * BattleStateHelper.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Utility methods to handle battles and exit battles if needed.
 */

package com.mygdx.game.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameStore;
import com.mygdx.game.graphics.GraphicsHelper;
import com.mygdx.game.graphics.helpers.FadeTransitionOut;
import com.mygdx.game.logging.Logger;
import com.mygdx.game.state.BattleState;
import com.mygdx.game.state.LocalMapState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class BattleStateHelper {
    private static int MINIMUM_STEPS = 10;

    private GameStore store;
    private int currentBattleStepThreshold;

    public BattleStateHelper(GameStore store) {
        this.store = store;
        this.currentBattleStepThreshold = 0;
    }

    public void resetStepsToBattle() {
        Random stepThresholdForBattleGenerator = new Random(new Date().getTime());
        for(int i = 0; i < 6; i++) {
            this.currentBattleStepThreshold = stepThresholdForBattleGenerator.nextInt(100) + 1;
        }
        if(this.currentBattleStepThreshold <= MINIMUM_STEPS) {
            this.currentBattleStepThreshold *= 5;
        } else {
            this.currentBattleStepThreshold = stepThresholdForBattleGenerator.nextInt(100) + 1;
        }

        this.store.setSteps(this.currentBattleStepThreshold);

        Logger.log("STEPS UNTIL NEXT BATTLE: " + this.currentBattleStepThreshold);
    }

    public void exitBattleIfNeeded() {
        if(this.store.battleInteractionState != null
                && this.store.battleInteractionState.currentStage() == CombatStage.Exited) {
            this.store.stateMachine.exitState();
            this.currentBattleStepThreshold = 0;
            this.store.battleInteractionState = null;

            // Go back to map;
            this.store.stateMachine.setState(
                this.store.stateMachine.getPreviousStateKey(),
                new LocalMapState(this.store, this.store.stateMachine.getPreviousEntity())
            );
        }
    }

    public void goToBattleIfNeeded() {
        if(this.store.battleInteractionState != null) {
            // We are in battle, don't disturb.
            return;
        }
        if(this.store.currentEntity().getEnemies().size() == 0) {
            // This map does not have any random encounters.

            // If current steps are less than the threshold, then reset.
            if(this.store.getTotalSteps() <= 0) {
                this.resetStepsToBattle();
            }

            return;
        }

        if(this.currentBattleStepThreshold <= 0) {
            this.resetStepsToBattle();
        }

        if(this.store.getTotalSteps() <= 0) {
            this.store.disableKeyPress();
            this.store.setSteps(1); // Increment steps to not go into a weird battle state loop.

            this.store.callbackQueue.registerImmediate(() -> {
                Color initialColor = new Color(0, 0, 0, 0.0f);
                FadeTransitionOut fOut = new FadeTransitionOut();

                if(fOut.render(this.store, new GraphicsHelper().getFadeProps(1, initialColor))) {
                    this.store.stateMachine.exitState();

                    this.store.battleInteractionState = new BattleInteractionState(this.store);
                    this.store.battleInteractionState.setEnemies(this.generateEnemyListForEncounter());
                    this.store.battleInteractionState.setCurrentBackground(
                        new Texture(this.store.configuration.Assets.Registry
                                .get("WarehouseBattleBackground").File)
                    );

                    this.store.stateMachine.setState(
                            "BattleScreen",
                            new BattleState(this.store, this.store.entityLibrary.get("BattleScreen"))
                    );

                    this.store.enableKeypress();
                    return true;
                }
                return false;
            });

        }
    }

    private List<String> generateEnemyListForEncounter() {
        int maxEnemies = 4;

        Random totalEnemiesGenerator = new Random(new Date().getTime());
        int totalEnemies = totalEnemiesGenerator.nextInt(maxEnemies) + 1;

        List<String> enemyList = new ArrayList<>();
        for(int i = 0; i < totalEnemies; i++) {
            int idx = 0;
            for(int j = 0; j < 3; j++) {
                Random generatorToPickEnemyFromList = new Random(new Date().getTime());
                idx = generatorToPickEnemyFromList.nextInt(this.store.currentEntity().getEnemies().size());
            }

            String enemyFile = this.store.currentEntity().getEnemies().get(idx);
            enemyList.add(enemyFile);
        }

        return enemyList;
    }

}
