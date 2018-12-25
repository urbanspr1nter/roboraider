package com.mygdx.game.battle.enums;

public enum Choices {
    Attack("ATTACK"),
    Skill("SKILL"),
    Item("ITEM"),
    Run("RUN"),
    None("NONE");

    private String value;

    Choices(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
