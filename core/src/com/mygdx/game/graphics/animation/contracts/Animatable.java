package com.mygdx.game.graphics.animation.contracts;

import com.mygdx.game.objects.contracts.Targetable;

import java.awt.*;

public interface Animatable {
    Point getLocation();
    void setLocation(Point location);
    void reset();
    void animate(AnimationDoneCallback cb, Targetable t);
}
