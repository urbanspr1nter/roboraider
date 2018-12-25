package com.mygdx.game.map.models;

import com.mygdx.game.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

public class MapInformation {
    public List<Trigger> triggers;

    public MapInformation(Configuration configuration) {
        this.triggers = new ArrayList<Trigger>();
    }

    public void addTrigger(Trigger trigger) {
        this.triggers.add(trigger);
    }

    public List<Trigger> getTriggers() {
        return this.triggers;
    }
}
