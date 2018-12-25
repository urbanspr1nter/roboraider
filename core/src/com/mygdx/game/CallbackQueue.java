/*
 * CallbackQueue.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A sleazy way to simulate callbacks for the state machine.
 */

package com.mygdx.game;

import java.util.LinkedList;
import java.util.List;

public class CallbackQueue {
    private List<Callable> callbacks;

    public CallbackQueue() {
        this.drain();
    }

    public void drain() {
        this.callbacks = new LinkedList<>();
    }

    public void register(Callable c) {
        this.callbacks.add(c);
    }

    public void registerImmediate(Callable c) {
        if(this.callbacks.size() > 0) {
            this.callbacks.add(0, c);
        } else {
            this.callbacks.add(c);
        }
    }

    public void handleCalls() {
        if(this.callbacks.size() == 0) {
            return;
        }

        List<Callable> toKeep = new LinkedList<Callable>();
        for(Callable c : this.callbacks) {
            boolean result = c.call();
            if(!result) {
               toKeep.add(c);
            }
        }

        this.callbacks = new LinkedList<Callable>(toKeep);
    }
}
