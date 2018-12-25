/*
 * CircularBuffer.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A "circular" buffer implementation with a provided limit.
 */

package com.mygdx.game.util;

import java.util.ArrayList;
import java.util.List;

public class CircularBuffer<E> {
    private List<E> buffer;
    private int limit;
    private int currIdx;

    public CircularBuffer(int limit) {
        this.limit = limit;
        this.currIdx = 0;
        this.buffer = new ArrayList<>();
    }

    public void add(E datum) {
        if(this.currIdx >= this.limit) {
            this.currIdx = 0;
        }

        if(this.currIdx < this.limit && this.buffer.size() != this.limit) {
            this.buffer.add(datum);
        } else {
            this.buffer.set(this.currIdx, datum);
        }

        this.currIdx++;
    }

    public List<E> getBuffer() {
        return this.buffer;
    }
}
