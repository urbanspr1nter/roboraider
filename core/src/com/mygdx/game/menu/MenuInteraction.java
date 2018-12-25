package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameStore;
import com.mygdx.game.menu.contracts.MenuInteractable;
import com.mygdx.game.menu.enums.MenuInteractionDialogNames;
import com.mygdx.game.ui.DialogBox;
import com.mygdx.game.ui.DialogBoxPositions;
import com.mygdx.game.ui.enums.DialogBoxSize;

import java.awt.*;
import java.util.List;
import java.util.Map;

public abstract class MenuInteraction<E> implements MenuInteractable {
    private Point positionForDialogBoxRender;
    private float elapsedTime;

    protected GameStore store;
    protected DialogBox displayBox;

    public MenuInteraction(GameStore store) {
        this.store = store;
        this.displayBox = new DialogBox(this.store.configuration);

        this.positionForDialogBoxRender = new Point(448, 432);
        this.elapsedTime = 0;
    }

    public abstract void setData(Map<?, ?> data);
    public abstract String transformData();
    public abstract List<Point> getCursorLocations();
    public abstract MenuInteractionDialogNames getDialogName();

    private void updateDisplayBoxPosition() {
        int stopPositionX = 32;

        float deltaTime = Gdx.graphics.getDeltaTime();
        this.elapsedTime += deltaTime * 1000;

        if(this.elapsedTime >= 16 && this.positionForDialogBoxRender.x > stopPositionX) {
            this.positionForDialogBoxRender.x -= 64;
            this.elapsedTime = 0;
        } else if(this.positionForDialogBoxRender.x <= stopPositionX) {
            this.positionForDialogBoxRender.x = DialogBoxPositions.ThreeQuarters().x;
        }
    }

    @Override
    public void render() {
        if(!this.displayBox.visible()) {
            this.displayBox.toggle();
        }

        this.displayBox.display(
            this.transformData(),
            DialogBoxSize.ThreeQuarters,
            this.positionForDialogBoxRender
        );

        this.updateDisplayBoxPosition();
    }

    public String toDisplayString(String value) {
        int maximumStringLength = 8;

        StringBuilder sb = new StringBuilder();

        if(value.length() > maximumStringLength) {
            sb.append(value.substring(0, maximumStringLength));
        } else if(value.length() < maximumStringLength) {
            sb.append(value);
            for(int i = 0; i < maximumStringLength - value.length(); i++) {
                sb.append(" ");
            }
        } else {
            sb.append(value);
        }

        return sb.toString();
    }
}
