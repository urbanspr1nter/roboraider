package com.mygdx.game.ui.battle;

import com.mygdx.game.GameStore;
import com.mygdx.game.ui.DialogBox;
import com.mygdx.game.ui.DialogBoxPositions;
import com.mygdx.game.ui.enums.DialogBoxSize;

public class ActionChoiceBox {
    private GameStore store;
    private DialogBox dialogBox;

    public ActionChoiceBox(GameStore store) {
        this.store = store;
        this.dialogBox = new DialogBox(this.store.configuration);
    }

    public void render() {
        if(!this.dialogBox.visible()) {
            this.dialogBox.toggle();
        }

        this.dialogBox.display(this.getChoices(), DialogBoxSize.SmallThin, DialogBoxPositions.SmallLeftBottom());
    }

    private String getChoices() {
        StringBuilder sb = new StringBuilder();

        sb.append("ATTACK");
        sb.append(System.lineSeparator());
        sb.append("SKILL");
        sb.append(System.lineSeparator());
        sb.append("ITEM");
        sb.append(System.lineSeparator());
        sb.append("RUN");

        return sb.toString();
    }


}
