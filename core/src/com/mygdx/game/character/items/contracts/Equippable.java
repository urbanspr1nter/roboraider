/*
 * Equippable.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Implement this interface to make your item equippable.
 */

package com.mygdx.game.character.items.contracts;

import com.mygdx.game.character.enums.EquipmentAreas;

public interface Equippable {
    EquipmentAreas equippableArea();
}
