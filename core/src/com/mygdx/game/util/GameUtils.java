/*
 * GameUtils.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * For all other handy utility methods that don't fit elsewhere.
 */

package com.mygdx.game.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameUtils {
    public static int delayInMs(int fps) {
        return (1000 / fps);
    }

    public static String durationMsToFormattedTime(long duration) {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("HH:mm:ss");

        return sdf.format(LocalTime.MIN.plusSeconds(duration / 1000));
    }
}
