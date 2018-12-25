/*
 * Tuple.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A generic tuple structure. Handy for storing stuff in pairs.
 */

package com.mygdx.game.util.structures;

public class Tuple<E> {
    public E first;
    public E second;

    public Tuple(E first, E second) {
        this.first = first;
        this.second = second;
    }
}
