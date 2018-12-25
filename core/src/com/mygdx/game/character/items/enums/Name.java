/*
 * Name.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A list of all valid items.
 */

package com.mygdx.game.character.items.enums;

public enum Name {
    Potion("POTION"),
    Ether("ETHER"),
    Helmet("HELMET"),
    ClothArmor("CL ARMOR"),
    IronSword("FE SWORD"),
    LifeRing("LIFE RNG");

    private String value;

    Name(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
