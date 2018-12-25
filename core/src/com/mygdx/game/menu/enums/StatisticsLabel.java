/*
 * StatisticsLabel.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Labels for the statistics menu dialog.
 */

package com.mygdx.game.menu.enums;

public enum StatisticsLabel {
    Attack("ATTACK"),
    Defense("DEFENSE"),
    Magic("MAGIC"),
    Spirit("SPIRIT"),
    Speed("SPEED"),
    Accuracy("ACCURACY"),
    Evasion("EVASION"),
    Stamina("STAMINA"),
    Luck("LUCK"),
    Vitality("VITALITY");

    private String value;

    StatisticsLabel(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
