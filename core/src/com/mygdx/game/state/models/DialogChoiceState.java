/*
 * DialogChoiceState.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * The DialogChoiceState holds the current context of the menu UI,
 * dialog and choices available.
 */

package com.mygdx.game.state.models;

import com.mygdx.game.ui.Cursor;
import com.mygdx.game.ui.DialogBox;

import java.awt.*;

public class DialogChoiceState {
    private DialogBox primaryDialog;
    private DialogBox choiceDialog;
    private Cursor cursor;
    private int currentCursorChoice;
    private Point cursorCellPosition;
    private boolean cursorVisible;

    public DialogChoiceState() {
        this.primaryDialog = null;
        this.choiceDialog = null;
        this.cursor = null;
        this.currentCursorChoice = 0;
        this.cursorCellPosition = null;
        this.cursorVisible = false;
    }

    public void setPrimaryDialog(DialogBox primaryDialog) {
        this.primaryDialog = primaryDialog;
    }

    public void setChoiceDialog(DialogBox choiceDialog) {
        this.choiceDialog = choiceDialog;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public void setCursorVisible(boolean visible) {
        this.cursorVisible = visible;
    }

    public void incrementCursorChoice() {
        this.currentCursorChoice++;
    }

    public void decrementCursorChoice() {
        this.currentCursorChoice--;
    }

    public void resetCursorChoice() {
        this.currentCursorChoice = 0;
    }

    public void setCursorCellPosition(Point cursorCellPosition) {
        this.cursorCellPosition = cursorCellPosition;
    }

    public DialogBox getPrimaryDialog() {
        return this.primaryDialog;
    }

    public DialogBox getChoiceDialog () {
        return this.choiceDialog;
    }

    public Cursor getCursor() {
        return this.cursor;
    }

    public boolean getCursorVisible() {
        return this.cursorVisible;
    }

    public int getCurrentCursorChoice() {
        return this.currentCursorChoice;
    }

    public Point getCursorCellPosition() {
        return this.cursorCellPosition;
    }
}
