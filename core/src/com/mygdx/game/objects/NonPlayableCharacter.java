package com.mygdx.game.objects;

import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.map.enums.Action;
import com.mygdx.game.map.models.Trigger;
import com.mygdx.game.map.triggers.Triggerable;

import java.awt.*;

public class NonPlayableCharacter extends Character {
    private Trigger dialogTrigger;
    private Triggerable triggerHandler;

    public NonPlayableCharacter(
            Configuration configuration,
            String name,
            String resourceFilename,
            Point startingCellLocation,
            Triggerable triggerHandler
    ) {
        super(configuration, name, resourceFilename, startingCellLocation);

        this.triggerHandler = triggerHandler;
        this.dialogTrigger = new Trigger(configuration, new Point(0, 0), Action.Npc, triggerHandler);
    }

    public Triggerable getTriggerHandler() {
        return this.triggerHandler;
    }

    public void trigger() {
        this.dialogTrigger.execute();
    }
}
