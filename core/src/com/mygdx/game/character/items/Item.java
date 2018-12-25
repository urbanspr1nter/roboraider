/*
 * Item.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * The item class is extended to make custom items, or equipment.
 */

package com.mygdx.game.character.items;

import com.mygdx.game.GameStore;
import com.mygdx.game.audio.UiSounds;
import com.mygdx.game.character.items.contracts.Combatable;
import com.mygdx.game.character.items.contracts.ItemUsable;
import com.mygdx.game.character.items.enums.Name;
import com.mygdx.game.graphics.animation.Animation;
import com.mygdx.game.graphics.animation.contracts.AnimationDoneCallback;
import com.mygdx.game.objects.contracts.Targetable;

public abstract class Item implements ItemUsable, Combatable {
    protected Name name;
    protected GameStore store;
    protected UiSounds uiSounds;
    protected Animation animation;

    Item(GameStore store, Name name) {
        this.name = name;
        this.store = store;
        this.uiSounds = new UiSounds(this.store);
        this.animation = null;
    }

    public String getName() {
        return this.name.toString();
    }

    @Override
    public String toString() {
        return this.name.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Item)) {
            return false;
        }

        Item that = (Item)o;

        return that.getName().equals(this.getName());
    }

    public boolean isCombatable() {
        return false;
    }

    public abstract boolean use();

    public void animate(AnimationDoneCallback cb, Targetable target) {
        if(this.animation != null) {
            this.animation.animate(cb, target);
        }
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public void playSound() { }
}
