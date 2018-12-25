/*
 * Timer.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A timer utility that can be used to invoke Callabales after a
 * specific amount in time.
 */
package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Callable;

public class Timer {
    private float elapsedTimeMs;
    private float totalTimeMs;
    private boolean resolved;

    public Timer(int totalTimeMs) {
        this.reset(totalTimeMs);
    }

    public Timer() {
        this.reset(0);
    }

    public void reset(int totalTimeMs) {
        this.elapsedTimeMs = 0;
        this.totalTimeMs = totalTimeMs;
        this.resolved = false;
    }

    public boolean run(Callable c) {
        this.elapsedTimeMs += (Gdx.graphics.getDeltaTime() * 1000);

        if(!this.resolved && this.elapsedTimeMs >= this.totalTimeMs) {
            this.resolved = true;

            c.call();

            return true;
        }

        return false;
    }
}
