package com.mygdx.game.menu;

import com.mygdx.game.GameStore;
import com.mygdx.game.menu.enums.MenuInteractionDialogNames;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class MenuConfigurationInteraction extends MenuInteraction<String>  {
    public MenuConfigurationInteraction(GameStore store) {
        super(store);
    }

    @Override
    public void setData(Map<?, ?> data) {

    }

    @Override
    public String transformData() {
        return "CONFIGURATION - TODO!";
    }

    @Override
    public List<Point> getCursorLocations() {
        return null;
    }

    @Override
    public MenuInteractionDialogNames getDialogName() {
        return MenuInteractionDialogNames.Configuration;
    }
}
