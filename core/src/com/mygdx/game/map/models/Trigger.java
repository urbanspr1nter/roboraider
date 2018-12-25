package com.mygdx.game.map.models;

import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.map.enums.Action;
import com.mygdx.game.map.triggers.Triggerable;

import java.awt.*;

public class Trigger {
    private Configuration configuration;

    private Point cellLocation;
    private Action triggerAction;
    private Triggerable triggerHandler;

    public Trigger(Configuration config, Point cellLocation, Action triggerAction, Triggerable triggerHandler) {
        this.configuration = config;
        this.cellLocation = cellLocation;
        this.triggerAction = triggerAction;
        this.triggerHandler = triggerHandler;
    }

    public void execute() {
        this.triggerHandler.trigger();
    }

    public Point location() {
        return this.cellLocation;
    }

    public Action action() {
        return this.triggerAction;
    }
}
