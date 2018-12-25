/*
 * MenuState.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * The menu state!
 */

package com.mygdx.game.state;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.GameStore;
import com.mygdx.game.GameStoreEntity;
import com.mygdx.game.logging.Logger;
import com.mygdx.game.graphics.helpers.FadeTransitionIn;
import com.mygdx.game.menu.enums.MenuInteractionDialogNames;
import com.mygdx.game.menu.contracts.MenuInteractable;
import com.mygdx.game.ui.DialogBoxPositions;
import com.mygdx.game.ui.enums.DialogBoxSize;
import com.mygdx.game.util.GameUtils;
import com.mygdx.game.util.ListUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MenuState extends BaseState {
    private static String PLAY_TIME_LABEL = "PLAY TIME";
    private static String WALLET_LABEL = "WALLET";
    private static Point NAVIGATION_CURSOR_START = new Point(443, 427);

    private ListUtils<String> listUtils;
    private List<String> navigationElements;

    public MenuState(GameStore store, GameStoreEntity newEntity) {
        super(store, newEntity);

        this.listUtils = new ListUtils<>();
        this.navigationElements = this.store.menuInteractionState.getNavigationElements();
    }

    @Override
    protected void onEnter() {
        this.store.menuInteractionState.setCursorCellPosition(NAVIGATION_CURSOR_START);
        Logger.log("Entering menu.");
    }

    @Override
    protected void onUpdate() { }

    @Override
    protected void onExecute() {
        if(!this.store.menuInteractionState.getInteracting()) {
            return;
        }

        this.toggleAllMenusVisible();

        if(this.store.menuInteractionState.getMenuNavigationStack().hasMenuViewOnStack()) {
            MenuInteractable topOfStack = this.store.menuInteractionState.getMenuNavigationStack().peekMenuViewOnStack();
            topOfStack.render();

            if(this.store.menuInteractionState.getCursorVisible()) {
                if(topOfStack.getDialogName() == MenuInteractionDialogNames.Items) {
                    this.store.menuInteractionState.setCursorCellPosition(
                        this.store.menuInteractionState.getMenuNavigationStack().peekMenuViewOnStack().getCursorLocations()
                            .get(this.store.menuInteractionState.getCurrentItemChoice())
                    );
                } else if(topOfStack.getDialogName() == MenuInteractionDialogNames.Equipment) {
                    this.store.menuInteractionState.setCursorCellPosition(
                        this.store.menuInteractionState.getMenuNavigationStack().peekMenuViewOnStack().getCursorLocations()
                            .get(this.store.menuInteractionState.getCurrentEquipmentAreaChoice())
                    );
                } else if(topOfStack.getDialogName() == MenuInteractionDialogNames.Statistics
                        || topOfStack.getDialogName() == MenuInteractionDialogNames.Navigation) {
                    // Handle statistics and navigation as the same...
                    if(this.store.menuInteractionState.getCurrentNavigationCursorChoice() >= this.navigationElements.size()) {
                        this.store.menuInteractionState.decrementNavigationCursorChoice();
                    }

                    this.store.menuInteractionState.setCursorCellPosition(new Point(
                        NAVIGATION_CURSOR_START.x,
                        NAVIGATION_CURSOR_START.y - (42 * this.store.menuInteractionState.getCurrentNavigationCursorChoice())
                    ));
                } else if(topOfStack.getDialogName() == MenuInteractionDialogNames.Skills) {
                    this.store.menuInteractionState.setCursorCellPosition(
                        this.store.menuInteractionState.getMenuNavigationStack().peekMenuViewOnStack()
                            .getCursorLocations().get(this.store.menuInteractionState.getCurrentSkillChoice())
                    );
                } else if(topOfStack.getDialogName() == MenuInteractionDialogNames.Configuration) {

                } else if(topOfStack.getDialogName() == MenuInteractionDialogNames.Save) {

                }
            }
        }

        this.renderBaseMenuView();
        this.renderCursorPosition();
        this.renderConfirmationBox();
    }

    @Override
    protected void onExit() {
        this.store.menuInteractionState.toggleAll();
        this.store.menuInteractionState.setInteracting(false);
        this.store.menuInteractionState.setCursorVisible(false);

        this.store.callbackQueue.registerImmediate(() -> {
            Map<String, Object> props = new HashMap<>();
            props.put("color", Color.BLACK);
            props.put("seconds", 1);

            return new FadeTransitionIn().render(this.store, props);
        });
    }

    private void toggleAllMenusVisible() {
        if(!this.store.menuInteractionState.isMenuAllVisible()) {
            this.store.menuInteractionState.setCursorVisible(true);
            this.store.menuInteractionState.toggleAll();
        }
    }

    private void renderCursorPosition() {
        this.store.menuInteractionState.getCursor().draw(this.store.menuInteractionState.getCursorCellPosition());
    }

    private void renderConfirmationBox() {
        if(this.store.menuInteractionState.confirmationBox.isValid()) {
            this.store.menuInteractionState.confirmationBox.render();
        }
    }

    private void renderBaseMenuView() {
        String doubleLineSeparator = System.lineSeparator() + System.lineSeparator();
        String navigationElementsToDisplay = this.listUtils.join(doubleLineSeparator, this.navigationElements);
        String timeElementsToDisplay = PLAY_TIME_LABEL
                + doubleLineSeparator
                + GameUtils.durationMsToFormattedTime(this.store.totalPlayTime);
        String walletElementsToDisplay = WALLET_LABEL + doubleLineSeparator + this.store.playerData.getGold();

        this.store.menuInteractionState.getDialog(MenuInteractionDialogNames.Navigation)
            .display(navigationElementsToDisplay, DialogBoxSize.QuarterHalf, DialogBoxPositions.Quarter());

        this.store.menuInteractionState.getDialog(MenuInteractionDialogNames.Time)
            .display(timeElementsToDisplay, DialogBoxSize.QuarterQuarter, DialogBoxPositions.QuarterQuarterTime());

        this.store.menuInteractionState.getDialog(MenuInteractionDialogNames.Wallet)
            .display(walletElementsToDisplay, DialogBoxSize.QuarterQuarter, DialogBoxPositions.QuarterQuarterWallet());
    }
}
