/*
 * UtilityBox.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * For the battle screen, this is the box in which the user will make crucial
 * decisions in such as targeting the monster, skill to be used, or item to be used.
 */

package com.mygdx.game.ui.battle;

import com.mygdx.game.GameStore;
import com.mygdx.game.ui.DialogBox;
import com.mygdx.game.ui.DialogBoxPositions;
import com.mygdx.game.ui.enums.DialogBoxSize;

import java.util.List;

public class UtilityBox {
    private final static String EmptyItem = "--------";

    private GameStore store;
    private DialogBox dialogBox;
    private List<?> data;

    public UtilityBox(GameStore store) {
        this.store = store;
        this.dialogBox = new DialogBox(this.store.configuration);
    }

    public void render() {
        if(!this.dialogBox.visible()) {
            this.dialogBox.toggle();
        }

        this.dialogBox.display(this.transformData(), DialogBoxSize.Large, DialogBoxPositions.LargeRightBottom());
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    private String transformData() {
        StringBuilder sb = new StringBuilder();

        for(Object d : this.data) {
            if(d == null) {
                sb.append(EmptyItem);
            } else {
                sb.append(d.toString());
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
