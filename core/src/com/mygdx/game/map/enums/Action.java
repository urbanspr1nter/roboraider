package com.mygdx.game.map.enums;

public enum Action {
    Teleport("TELEPORT"),
    Npc("NPC");

    private String action;

    Action(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return this.action;
    }
}
