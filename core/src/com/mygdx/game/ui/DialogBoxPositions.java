/*
 * DialogBoxPositions.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * A container for various starting positions for where to
 * render a dialog box of a specific size.
 */

package com.mygdx.game.ui;

import java.awt.*;

public class DialogBoxPositions {
    public static Point HeaderTop() {
        return new Point(180, 440);
    }

    public static Point SmallHeaderTop() {
        return new Point(32, 440);
    }

    public static Point Large() {
        return new Point(72, 122);
    }

    public static Point LargeRightBottom() {
        return new Point(132, 114);
    }

    public static Point XLargeRightBottom() {
        return new Point(132, 306);
    }

    public static Point SmallRightBottomMiddle() {
        return new Point(270, 162);
    }

    public static Point SmallLeftBottom() {
        return new Point(32, 114);
    }
    public static Point SmallRightBottom() {
        return new Point(408, 114);
    }

    public static Point SmallTallRightBottom() {
        return new Point(408, 146);
    }

    public static Point SmallCenter() {
        return new Point(224, 240);
    }

    public static Point Full() {
        return new Point(16, 432);
    }

    public static Point ThreeQuarters() {
        return new Point(16, 432);
    }

    public static Point Quarter() {
        return new Point(466, 432);
    }

    public static Point QuarterQuarterTime() {
        return new Point(466, 176);
    }

    public static Point QuarterQuarterWallet() {
        return new Point(466, 80);
    }

}
