/*
 * EquipmentAreas.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * All valid areas to equip your character with items.
 */

package com.mygdx.game.character.enums;

public enum EquipmentAreas {
    Head("Head"),
    Chest("Chest"),
    Arms("Arms"),
    Legs("Legs"),
    Accessory("Accessory"),
    LeftHand("Left Hand"),
    RightHand("Right Hand");

    private String value;

    EquipmentAreas(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
