package com.mygdx.game.battle;

import com.mygdx.game.GameStore;
import com.mygdx.game.ui.battle.*;

public class BattleUiManager {
    private GameStore store;
    private PlayerInformationHeader playerInformationHeader;
    private ActionChoiceBox actionChoiceBox;
    private UtilityBox utilityBox;
    private InformationalBox informationalBox;
    private ItemBox itemBox;

    public BattleUiManager(GameStore store) {
        this.store = store;
        this.playerInformationHeader = new PlayerInformationHeader(this.store);
        this.actionChoiceBox = new ActionChoiceBox(this.store);
        this.utilityBox = new UtilityBox(this.store);
        this.informationalBox = new InformationalBox(this.store);
        this.itemBox = new ItemBox(this.store);
    }

    public PlayerInformationHeader getPlayerInformationHeader() {
        return this.playerInformationHeader;
    }

    public ActionChoiceBox getActionChoiceBox() {
        return this.actionChoiceBox;
    }

    public UtilityBox getUtilityBox() {
        return this.utilityBox;
    }

    public InformationalBox getInformationalBox() {
        return this.informationalBox;
    }

    public ItemBox getItemBox() {
        return this.itemBox;
    }
}
