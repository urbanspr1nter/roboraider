package com.mygdx.game.map.triggers.dialogs;

import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.map.triggers.Triggerable;
import com.mygdx.game.objects.Dialog;

import java.util.List;

public abstract class DialogTriggerHandler implements Triggerable {
    protected Configuration config;
    protected String currText;

    private Dialog dialog;

    DialogTriggerHandler(Configuration config, Dialog dialog) {
        this.config = config;
        this.dialog = dialog;
        this.currText = "";
    }

    public void next(String currText) {
        this.dialog.setNext(currText);
        this.currText = currText;
    }

    public List<String> currentDialog() {
        return this.dialog.getDialog();
    }

    public String currentText() {
        return this.currText;
    }

    public boolean hasMoreDialog() {
        return this.dialog.hasNext();
    }

    public void resetDialog() {
        this.dialog.reset();
    }

    @Override
    public abstract void trigger();
}
