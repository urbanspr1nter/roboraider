package com.mygdx.game.ui;

import com.mygdx.game.ui.enums.DialogBoxPosition;
import com.mygdx.game.ui.enums.GridPosition;

import java.awt.*;

public class CursorPlacement {
    public static Point GetStartLocation(DialogBoxPosition position) {
        if(position == DialogBoxPosition.SmallRightBottom) {
            return SmallRightBottom();
        } else if(position == DialogBoxPosition.SmallTallRightBottom) {
            return SmallTallRightBottom();
        } else if(position == DialogBoxPosition.Full) {
            return Full();
        }

        return SmallRightBottom();
    }

    public static Point GetStartLocation(GridPosition position, int choices) {
        if(position == GridPosition.RightBottom) {
            switch(choices) {
                case 2:
                    return SmallRightBottom();
                case 3:
                    return SmallTallRightBottom();
                default:
                    return SmallRightBottom();
            }
        }

        return SmallRightBottom();
    }

    private static Point Full() {
        return new Point(0, 0);
    }

    private static Point SmallRightBottom() {
        return new Point(388, 100);
    }

    private static Point SmallTallRightBottom() {
        return new Point(384, 128);
    }
}
