package com.mygdx.game.menu;

import com.mygdx.game.GameStore;
import com.mygdx.game.logging.Logger;
import com.mygdx.game.audio.UiSounds;
import com.mygdx.game.character.enums.EquipmentAreas;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.menu.enums.MenuInteractionDialogNames;
import com.mygdx.game.menu.utils.MenuNavigationStack;
import com.mygdx.game.ui.ConfirmationBox;
import com.mygdx.game.ui.Cursor;
import com.mygdx.game.ui.DialogBox;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MenuInteractionState {
    private static Point NAVIGATION_CURSOR_START = new Point(443, 427);

    private static class CursorOnEquipmentArea {
        final static int LeftHand = 0;
        final static int RightHand = 1;
        final static int Accessory = 2;
        final static int Head = 3;
        final static int Chest = 4;
        final static int Arms = 5;
        final static int Legs = 6;
    }

    private Configuration configuration;
    private UiSounds uiSounds;
    private Cursor cursor;
    private MenuNavigationStack menuNavigationStack;

    private boolean isInteracting;
    private boolean isChoiceMade;

    private Map<MenuInteractionDialogNames, DialogBox> dialogBoxMap;
    private List<String> navigationElements;

    private Point cursorCellPosition;
    private boolean cursorVisible;

    private int currentNavigationCursorChoice;
    private int currentItemChoice;
    private int currentEquipmentAreaChoice;
    private int currentSkillChoice;

    private EquipmentAreas currentEquipmentAreaToModify;

    private boolean isEquipMode;

    public ConfirmationBox confirmationBox;

    public MenuInteractionState(GameStore store) {
        this.configuration = store.configuration;
        this.navigationElements = new LinkedList<>();
        this.initializeDialogBoxMap();
        this.initializeNavigationElements();
        this.cursor = null;
        this.currentNavigationCursorChoice = 0;
        this.cursorCellPosition = new Point(440, 430);
        this.cursorVisible = false;
        this.isChoiceMade = false;
        this.currentItemChoice = 0;
        this.currentEquipmentAreaChoice = 0;
        this.menuNavigationStack = new MenuNavigationStack(store);
        this.confirmationBox = new ConfirmationBox(store, null);
    }

    public MenuNavigationStack getMenuNavigationStack() {
        return this.menuNavigationStack;
    }

    private void initializeDialogBoxMap() {
        this.dialogBoxMap = new HashMap<>();
        this.dialogBoxMap.put(MenuInteractionDialogNames.Main, new DialogBox(this.configuration));
        this.dialogBoxMap.put(MenuInteractionDialogNames.Navigation, new DialogBox(this.configuration));
        this.dialogBoxMap.put(MenuInteractionDialogNames.Time, new DialogBox(this.configuration));
        this.dialogBoxMap.put(MenuInteractionDialogNames.Wallet, new DialogBox(this.configuration));
    }

    private void initializeNavigationElements() {
        this.navigationElements = new LinkedList<>();
        this.navigationElements.add("Items");
        this.navigationElements.add("Skills");
        this.navigationElements.add("Equipment");
        this.navigationElements.add("Statistics");
        this.navigationElements.add("Configuration");
        this.navigationElements.add("Save");
    }

    public List<String> getNavigationElements() {
        return this.navigationElements;
    }

    public void setChoiceMade(boolean choiceMade) {
        this.isChoiceMade = choiceMade;
    }

    public boolean getChoiceMade() {
        return this.isChoiceMade;
    }

    public EquipmentAreas getCurrentEquipmentAreaToModify() {
        return this.currentEquipmentAreaToModify;
    }

    public void incrementCurrentItemChoice() {
        this.currentItemChoice++;
    }

    public void incrementCurrentItemChoice(int size) {
        this.currentItemChoice += size;
    }

    public void decrementCurrentItemChoice() {
        this.currentItemChoice--;
    }

    public void decrementCurrentItemChoice(int size) {
        this.currentItemChoice -= size;
    }

    public int getCurrentItemChoice() {
        return this.currentItemChoice;
    }

    public void incrementCurrentEquipmentAreaChoice() {
        this.currentEquipmentAreaChoice++;
        this.updateCurrentEquipmentAreaToModify();
    }

    public void decrementCurrentEquipmentAreaChoice() {
        this.currentEquipmentAreaChoice--;
        this.updateCurrentEquipmentAreaToModify();
    }

    public int getCurrentEquipmentAreaChoice() {
        return this.currentEquipmentAreaChoice;
    }

    public void incrementCurrentSkillChoice() {
        this.currentSkillChoice++;
    }

    public void decrementCurrentSkillChoice() {
        this.currentSkillChoice--;
    }

    public int getCurrentSkillChoice() {
        return this.currentSkillChoice;
    }

    public void incrementNavigationCursorChoice() {
        this.currentNavigationCursorChoice++;
    }

    public void decrementNavigationCursorChoice() {
        if(this.currentNavigationCursorChoice > 0) {
            this.currentNavigationCursorChoice--;
        }
    }

    public int getCurrentNavigationCursorChoice() {
        return this.currentNavigationCursorChoice;
    }

    public void setCursorCellPosition(Point cellPosition) {
        this.cursorCellPosition = cellPosition;
    }

    public Point getCursorCellPosition() {
        return this.cursorCellPosition;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return this.cursor;
    }

    public void setCursorVisible(boolean visible) {
        this.cursorVisible = visible;
    }

    public boolean getCursorVisible() {
        return this.cursorVisible;
    }

    public boolean isMenuAllVisible() {
        boolean result = true;

        for(DialogBox d : this.dialogBoxMap.values()) {
            result &= d.visible();
        }

        return result;
    }

    public void toggleAll() {
        for(DialogBox d : this.dialogBoxMap.values()) {
            d.toggle();
        }
    }

    public void setInteracting(boolean isInteracting) {
        this.isInteracting = isInteracting;
    }

    public boolean getInteracting() {
        return this.isInteracting;
    }

    public DialogBox getDialog(MenuInteractionDialogNames dialogName) {
        return this.dialogBoxMap.get(dialogName);
    }

    public void returnToNavigation(GameStore store) {
        // Only play a sound if there is something on the stack. This handles the case on the initial build of the
        // menu key press handler to prevent it from playing the cursor cancel sound
        if(this.menuNavigationStack.hasMenuViewOnStack()) {
            Logger.log("Canceling and returning to navigation.");

            if(this.uiSounds == null) {
                this.uiSounds = new UiSounds(store);
            }

            this.uiSounds.playCancel();
        }

        this.menuNavigationStack.pushViewToNavigationStack(MenuInteractionDialogNames.Navigation);
        this.currentEquipmentAreaChoice = 0;
        this.currentItemChoice = 0;
        this.setChoiceMade(false);
        this.setCursorCellPosition(NAVIGATION_CURSOR_START);
    }

    public void setEquipMode(boolean mode) {
        this.isEquipMode = mode;
        Logger.log("Setting equip mode to: " + this.isEquipMode);
    }

    public boolean getEquipMode() {
        return this.isEquipMode;
    }

    private void updateCurrentEquipmentAreaToModify() {
        switch(this.currentEquipmentAreaChoice) {
            case CursorOnEquipmentArea.LeftHand:
                this.currentEquipmentAreaToModify = EquipmentAreas.LeftHand;
                break;
            case CursorOnEquipmentArea.RightHand:
                this.currentEquipmentAreaToModify = EquipmentAreas.RightHand;
                break;
            case CursorOnEquipmentArea.Accessory:
                this.currentEquipmentAreaToModify = EquipmentAreas.Accessory;
                break;
            case CursorOnEquipmentArea.Head:
                this.currentEquipmentAreaToModify = EquipmentAreas.Head;
                break;
            case CursorOnEquipmentArea.Chest:
                this.currentEquipmentAreaToModify = EquipmentAreas.Chest;
                break;
            case CursorOnEquipmentArea.Arms:
                this.currentEquipmentAreaToModify = EquipmentAreas.Arms;
                break;
            case CursorOnEquipmentArea.Legs:
                this.currentEquipmentAreaToModify = EquipmentAreas.Legs;
                break;
            default:
                this.currentEquipmentAreaToModify = EquipmentAreas.LeftHand;
        }
    }
}
