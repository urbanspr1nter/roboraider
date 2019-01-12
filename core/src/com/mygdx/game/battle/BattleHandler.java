package com.mygdx.game.battle;

import com.mygdx.game.GameStore;
import com.mygdx.game.audio.UiSounds;
import com.mygdx.game.battle.contracts.BattleInvokable;
import com.mygdx.game.battle.enums.CombatStage;

public abstract class BattleHandler implements BattleInvokable {
    protected GameStore store;
    protected BattleUiManager uiManager;
    protected BattleUiSounds battleUiSounds;
    protected BattleUiHelper uiHelper;
    protected UiSounds uiSounds;

    protected BattleHandler(GameStore store) {
        this.store = store;
        this.uiManager = (BattleUiManager)this.store.battleInteractionState.getServiceContainer().get(BattleUiManager.class);
        this.battleUiSounds = (BattleUiSounds)this.store.battleInteractionState.getServiceContainer().get(BattleUiSounds.class);
        this.uiHelper = (BattleUiHelper)this.store.battleInteractionState.getServiceContainer().get(BattleUiHelper.class);
        this.uiSounds = (UiSounds)this.store.battleInteractionState.getServiceContainer().get(UiSounds.class);
    }

    public abstract void handle(CombatStage stage);

}
