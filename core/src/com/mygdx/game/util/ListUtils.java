/*
 * ListUtils.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Various utilities to manipulate and transform lists.
 */

package com.mygdx.game.util;

import java.util.List;

public class ListUtils<E> {
    public String join(String separator, List<E> list) {
        StringBuilder result = new StringBuilder();
        for(E item : list) {
            result.append(item.toString());
            result.append(separator);
        }

        return result.toString();
    }
}
