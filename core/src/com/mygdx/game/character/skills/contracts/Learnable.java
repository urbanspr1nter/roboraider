package com.mygdx.game.character.skills.contracts;

import com.mygdx.game.character.skills.enums.FieldType;

public interface Learnable {
    int getMpNeeded();
    String getName();
    boolean use();
    FieldType getFieldType();
}
