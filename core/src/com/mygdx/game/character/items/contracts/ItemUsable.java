/*
 * ItemUsable.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Implement this interface to make your item usable.
 */

package com.mygdx.game.character.items.contracts;

import com.mygdx.game.graphics.animation.contracts.AnimationDoneCallback;
import com.mygdx.game.objects.contracts.Targetable;

public interface ItemUsable {
    boolean use();
    void animate(AnimationDoneCallback callback, Targetable target);
    void playSound();
}
