package com.mygdx.game.ui.battle;

import com.mygdx.game.GameStore;
import com.mygdx.game.character.items.Item;
import com.mygdx.game.ui.DialogBox;
import com.mygdx.game.ui.DialogBoxPositions;
import com.mygdx.game.ui.enums.DialogBoxSize;

import java.util.Map;

public class ItemBox {
    private GameStore store;
    private DialogBox dialogBox;
    private Map<?, ?> data;

    public ItemBox(GameStore store) {
        this.store = store;
        this.dialogBox = new DialogBox(this.store.configuration);
    }

    public void render() {
        if(!this.dialogBox.visible()) {
            this.dialogBox.toggle();
        }

        this.dialogBox.display(this.transformData(), DialogBoxSize.XLarge, DialogBoxPositions.XLargeRightBottom());
    }

    public void setData(Map<?, ?> data) {
        this.data = data;
    }

    private String transformData() {
        StringBuilder sb = new StringBuilder();

        int i = 0;
        for(Object d : this.data.keySet()) {
            if(i % 3 == 0 && i != 0) {
                sb.append(System.lineSeparator());
            }

            Item currItem = (Item)d;
            Integer amount = (Integer)this.data.get(currItem);

            sb.append(currItem.getName() + " (" + amount + ") ");

            sb.append("          ");

            i++;
        }

        return sb.toString();
    }
}
