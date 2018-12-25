/*
 * MenuNavigationStack.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * The menu navigation stack. Used to manage which screen should be displayed.
 */
package com.mygdx.game.menu.utils;

import com.mygdx.game.GameStore;
import com.mygdx.game.logging.Logger;
import com.mygdx.game.menu.*;
import com.mygdx.game.menu.enums.MenuInteractionDialogNames;
import com.mygdx.game.menu.contracts.MenuInteractable;

import java.util.HashMap;
import java.util.Stack;

public class MenuNavigationStack {
    private GameStore store;
    private Stack<MenuInteractable> menuInteractionStack;

    public MenuNavigationStack(GameStore store) {
        this.store = store;
        this.menuInteractionStack = new Stack<>();
    }

    public boolean hasMenuViewOnStack() {
        return this.menuInteractionStack.size() > 0;
    }

    public MenuInteractable peekMenuViewOnStack() {
        return this.menuInteractionStack.peek();
    }

    private void pushMenuViewToStack(MenuInteractable menuInteractable) {
        if(this.menuInteractionStack.size() > 0 && this.menuInteractionStack.peek() == menuInteractable) {
            return;
        }

        this.menuInteractionStack.push(menuInteractable);

        Logger.log("Pushed " + menuInteractable.getClass().getSimpleName() + " to the stack.");
    }

    public void pushViewToNavigationStack(MenuInteractionDialogNames name) {
        MenuInteractable interactableMenu = null;

        if(name == MenuInteractionDialogNames.Items) {
            interactableMenu = new MenuItemsInteraction(store);
            interactableMenu.setData(this.store.playerData.getInventory());
        } else if(name == MenuInteractionDialogNames.Equipment) {
            interactableMenu = new MenuEquipmentInteraction(store);
            interactableMenu.setData(this.store.playerData.getEquipped());
        } else if(name == MenuInteractionDialogNames.Statistics || name == MenuInteractionDialogNames.Navigation) {
            interactableMenu = new MenuStatisticsInteraction(store);
            interactableMenu.setData(new HashMap<>());
        } else if(name == MenuInteractionDialogNames.Skills) {
            interactableMenu = new MenuSkillsInteraction(store);
            interactableMenu.setData(this.store.playerData.getSkills());
        } else if(name == MenuInteractionDialogNames.Configuration) {
            interactableMenu = new MenuConfigurationInteraction(this.store);
            interactableMenu.setData(new HashMap<>());
        } else if(name == MenuInteractionDialogNames.Save) {
            interactableMenu = new MenuSaveInteraction(this.store);
            interactableMenu.setData(new HashMap<>());
        }

        this.pushMenuViewToStack(interactableMenu);
    }


    public void popMenuViewFromStack() {
        if(this.menuInteractionStack.size() == 0) {
            return;
        }

        MenuInteractable m = this.menuInteractionStack.pop();

        if(this.menuInteractionStack.size() <= 0) {
            this.pushViewToNavigationStack(MenuInteractionDialogNames.Navigation);
        }

        Logger.log("Popped " + m.getDialogName() + " from the stack.");
    }

    public int size() {
        return this.menuInteractionStack.size();
    }
}
