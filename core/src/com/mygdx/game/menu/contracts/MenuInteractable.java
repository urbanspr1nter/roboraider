/*
 * MenuInteractable.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * The interface for creating interactable menus with a Three-quarters
 * view.
 */

package com.mygdx.game.menu.contracts;

import com.mygdx.game.menu.enums.MenuInteractionDialogNames;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface MenuInteractable {
    void setData(Map<?, ?> data);
    String transformData();
    List<Point> getCursorLocations();
    void render();
    MenuInteractionDialogNames getDialogName();
}
