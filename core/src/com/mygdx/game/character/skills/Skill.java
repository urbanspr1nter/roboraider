package com.mygdx.game.character.skills;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameStore;
import com.mygdx.game.character.skills.contracts.Learnable;
import com.mygdx.game.character.skills.enums.FieldType;
import com.mygdx.game.graphics.animation.Animation;
import com.mygdx.game.graphics.animation.contracts.AnimationDoneCallback;
import com.mygdx.game.objects.contracts.Targetable;

import java.util.Objects;

public abstract class Skill implements Learnable {
    protected GameStore store;
    protected Animation animation;
    protected String name;
    protected int mpNeeded;
    protected Sound sound;

    Skill(GameStore store, Animation animation, Sound sound, String name, int mpNeeded) {
        this.store = store;
        this.name = name;
        this.mpNeeded = mpNeeded;
        this.animation = animation;
        this.sound = sound;
    }

    public void playSound() {
        this.sound.play(77);
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public void animate(AnimationDoneCallback cb, Targetable t) {
        this.animation.animate(cb, t);
    }

    @Override
    public int getMpNeeded() {
        return this.mpNeeded;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public abstract FieldType getFieldType();

    @Override
    public abstract boolean use();

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.getFieldType());
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(!(o instanceof Skill)) {
            return false;
        }

        Skill that = (Skill)o;

        return that.toString().equals(this.name) && that.getFieldType() == this.getFieldType();
    }
}
